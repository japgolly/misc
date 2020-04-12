name := "Misc"

scalaVersion := "2.12.11"

scalacOptions := List(
  "-deprecation",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-Xlint:infer-any",
  "-Xsource:2.13",
  "-Ybackend-parallelism", java.lang.Runtime.getRuntime.availableProcessors().min(16).toString,
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
  "-Ywarn-unused:privates")
  // "-target:jvm-1.8",
  // "-Xstrict-inference", // Don't infer known-unsound types
  // "-Ywarn-self-implicit",
  // "-Ywarn-unused-import"
  // "-Ywarn-unused:explicits",
  // "-Ywarn-unused:locals",

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
// addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
