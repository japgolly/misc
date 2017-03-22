package demo

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSImport}
import japgolly.scalajs.react.test.WebpackRequire

@JSExport
object TestAssets {

  @JSExport
  def prepare(): Unit = {
    Main.require()
    WebpackRequire.ReactTestUtils
    ()
  }
}
