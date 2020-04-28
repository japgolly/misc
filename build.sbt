scalaVersion := "2.13.2"

enablePlugins(JmhPlugin)

Compile / scalaSource := baseDirectory.value / "src"

libraryDependencies += "com.github.japgolly.nyaya" %% "nyaya-gen" % "0.9.2"
