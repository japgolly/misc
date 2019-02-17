package ahh

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import javax.websocket.server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer

object Main {
  def main(args: Array[String]): Unit = {
    val server = new Server(8080)

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
    server.join()
  }
}

// ===========================================================================================================

import javax.websocket._
import javax.websocket.server.ServerEndpoint

@ClientEndpoint
@ServerEndpoint(value = "/events/") class EventSocket {
  @OnOpen def onWebSocketConnect(sess: Session): Unit = {
    System.out.println("Socket Connected: " + sess)
  }

  @OnMessage def onWebSocketText(message: String): Unit = {
    System.out.println("Received TEXT message: " + message)
  }

  @OnClose def onWebSocketClose(reason: CloseReason): Unit = {
    System.out.println("Socket Closed: " + reason)
  }

  @OnError def onWebSocketError(cause: Throwable): Unit = {
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