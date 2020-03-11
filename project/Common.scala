import sbt._, Keys._
import java.nio.file.{Files, Path}
import scala.concurrent.duration._
// import com.typesafe.sbt.GitPlugin.autoImport._
import org.scalajs.core.tools.sem._
import org.scalajs.jsenv.phantomjs.PhantomJSEnv
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.{crossProject => _, CrossType => _, _}
import sbtcrossproject.CrossProject
import sbtcrossproject.CrossPlugin.autoImport._
import scalajscrossproject.ScalaJSCrossPlugin.autoImport._
// import sbtdocker.DockerPlugin, DockerPlugin.autoImport._
import LibDependency.{Dep, HasBoth, HasJs, HasJvm, JS, JVM, ModDepScope}

sealed trait JsTestType
case object NoTests      extends JsTestType
case object UseNode      extends JsTestType
case object UsePhantomJs extends JsTestType

object Common {

  private val availableProcessors = java.lang.Runtime.getRuntime.availableProcessors()

  def scalacFlags = Seq(
    "-deprecation",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-unchecked",
    "-Xlint:infer-any",
    "-Xsource:2.13",
    "-Ybackend-parallelism", availableProcessors.min(16).toString,
    "-Ycache-macro-class-loader:last-modified",
    "-Ycache-plugin-class-loader:last-modified",
    "-Yno-adapted-args",
    "-Yno-generic-signatures",
    "-Ypartial-unification",
    "-Ypatmat-exhaust-depth", "off",
    "-Ywarn-inaccessible",
    "-Ywarn-infer-any",
    "-Ywarn-unused:implicits",
    "-Ywarn-unused:patvars",
    "-Ywarn-unused:privates"
  )

  def scalacTestFlags = Seq("-language:reflectiveCalls")

  val debugSettings: Project => Project =
    _.settings(
      scalacOptions ++= Seq("-Xcheckinit"))

  /** Minimal settings used by benchmark modules too */
  lazy val settingsMin = (p: Project) => p
    .settings(
      organization                := "com.beardedlogic.shipreq",
      organizationName            := "Bearded Logic",
      shellPrompt in ThisBuild    := ((s: State) => Project.extract(s).currentRef.project + "> "),
      incOptions                  := incOptions.value.withLogRecompileOnMacro(false),
      updateOptions               := updateOptions.value.withCachedResolution(true),
      aggregate in update         := true,
      scalaVersion                := Dependencies.Scala.version,
      scalacOptions              ++= scalacFlags,
      testFrameworks              := List(new TestFramework("utest.runner.Framework")),
    //cancelable in Global        := true, // Allows ctrl-c to kill apps started with run without exiting SBT
      minForcegcInterval          := 3.minutes)

  /** Common settings used by standard modules - not benchmarks, not test modules */
  private def settings: Project => Project =
    _.configure(settingsMin)
      .settings(
        excludeDependencies += "commons-logging" % "commons-logging", // commons-logging should be replaced by jcl-over-slf4j
        scalacOptions in Test ++= scalacTestFlags)
      .configure(debugSettings)

  lazy val jvmSettings: Project => Project =
    _.configure(settings)

  def jsSettings(t: JsTestType): Project => Project =
    _.configure(
      settings,
      jsTests(t),
      jsDevSettings)
    .settings(
      scalacOptions += "-P:scalajs:sjsDefinedByDefault",
      parallelExecution in testOnly := false,
      // scalaJSOptimizerOptions in fullOptJS ~= (_ withPrettyPrintFullOptJS true),
      scalaJSSemantics in fullOptJS ~= (_
        .withProductionMode(true)
        .withRuntimeClassNameMapper(Semantics.RuntimeClassNameMapper.discardAll())
        .withArrayIndexOutOfBounds(CheckedBehavior.Unchecked)
        .withAsInstanceOfs(CheckedBehavior.Unchecked)))

  private def jsDevSettings: Project => Project =
    _.settings(emitSourceMaps := false)

  lazy val testModuleSettings = (p: Project) => settingsMin(p)
    .settings(scalacOptions ++= scalacTestFlags)
    .configure(debugSettings)

  private def jsTests(t: JsTestType): Project => Project =
    t match {
      case NoTests =>
        _.settings(test := {})
      case UseNode =>
        _.settings(
          jsEnv in Test := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv)
      case UsePhantomJs =>
        _.settings(
          emitSourceMaps in fastOptJS in Test := false, // PhantomJS doesn't use
          jsEnv                       in Test := new PhantomJS2Env(PhantomJSEnv.Config().withJettyClassLoader(scalaJSPhantomJSClassLoader.value)))
//          emitSourceMaps in fastOptJS in Test := true)
    }

  implicit class CrossProjectExt(val p: CrossProject) extends AnyVal {

    def configureBoth(fs: (Project => Project)*): CrossProject =
      fs.foldLeft(p)((q,f) => q.jvmConfigure(f).jsConfigure(f))

    def configureJvm(fs: (Project => Project)*): CrossProject =
      fs.foldLeft(p)((q,f) => q.jvmConfigure(f))

    def configureJs(fs: (Project => Project)*): CrossProject =
      fs.foldLeft(p)((q,f) => q.jsConfigure(f))

    def depsForBoth(deps: Dep[HasBoth]): CrossProject =
      depsForJvm(deps.widen).depsForJs(deps.widen)

    def depsForJvm(deps: Dep[HasJvm]): CrossProject =
      p.jvmSettings(libraryDependencies ++= deps(JVM))

    def depsForJs(deps: Dep[HasJs]): CrossProject =
      p.jsSettings(libraryDependencies ++= deps(JS))
  }

  implicit class ProjectExt(val p: Project) extends AnyVal {
    def depsForJs(deps: Dep[HasJs]): Project =
      p.settings(libraryDependencies ++= deps(JS))
  }
}
