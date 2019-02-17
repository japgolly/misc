name := "Misc"

scalaVersion := "2.12.8"

scalacOptions := List(
  "-unchecked",
  "-deprecation",
  "-Xsource:2.13",
  "-Ypartial-unification",
  "-Ypatmat-exhaust-depth", "off",
  "-Ywarn-inaccessible",
  "-feature", "-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-language:existentials")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4")
