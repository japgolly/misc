
import sbt._, Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.{crossProject => _, CrossType => _, _}
import sbtcrossproject.CrossProject
import sbtcrossproject.CrossPlugin.autoImport.{crossProject => _, _}
import scalajscrossproject.ScalaJSCrossPlugin.autoImport._
import Common._
import Dependencies._
import WebappBuild._

object ShipReqBuild {

  def project(dir: String): Project =
    Project(dir, file(dir))

  def crossProject(dir: String): CrossProject =
    CrossProject(dir, file(dir))(JVMPlatform, JSPlatform)
      .jvmConfigure(_.withId(dir + "-jvm"))
      .jsConfigure(_.withId(dir + "-js"))
      .settings(name := dir)

  lazy val root =
    Project("root", file("."))
      .configure(Common.jvmSettings)
      .aggregate(webappBaseTestJs, webappBaseTestJvm)
}
