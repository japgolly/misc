import utest._

final case class X(a: X.Y, b: X.Y)

object X {

  final case class Y(value: Int)

  def fromInts(i: Int, j: Int): X =
    X(Y(i), Y(j))
}

object Z {

  def v(i: Int, j: Int) = X.fromInts(i, j)

  val v10 = v(1, 0)
  val v11 = v(1, 1)
  val v12 = v(1, 2)
  val v20 = v(2, 0)
  val v21 = v(2, 1)

  def wtf() = {

    val a = List(v10, v11, v12, v20, v21).iterator.zipWithIndex.toMap

    println(a(v12)) // Prints 2
    println(a(v12)) // Prints 2
    println(a(v12)) // Prints 2
    println(a(v12)) // Throws java.util.NoSuchElementException: key not found: X(Y(1),Y(2))
  }
}

object Bug extends TestSuite {
  override def tests = Tests {
    "wtf" - Z.wtf()
  }
}
