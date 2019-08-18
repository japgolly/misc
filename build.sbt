name := "Misc"

scalaVersion := "2.12.9"

scalacOptions := List(
  "-unchecked",
  "-deprecation",
  "-Xsource:2.13",
  "-Ypartial-unification",
  "-Ypatmat-exhaust-depth", "off",
  "-Ywarn-inaccessible",
  "-feature", "-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-language:existentials")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
