package demo

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSImport}

@JSExport
object TestAssets {

  @JSImport("expose-loader?React.addons.TestUtils!react-addons-test-utils", JSImport.Namespace)
  @js.native
  private object ReactTestUtils extends js.Any

  @JSExport
  def prepare(): Unit = {
    Assets.requireExternal()
    ReactTestUtils
    ()
  }
}
