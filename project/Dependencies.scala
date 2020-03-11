import sbt._
import scala.languageFeature._
import LibDependency._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.{CrossType => _, crossProject => _, _}

object Dependencies {

  object Scala {
    def version  = "2.12.10"
  }

  val μTest       = jvmAndJs("com.lihaoyi",                      "utest",       "0.6.7")
}
