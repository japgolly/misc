package ahh

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler

import javax.websocket.server

object Main {
  def main(args: Array[String]): Unit = {
    val server = new Server(8080)
    server.setHandler(new Handler())
    server.start()
    server.join()
  }
}

class Handler extends AbstractHandler {

  override def handle(target: String,
                      baseRequest: Request,
                      request: HttpServletRequest,
                      response: HttpServletResponse): Unit = {

    // Declare response encoding and types
    response.setContentType("text/html; charset=utf-8")

    // Declare response status code
    response.setStatus(HttpServletResponse.SC_OK)

    // Write back response
    response.getWriter().println("<h1>Hello World</h1>")

    // Inform jetty that this request has now been handled
    baseRequest.setHandled(true)
  }

}