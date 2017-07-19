val akkaVer = "2.5.3"
val assertjVer = "3.8.0"
val junitVer = "0.11"
val logbackVer = "1.2.3"
val quavaVer = "22.0"
val scalaVer = "2.12.2"
val scalaParsersVer= "1.0.6"

lazy val compileOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

lazy val commonDependencies = Seq(
  "com.google.guava"         %  "guava"                      % quavaVer,
  "com.typesafe.akka"        %% "akka-actor"                 % akkaVer,
  "com.typesafe.akka"        %% "akka-remote"                % akkaVer,
  "com.typesafe.akka"        %% "akka-slf4j"                 % akkaVer,
  "ch.qos.logback"           %  "logback-classic"            % logbackVer,
  "org.scala-lang.modules"   %% "scala-parser-combinators"   % scalaParsersVer,
  "com.typesafe.akka"        %% "akka-testkit"               % akkaVer            % Test,
  "com.novocode"             %  "junit-interface"            % junitVer           % Test,
  "org.assertj"              %  "assertj-core"               % assertjVer         % Test
)

lazy val fttas = project in file(".")
lazy val commonSettings = Seq(
  organization := "com.lightbend.training",
  version := "1.0.0",
  scalaVersion := scalaVer,
  scalacOptions ++= compileOptions,
  unmanagedSourceDirectories in Compile := List((javaSource in Compile).value),
  unmanagedSourceDirectories in Test := List((javaSource in Test).value),
  testOptions += Tests.Argument(TestFrameworks.JUnit, "-v"),
  EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.ManagedClasses,
  EclipseKeys.eclipseOutput := Some(".target"),
  EclipseKeys.withSource := true,
  EclipseKeys.skipParents in ThisBuild := true,
  parallelExecution in Test := false,
  logBuffered in Test := false,
  parallelExecution in ThisBuild := false,
  javacOptions += "-Xlint:unchecked",
  libraryDependencies ++= commonDependencies
)

lazy val base = (project in file("."))
  .aggregate(
    exercise_000_Initial_state,
    exercise_001_Implement_actor,
    exercise_002_Top_level_actor,
    exercise_003_Message_actor,
    exercise_004_Use_sender,
    exercise_005_Child_actors,
    exercise_006_Actor_state,
    exercise_007_Use_scheduler,
    exercise_008_Busy_actor,
    exercise_009_Stop_actor,
    exercise_010_Lifecycle_monitoring,
    exercise_011_Faulty_guest,
    exercise_012_Custom_supervision,
    exercise_013_Faulty_waiter,
    exercise_014_Self_healing,
    exercise_015_Detect_bottleneck,
    exercise_016_Use_router)
  .settings(commonSettings: _*)

lazy val exercise_000_Initial_state = project
  .settings(commonSettings: _*)

lazy val exercise_001_Implement_actor = project
  .settings(commonSettings: _*)

lazy val exercise_002_Top_level_actor = project
  .settings(commonSettings: _*)

lazy val exercise_003_Message_actor = project
  .settings(commonSettings: _*)

lazy val exercise_004_Use_sender = project
  .settings(commonSettings: _*)

lazy val exercise_005_Child_actors = project
  .settings(commonSettings: _*)

lazy val exercise_006_Actor_state = project
  .settings(commonSettings: _*)

lazy val exercise_007_Use_scheduler = project
  .settings(commonSettings: _*)

lazy val exercise_008_Busy_actor = project
  .settings(commonSettings: _*)

lazy val exercise_009_Stop_actor = project
  .settings(commonSettings: _*)

lazy val exercise_010_Lifecycle_monitoring = project
  .settings(commonSettings: _*)

lazy val exercise_011_Faulty_guest = project
  .settings(commonSettings: _*)

lazy val exercise_012_Custom_supervision = project
  .settings(commonSettings: _*)

lazy val exercise_013_Faulty_waiter = project
  .settings(commonSettings: _*)

lazy val exercise_014_Self_healing = project
  .settings(commonSettings: _*)

lazy val exercise_015_Detect_bottleneck = project
  .settings(commonSettings: _*)

lazy val exercise_016_Use_router = project
  .settings(commonSettings: _*)
