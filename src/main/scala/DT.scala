// Functions with domain dependent on codomain

trait Forall[F[_]] {
  def apply[A]: F[A]
}

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
object TypeMember {

  sealed trait F { type A }
  case object FI extends F { type A = Int }
  case object FS extends F { type A = String }
  type FAux[B] = F { type A = B }

  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:32: type mismatch;
  // [error]  found   : Int(1)
  // [error]  required: f.A
  // [error]       case FI => 1
  // [error]                  ^
  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:33: type mismatch;
  // [error]  found   : String("")
  // [error]  required: f.A
  // [error]       case _: FS.type => ""
  // [error]                          ^
  /*
  def test1a(f: F): f.A =
    f match {
      case FI => 1
      case _: FS.type => ""
    }
  */

  // Proves that the above should work
  def test1b(f: F): f.A =
    test2[f.A](f)

  def test2[A](f: FAux[A]): A =
    f match {
      case FI => 1
      case FS => ""
    }

  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:48: type mismatch;
  // [error]  found   : Int(1)
  // [error]  required: A
  // [error]       case _: FI.type => 1
  // [error]                          ^
  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:49: type mismatch;
  // [error]  found   : String("")
  // [error]  required: A
  // [error]       case _: FS.type => ""
  // [error]                          ^
  // THIS WORKS IN DOTTY!
  /*
  def test2b[A](f: FAux[A]): A =
    f match {
      case _: FI.type => 1
      case _: FS.type => ""
    }
  */

  trait FFn1 { def apply(f: F): f.A }
  trait FFn2 { def apply[A](f: FAux[A]): A }
  type FnAux[A] = FAux[A] => A
  type FFn3 = Forall[FnAux]

  val test3a: FFn1 =
    new FFn1 { override def apply(f: F): f.A = test1b(f) }

  val test3b: FFn2 =
    new FFn2 { override def apply[A](f: FAux[A]): A = test2(f) }

  val test3c: FFn3 =
    new Forall[FnAux] { override def apply[A] = test2 }

  // inline match works
  val test3c2: FFn3 =
    new Forall[FnAux] { override def apply[A] = {
      case FI => 1
      case FS => ""
    }}

  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:41: missing parameter type for expanded function
  // [error] The argument types of an anonymous function must be fully known. (SLS 8.5)
  // [error] Expected type was: TypeMember.FFn1
  // [error]   val test4a: FFn1 = {
  // [error]                     ^
  /*
  val test4a: FFn1 = {
    case FI => 1
    case FS => ""
  }
  */

  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:46: missing parameter type for expanded function
  // [error] The argument types of an anonymous function must be fully known. (SLS 8.5)
  // [error] Expected type was: TypeMember.FFn2
  // [error]   val test4b: FFn2 = {
  // [error]                     ^
  /*
  val test4b: FFn2 = {
    case FI => 1
    case FS => ""
  }
  */
}
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
object TypeParam {

  sealed trait F[A]
  case object FI extends F[Int]
  case object FS extends F[String]

  def test2[A](f: F[A]): A =
    f match {
      case FI => 1
      case FS => ""
    }

  def test2b[A](f: F[A]): A =
    f match {
      case _: FI.type => 1
      case _: FS.type => ""
    }

  trait FFn { def apply[A](f: F[A]): A }

  val test3: FFn =
    new FFn { override def apply[A](f: F[A]): A = test2(f) }

  // [error] /home/golly/projects/misc/src/main/scala/DT.scala:104: missing parameter type for expanded function
  // [error] The argument types of an anonymous function must be fully known. (SLS 8.5)
  // [error] Expected type was: TypeParam.FFn
  // [error]   val test4: FFn = {
  // [error]                    ^
  /*
  val test4: FFn = {
    case FI => 1
    case FS => ""
  }
  */
}
