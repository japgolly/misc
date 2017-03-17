package demo

import scalajs.js
import scalajs.js.annotation._

@JSImport("experiment-webpack/assets", JSImport.Namespace)
@js.native
object Assets extends js.Object {
  val magpie: String = js.native
}
