package demo

import org.scalajs.dom
import scala.scalajs.js.JSApp

object Main extends JSApp {

  override def main(): Unit = {
    val component = DemoPage.Component()
    val target = dom.document.getElementById("target")
    component.renderIntoDOM(target)
  }

}
