scalacOptions ++= Seq("-feature", "-language:higherKinds", "-language:implicitConversions")

libraryDependencies ++= {
  val scalazVer = "7.1.0-M6"
  Seq(
    "com.github.axel22" %% "scalameter" % "0.4",
    "org.scalaz" %% "scalaz-core" % scalazVer,
    "org.scalaz" %% "scalaz-concurrent" % scalazVer,
    "org.scalaz" %% "scalaz-effect" % scalazVer
  )
}
