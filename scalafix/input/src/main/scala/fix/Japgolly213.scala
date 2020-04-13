/*
rule = Japgolly213
*/
package fix

object Japgolly213 {

  def iterableOnce(as: IterableOnce[Unit]) = {

    // one
    Nil.foreach(_ => ())
    as.foreach(_ => ())

    // two
    val f = () => () => as
    def omg(i: Int) = as
    for {
      _ <- as
      _ <- Nil
      // _ <- f()()
      _ <- omg(7)
    } ()

    // three
    for {
      _ <- Nil
      _ <- as
    } yield ()
  }

  def iterableOnceBN(bn: => IterableOnce[Unit]) = bn.isEmpty
  def iterableOnceF0(f0: () => IterableOnce[Unit]) = f0().isEmpty
  def iterableOnceF1(f1: Int => IterableOnce[Unit]) = f1(1).isEmpty
  def iterableOnceAR[A <: AnyRef](ar: IterableOnce[A]) = ar.iterator.isEmpty
}
