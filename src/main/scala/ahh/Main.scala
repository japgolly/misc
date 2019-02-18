package ahh

import java.util.concurrent.{Executors, TimeUnit}
import org.redisson.Redisson
import org.redisson.api.listener.MessageListener
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import javax.websocket.server
import javax.websocket.server.PathParam
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer
import org.redisson.api.RTopic

object Main {
  def newRedissonClient() = {
    val cfg = new org.redisson.config.Config
    cfg
      .useSingleServer()
      .setAddress("redis://127.0.0.1:6379")
      .setConnectionMinimumIdleSize(1)
      .setConnectionPoolSize(1)
        .setSubscriptionConnectionMinimumIdleSize(1)
        .setSubscriptionConnectionPoolSize(1)
        .setSubscriptionsPerConnection(1)
    Redisson.create(cfg)
  }


  def apply(port: Int): Unit = {
    val server = new Server(port)

    // Setup the basic application "context" for this application at "/"
    // This is also known as the handler tree (in jetty speak)
    val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")
    server.setHandler(context)

    // Initialize javax.websocket layer
    val wscontainer = WebSocketServerContainerInitializer.configureContext(context)

    // Add WebSocket endpoint to javax.websocket layer
    wscontainer.addEndpoint(classOf[EventSocket])

    server.start()
    //    server.dumpStdErr()
    publishChanges(port)
    server.join()
  }

  def publishChanges(port: Int): Unit = {
    val redissonClient = newRedissonClient()

    val topics = List(1, 2).map("project-" + _).map(redissonClient.getTopic)

    val next = Stream.continually(topics).flatten.iterator

    def publish(): Unit = {
      val msg = s"[:$port] time is ${java.time.Instant.now()}"
      println("PUBLISH: " + msg)
      val t = next.next()
      t.publish(msg)
    }

    val scheduler = Executors.newSingleThreadScheduledExecutor()
    scheduler.scheduleAtFixedRate(() => publish(), 0, 4, TimeUnit.SECONDS)
  }
}

object Main8081 {def main(args: Array[String]): Unit = Main(8081)}
object Main8082 {def main(args: Array[String]): Unit = Main(8082)}

// ===========================================================================================================

import javax.websocket._
import javax.websocket.server.ServerEndpoint

class ShittyState {
  import ShittyGlobalState._

  private var m = List.empty[Conn]

  def apply[A](f: List[Conn] => (List[Conn], A)): A =
    synchronized {
      val (m2, a) = f(m)
      m = m2
      a
    }
}

object ShittyGlobalState {
  val shittyState = new ShittyState

  case class Conn(sessionId: String, topic: RTopic, listenerId: Int)
}

@ClientEndpoint
@ServerEndpoint(value = "/project/{id}")
class EventSocket {
  import ShittyGlobalState._

  // TODO Is this EventSocket class created per-server / per-WS-client / per-WS-req?
  // Consider any local state/resources (like the Redis client)

  val redissonClient = Main.newRedissonClient()

  @OnOpen
  def onWebSocketConnect(s: Session, @PathParam("id") projectId: String): Unit = {
    println(s"Socket Connected for #${projectId}: $s")

    val topicName = s"project-$projectId"
    val topic = redissonClient.getTopic(topicName)

    val listener: MessageListener[String] = (channel, msg) => {
      s.getBasicRemote.sendText(s"[$channel] $msg")
    }

    val listenerId = topic.addListener(classOf[String], listener)

    shittyState(m => (Conn(s.getId, topic, listenerId) :: m, ()))
  }

  @OnMessage
  def onWebSocketText(s: Session, message: String): Unit = {
    // TODO heh. could accept events through this instead of ajax
    println(s"Received TEXT message: $message")
  }

  @OnClose
  def onWebSocketClose(s: Session, reason: CloseReason): Unit = {
    println("Socket Closed: " + reason)

    val ours = shittyState(_.partition(_.sessionId != s.getId))
    ours.foreach(c => c.topic.removeListener(c.listenerId))
  }

  @OnError
  def onWebSocketError(cause: Throwable): Unit = {
    cause.printStackTrace(System.err)
  }
}

// ===========================================================================================================

import java.net.URI
import org.eclipse.jetty.util.component.LifeCycle

object EventClient {
  def main(args: Array[String]): Unit = {
    val uri = URI.create("ws://localhost:8080/events/")
    val container = ContainerProvider.getWebSocketContainer
    try { // Attempt Connect
      val session = container.connectToServer(classOf[EventSocket], uri)
      // Send a message
      session.getBasicRemote.sendText("Hello")
      // Close session
      session.close()
    } finally {
      // Force lifecycle stop when done with container.
      // This is to free up threads and resources that the
      // JSR-356 container allocates. But unfortunately
      // the JSR-356 spec does not handle lifecycles (yet)
      container match {
        case l: LifeCycle => l.stop()
        case _ => ()
      }
    }
  }
}