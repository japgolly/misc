version      in ThisBuild := "1.0-SNAPSHOT"
organization in ThisBuild := "com.github.japgolly.experiment.webpack"
shellPrompt  in ThisBuild := ((s: State) => Project.extract(s).currentRef.project + "> ")

val root   = Experiment.root
val webapp = Experiment.webapp

