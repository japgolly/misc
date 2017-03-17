package demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object DemoPage {

  val Component = ScalaComponent.static("Demo", {

    def icon = <.span(^.cls := "glyphicon glyphicon-tower")
    def map = GoogleMap.Component(GoogleMap.props(137.0537453, -35.8177544, 9))()
    def img = <.img(^.src := Assets.magpie, ^.cls := "img-circle")

    <.div(
      ^.paddingLeft := 1.em,
      <.h1(icon, " Demo"),
      <.div(
        ^.display.flex,
        ^.height := 370.px,
        ^.width := 90.pct,
        <.div(img),
        <.div(^.flexGrow := "1", ^.paddingLeft := 1.em, map)))
  })

}
