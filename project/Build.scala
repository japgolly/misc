import sbt._
import Keys._
import pl.project13.scala.sbt.JmhPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin
//import ScalaJSPlugin._
import ScalaJSPlugin.autoImport._
import Lib._

object ScalaStdlibBenchmarks extends Build {

  val Scala211 = "2.11.7"

  def scalacFlags = Seq(
    "-deprecation", "-unchecked", "-feature",
    "-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-language:existentials")

  val commonSettings: PE =
    _.settings(
      organization             := "com.github.japgolly.tempname",
      version                  := "0.1.0-SNAPSHOT",
      homepage                 := Some(url("https://github.com/japgolly/tempname")),
      licenses                 += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
      scalaVersion             := Scala211,
      scalacOptions           ++= scalacFlags,
      clearScreenTask          := clearScreen(),
      shellPrompt in ThisBuild := ((s: State) => Project.extract(s).currentRef.project + "> "),
      incOptions               := incOptions.value.withNameHashing(true),
      updateOptions            := updateOptions.value.withCachedResolution(true))
    .configure(
      addCommandAliases(
        "/"   -> "project root",
        "B"   -> "project benchmarkJVM",
        "J"   -> "project benchmarkJS",
        "C"   -> "root/clean",
        "cc"  -> ";clear;compile",
        "ccc" -> ";clear;clean;compile"))

  override def rootProject = Some(root)

  lazy val root =
    Project("root", file("."))
      .configure(commonSettings)
      .aggregate(benchmarkJVM, benchmarkJS)

  lazy val benchmark =
    crossProject.in(file("benchmark"))
      .bothConfigure(commonSettings)
      .jvmConfigure(_.enablePlugins(JmhPlugin))

  lazy val benchmarkJVM = benchmark.jvm
  lazy val benchmarkJS  = benchmark.js
}
