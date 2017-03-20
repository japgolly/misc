package demo

import scalajs.js
import scalajs.js.annotation._

object Assets {

  @JSImport("expose-loader?React!react", JSImport.Namespace)
  @js.native
  object React extends js.Any

  @JSImport("expose-loader?ReactDOM!react-dom", JSImport.Namespace)
  @js.native
  object ReactDOM extends js.Any

  def requireExternal(): Unit = {
    React
    ReactDOM
    ()
  }

  @JSImport("experiment-webpack/magpie.jpg", JSImport.Namespace)
  @js.native
  private object _magpie extends js.Any
  def magpie = _magpie.asInstanceOf[String]
}
