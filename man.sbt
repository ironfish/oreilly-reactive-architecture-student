import scala.Console

val mkdRx = """((?:`[\[\]\$> \(\)\w.,-]+`)|(?:\*["\{\}\! \(\)\w.,-]+\*))""".r
val mkdMatch = ".*" + mkdRx.toString + ".*"
val mkdSplit = """(`)|(\*)"""

def man: Command = Command.command("man") { state =>
  val prjBase: File = Project.extract(state).get(baseDirectory)
  IO.readLines(new sbt.File(prjBase + "/README.md")) foreach {
    case ln if ln.length > 0 && ln(0).equals('#') =>
      Console.println(Console.RED + ln + Console.RESET)
    case ln if ln.matches(mkdMatch) =>
      val mkdSet: Set[String] = mkdRx.findAllIn(ln).matchData.map(m => m.toString()).toSet
      ln.split(mkdSplit).foreach { s =>
        if (mkdSet.contains("`" + s + "`"))
          Console.print(Console.BLUE + s + Console.RESET)
        else if (mkdSet.contains("*" + s + "*"))
          Console.print(Console.GREEN + s + Console.RESET)
        else Console.print(s)
      }
      Console.print("\n")
    case ln => Console.println(ln)
  }
  Console.print("\n")
  state
}

commands in Global ++= Seq(man)