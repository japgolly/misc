package demo

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Main extends js.JSApp {

  @JSImport("bootstrap/dist/css/bootstrap.css", JSImport.Default)
  @js.native
  object BootstrapCSS extends js.Any

  override def main(): Unit = {
//    val require = js.Dynamic.global.require
//    require("bootstrap/dist/css/bootstrap.css")

    BootstrapCSS

    val component = DemoPage.Component()
    val target = dom.document.getElementById("target")
    component.renderIntoDOM(target)
  }

}
