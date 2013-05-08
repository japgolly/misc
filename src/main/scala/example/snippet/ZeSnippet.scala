package example.snippet

import net.liftweb.http._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._
import scala.xml._

object ZeSnippet {

  def ButtonTemplate = {
    val templ = Templates("example" :: Nil).open_!
    val expr = ".container ^^" #> ""
    expr(templ)
  }

  case class JqId(id: String) extends JsExp {
    override def toJsCmd = "$('#" + id + "')"
  }
  case class JqAfter(content: NodeSeq) extends JsExp with JsMember {
    override val toJsCmd =
      "after(" + fixHtmlFunc("inline", content) { str => str } + ")"
  }
}

class ZeSnippet extends StatefulSnippet {
  import ZeSnippet._

  override def dispatch = { case _ => render }

  var buttons = 1
  var containerIds = Vector(nextFuncName)

  def render =
    "#buttons *" #> renderButton(0)

  def renderButton(button: Int) = {
    val cid = containerIds(button)
    ".container [id]" #> cid &
      ".add" #> SHtml.ajaxButton(s"Button ${button + 1}", () => addNewButtonAfter(cid)) &
      ".hello" #> SHtml.ajaxButton(s"Say Hello", () => sayHello(button))
  }

  def sayHello(button: Int): JsCmd =
    JsCmds.SetHtml("clicked", Text(s"Button ${button + 1} clicked."))

  def addNewButtonAfter(preceedingId: String): JsCmd = {
    buttons += 1
    containerIds = containerIds :+ nextFuncName
    val fn = "*" #> renderButton(buttons - 1)
    JqId(preceedingId) ~> JqAfter(fn(ButtonTemplate))
  }
}