// Add case FL[X](x: X) extends F { type A = List[X] }
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
object TypeMember2 {

  sealed trait F { type A }
  case object FI extends F { type A = Int }
  case class FL[X](x: X) extends F { type A = List[X] }
  type FAux[B] = F { type A = B }

  // [error] /home/golly/projects/misc/src/main/scala/DT2.scala:12: type mismatch;
  // [error]  found   : Int(1)
  // [error]  required: f.A
  // [error]       case FI => 1
  // [error]                  ^
  // [error] /home/golly/projects/misc/src/main/scala/DT2.scala:13: type mismatch;
  // [error]  found   : List[Any]
  // [error]  required: f.A
  // [error]       case FL(x) => x :: Nil
  // [error]                       ^
  /*
  def test1a(f: F): f.A =
    f match {
      case FI => 1
      case FL(x) => x :: Nil
    }
  */

  // [error] /home/golly/projects/misc/src/main/scala/DT2.scala:31: type mismatch;
  // [error]  found   : List[Any]
  // [error]  required: A
  // [error]       case FL(x) => x :: Nil
  // [error]                       ^
  /*
  def test2[A](f: FAux[A]): A =
    f match {
      case FI => 1
      case FL(x) => x :: Nil
    }
  */
}
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
object TypeParam2 {

  sealed trait F[A]
  case object FI extends F[Int]
  case class FL[X](x: X) extends F[List[X]]

  def test2[A](f: F[A]): A =
    f match {
      case FI => 1
      case FL(x) => x :: Nil
    }
}
