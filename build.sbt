scalacOptions ++= Seq("-feature", "-language:higherKinds", "-language:implicitConversions")

//scalacOptions ++= Seq("-optimise", "-Yinline", "-Yclosure-elim")

scalaVersion := "2.11.0"

libraryDependencies ++= {
  val scalazVer = "7.1.0-M7"
  Seq(
    "com.github.axel22" %% "scalameter" % "0.5-M2",
    "org.scalaz" %% "scalaz-core" % scalazVer,
    "org.scalaz" %% "scalaz-concurrent" % scalazVer,
    "org.scalaz" %% "scalaz-effect" % scalazVer
  )
}
