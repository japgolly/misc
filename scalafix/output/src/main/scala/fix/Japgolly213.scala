package fix

object Japgolly213 {

  def iterableOnce(as: IterableOnce[Unit]) = {

    // one
    Nil.foreach(_ => ())
    as.iterator.foreach(_ => ())

    // two
    val f = () => () => as
    def omg(i: Int) = as
    for {
      _ <- as.iterator
      _ <- Nil
      // _ <- f()()
      _ <- omg(7).iterator
    } ()

    // three
    for {
      _ <- Nil
      _ <- as.iterator
    } yield ()
  }

  def iterableOnceBN(bn: => IterableOnce[Unit]) = bn.iterator.isEmpty
  def iterableOnceF0(f0: () => IterableOnce[Unit]) = f0().iterator.isEmpty
  def iterableOnceF1(f1: Int => IterableOnce[Unit]) = f1(1).iterator.isEmpty
  def iterableOnceAR[A <: AnyRef](ar: IterableOnce[A]) = ar.iterator.isEmpty
}
