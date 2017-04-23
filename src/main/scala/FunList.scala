
sealed trait FunList[A, B, T]
object FunList {
  case class Done[A, B, T](done: T) extends FunList[A, B, T]
  case class More[A, B, T](a: A, next: FunList[A, B, B => T]) extends FunList[A, B, T]
}

