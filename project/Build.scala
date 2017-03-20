import sbt._, Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin, ScalaJSPlugin.autoImport._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin, ScalaJSBundlerPlugin.autoImport._

object Experiment {

  object Ver {
    val Scala         = "2.12.1"
    val ScalaJsReact  = "1.0.0-RC1"
    val ReactJs       = "15.4.2"
    val MTest         = "0.4.5"
  }

  type PE = Project => Project

  def commonSettings: PE =
    _.enablePlugins(ScalaJSBundlerPlugin)
      .settings(
        scalaVersion       := Ver.Scala,
        scalacOptions     ++= Seq("-deprecation", "-unchecked", "-feature",
                                "-language:postfixOps", "-language:implicitConversions",
                                "-language:higherKinds", "-language:existentials"),
        updateOptions      := updateOptions.value.withCachedResolution(true),
        incOptions         := incOptions.value.withNameHashing(true).withLogRecompileOnMacro(false),
        triggeredMessage   := Watched.clearWhenTriggered)

  def utestSettings: PE =
    _.settings(
      scalacOptions in Test += "-language:reflectiveCalls",
      libraryDependencies   += "com.lihaoyi" %%% "utest" % Ver.MTest % Test,
      testFrameworks        += new TestFramework("utest.runner.Framework"),
      requiresDOM           := true)

  def addCommandAliases(m: (String, String)*): PE = {
    val s = m.map(p => addCommandAlias(p._1, p._2)).reduce(_ ++ _)
    _.settings(s: _*)
  }

  // ==============================================================================================
  lazy val root = Project("root", file("."))
    .configure(commonSettings, utestSettings, addCommandAliases(
      "c"   -> "compile",
      "tc"  -> "test:compile",
      "t"   -> "test",
      "tq"  -> "testQuick",
      "to"  -> "test-only",
      "cc"  -> ";clean;compile",
      "ctc" -> ";clean;test:compile",
      "ct"  -> ";clean;test"))
    .settings(
      name := "demo",
      libraryDependencies ++= Seq(
        "com.github.japgolly.scalajs-react" %%% "core" % Ver.ScalaJsReact),

      // https://scalacenter.github.io/scalajs-bundler/api/latest/index.html#scalajsbundler.sbtplugin.ScalaJSBundlerPlugin$$autoImport$

      mainClass := Some("demo.Main"),
      version in webpack := "2.2.1",
      useYarn := true,
      webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack-dev.js"),
      webpackConfigFile in fullOptJS := Some(baseDirectory.value / "webpack-prod.js"),

      npmDependencies in Compile ++= Seq(
        "react"            -> Ver.ReactJs,
        "react-dom"        -> Ver.ReactJs,
        "google-map-react" -> "0.23.0",
        "bootstrap"        -> "3.3.7"),

      npmDevDependencies in Compile ++= Seq(
        "compression-webpack-plugin"  -> "0.3.2",
        "css-loader"                  -> "0.27.3",
        "expose-loader"               -> "0.7.3",
        "extract-text-webpack-plugin" -> "2.1.0",
        "file-loader"                 -> "0.10.1",
        "html-webpack-plugin"         -> "2.28.0",
        "style-loader"                -> "0.14.1",
        "url-loader"                  -> "0.5.8",
        "webpack"                     -> "2.2.1",
        "webpack-merge"               -> "4.1.0"))
}
