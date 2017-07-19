
def reorder[A](xs: List[A], n: Int): List[List[A]] = {
  val len = xs.length
  Stream.continually(xs).flatten // cycle
    .tails
    .map(_.take(len).toList)
    .take(n)
    .toList
}

def mapExercises(state: State, reverse: Boolean = false): Map[String, String] = {
  val refs = Project.extract(state).structure.allProjectRefs.toList.map(r => r.project).sorted
  val l = if (reverse) reorder(refs.reverse, 2) else reorder(refs, 2)
  l(0) zip l(1) toMap
}

val bookmarkKeyName = "bookmark"
val mapPrevKeyName = "map-prev"
val mapNextKeyName = "map-next"

{
  val bookmark: AttributeKey[File] = AttributeKey[File](bookmarkKeyName)
  val mapPrev: AttributeKey[Map[String, String]] = AttributeKey[Map[String, String]](mapPrevKeyName)
  val mapNext: AttributeKey[Map[String, String]] = AttributeKey[Map[String, String]](mapNextKeyName)
  val setupNavAttrs: (State) => State = (s: State) => {
    val mark: File = s get bookmark getOrElse new sbt.File(new sbt.File(Project.extract(s).structure.root), ".bookmark")
    val prev: Map[String, String] = s get mapPrev getOrElse mapExercises(s, reverse = true)
    val next: Map[String, String] = s get mapNext getOrElse mapExercises(s)
    s.put(bookmark, mark).put(mapPrev, prev).put(mapNext, next)
  }
  val loadBookmark: (State) => State = (state: State) => {
    val key: AttributeKey[File] = AttributeKey[File](bookmarkKeyName)
    val bookmarkFile: Option[File] = state get key
    try {
      val mark: String = IO.read(bookmarkFile.get)
      val cmd: String = s"project $mark"
      val newState = Command.process(cmd, state)
      newState
    } catch {
      case e: java.io.FileNotFoundException => state
    }
  }
  onLoad in Global := {
    val state = (onLoad in Global).value
    loadBookmark compose(setupNavAttrs compose state)
  }
}

def next = Command.command("next") { state =>
  val newState: State = move(mapNextKeyName, state)
  newState
}

def prev = Command.command("prev") { state =>
  val newState: State = move(mapPrevKeyName, state)
  newState
}

def move(keyName: String, state: State): State = {
  val attrKey = AttributeKey[Map[String, String]](keyName)
  val prjNme: String = Project.extract(state).get(name)
  val moveMap: Option[Map[String, String]] = state get attrKey
  val toPrjNme: String = moveMap.get(prjNme)
  var cmd: String = s"project $toPrjNme"
  if (keyName.equals(mapNextKeyName)) cmd = moveNextCmd(prjNme, toPrjNme, state)
  val newState: State = Command.process(cmd, state)
  bookmark(toPrjNme, newState)
  newState
}

def moveNextCmd(frmPrjNme: String, toPrjNme: String, state: State): String = {
  val frmPrjSrc: String = Project.extract(state).get(sourceDirectory) + "/main"
  val toPrjSrc: String = frmPrjSrc.toString.replaceAll(frmPrjNme, toPrjNme)
  val next: String = s"project $toPrjNme"
  next
}

def bookmark(toPrjNme: String, state: State): Unit = {
  val key: AttributeKey[File] = AttributeKey[File](bookmarkKeyName)
  val bookmarkFile: Option[File] = state get key
  IO.write(bookmarkFile.get, toPrjNme)
}

commands in Global ++= Seq(next, prev)
