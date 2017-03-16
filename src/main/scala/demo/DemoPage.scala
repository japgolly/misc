package demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object DemoPage {

  val Component = ScalaComponent.static("Demo",
    <.div(
      <.h1("Demo"),
      <.p("Hello.")))

}
