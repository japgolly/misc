import sbt.{project => _, _}, Keys._
import org.scalajs.core.tools.io.{FileVirtualJSFile, VirtualJSFile}
import org.scalajs.sbtplugin.{ScalaJSPlugin, Stage}
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.{crossProject => _, CrossType => _, _}
import org.scalajs.sbtplugin.ScalaJSPluginInternal.stageKeys
import sbtcrossproject.CrossPlugin.autoImport.{crossProject => _, _}
import scalajscrossproject.ScalaJSCrossPlugin.autoImport._
// import sbtdocker.DockerPlugin, DockerPlugin.autoImport._
import Common._
import Dependencies._
import LibDependency.JVM
import ShipReqBuild._

/** The user-facing app.
  */
object WebappBuild {

  lazy val webappBaseTestJvm = webappBaseTest.jvm
  lazy val webappBaseTestJs  = webappBaseTest.js
  lazy val webappBaseTest =
    crossProject("webapp-base-test")
      .configureBoth(Common.testModuleSettings)
      .configureJvm(Common.jvmSettings)
      .configureJs(Common.jsSettings(UsePhantomJs))
      .depsForBoth(μTest)
      .jsSettings(jsDependencies in Test += ProvidedJS / "webapp-base-test.js")
}
