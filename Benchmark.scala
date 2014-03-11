import scalaz.{Coyoneda, Free, ~>, Functor, ReaderT, Kleisli}
import scalaz.Scalaz.Id
import scalaz.effect.IO
import scalaz.Free.FreeC
import scalaz.std.function.function0Instance

class TheRealDeal {
  private val ai = new java.util.concurrent.atomic.AtomicLong()
  def add(b: Long): Unit = ai.addAndGet(b)
  def get(): Long = ai.get
}

object ScalazCandidate {
  def mapSuspensionFreeC[F[_], G[_], A](c: FreeC[F, A], f: F ~> G): FreeC[G, A] = {
    type CoyonedaG[A] = Coyoneda[G, A]
    c.mapSuspension[CoyonedaG](new (({type λ[α] = Coyoneda[F, α]})#λ ~> CoyonedaG){
      def apply[A](a: Coyoneda[F, A]) = a.trans(f)
    })
  }

  def coyoUnapply[F[_]: Functor]: (({type L[x] = Coyoneda[F, x]})#L ~> F) =
    new (({type L[x] = Coyoneda[F, x]})#L ~> F) {
      override def apply[A](m: ({type L[x] = Coyoneda[F, x]})#L[A]): F[A] = m.run
    }

  def liftFC[F[_], A](f: F[A]): FreeC[F, A] = {
    val c: Coyoneda[F, A] = Coyoneda(f) // TODO manual cast until next scalaz ver
    Free.liftFU(c)
  }
}

//======================================================================================================================

object FreeK {
  import Free.{Suspend, Return}

  trait Cmd[R]
  case class Add[R](b: Long, k: () => R) extends Cmd[R]
  case class Get[R](k: Long => R) extends Cmd[R]

  type FreeCmd[A] = Free[Cmd, A]
  def addCmd(b: Long): FreeCmd[Unit] = Suspend(Add(b, () => Return(())))
  val getCmd: FreeCmd[Long] = Suspend(Get(l => Return(l)))

  implicit object CmdFunctor extends Functor[Cmd] {
    override def map[A, B](fa: Cmd[A])(f: A => B): Cmd[B] = fa match {
      case Add(b, k) => Add(b, () => f(k()))
      case Get(k)    => Get(f compose k)
    }
  }

  type ReaderIO[A] = ReaderT[IO, TheRealDeal, A]
  implicit object CmdToReaderIO extends (Cmd ~> ReaderIO) {
    def io[A](f: TheRealDeal => A) = Kleisli((rd: TheRealDeal) => IO{ f(rd) })
    override def apply[A](ta: Cmd[A]): ReaderIO[A] = ta match {
      case Add(n, k) => io{ rd => rd.add(n); k() }
      case Get(k)    => io{ rd => k(rd.get) }
    }
  }

  def build(adds: Int) = {
    val add: FreeCmd[Unit] = addCmd(1)
    val manyAdds: FreeCmd[Unit] = List.fill(adds - 1)(add).foldLeft(add)((a,b) => a >>= (_ => b))
    val program: FreeCmd[Long] = manyAdds >>= (_ => getCmd)
    program
  }

  def runReaderIo(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: Free[ReaderIO, Long] = p1.mapSuspension(CmdToReaderIO)
    val p3: ReaderIO[Long] = p2.runM(identity)
    val r: Long = p3.run(new TheRealDeal).unsafePerformIO()
  }

  def CmdToF0(rd: TheRealDeal): Cmd ~> Function0 = new (Cmd ~> Function0) {
    override def apply[A](m: Cmd[A]): () => A = m match {
      case Add(n, k) => () => { rd.add(n); k() }
      case Get(k)    => () => { k(rd.get) }
    }
  }

  def runF0(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: Free[Function0, Long] = p1.mapSuspension(CmdToF0(new TheRealDeal))
    val r: Long = p2.run
  }

  def CmdToIO(rd: TheRealDeal): Cmd ~> IO = new (Cmd ~> IO) {
    override def apply[A](m: Cmd[A]): IO[A] = m match {
      case Add(n, k) => IO{ rd.add(n); k() }
      case Get(k)    => IO{ k(rd.get) }
    }
  }

  def runIo(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: Free[IO, Long] = p1.mapSuspension(CmdToIO(new TheRealDeal))
    val p3: IO[Long] = p2.runM(identity)
    val r: Long = p3.unsafePerformIO()
  }
}

//======================================================================================================================

object Coyo {
  import ScalazCandidate._

  trait Cmd[A]
  case class Add(b: Long) extends Cmd[Unit]
  case object Get extends Cmd[Long]

  type FreeCmd[A] = FreeC[Cmd, A]
  implicit def autoLiftCmd[A](c: Cmd[A]) = liftFC(c)

  type ReaderIO[A] = ReaderT[IO, TheRealDeal, A]
  implicit object CmdToReaderIO extends (Cmd ~> ReaderIO) {
    def io[A](f: TheRealDeal => A) = Kleisli((rd: TheRealDeal) => IO{ f(rd) })
    override def apply[A](ta: Cmd[A]): ReaderIO[A] = ta match {
      case Add(n) => io{ _.add(n) }
      case Get    => io{ _.get }
    }
  }

  def build(adds: Int) = {
    val add: FreeCmd[Unit] = Add(1)
    val manyAdds: FreeCmd[Unit] = List.fill(adds - 1)(add).foldLeft(add)((a,b) => a >>= (_ => b))
    val program: FreeCmd[Long] = manyAdds >>= (_ => Get)
    program
  }

  def runReaderIo(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: FreeC[ReaderIO, Long] = mapSuspensionFreeC(p1, CmdToReaderIO)
    val p3: Free[ReaderIO, Long] = p2.mapSuspension(coyoUnapply)
    val p4: ReaderIO[Long] = p3.runM(identity)
    val r: Long = p4.run(new TheRealDeal).unsafePerformIO()
  }

  def CmdToF0(rd: TheRealDeal): Cmd ~> Function0 = new (Cmd ~> Function0) {
    override def apply[A](m: Cmd[A]): () => A = m match {
      case Add(n) => () => rd.add(n)
      case Get    => () => rd.get
    }
  }

  def runF0(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: FreeC[Function0, Long] = mapSuspensionFreeC(p1, CmdToF0(new TheRealDeal))
    val p3: Free[Function0, Long] = p2.mapSuspension(coyoUnapply)
    val r: Long = p3.run
  }

  def CmdToIO(rd: TheRealDeal): Cmd ~> IO = new (Cmd ~> IO) {
    override def apply[A](m: Cmd[A]): IO[A] = m match {
      case Add(n) => IO{ rd.add(n) }
      case Get    => IO{ rd.get }
    }
  }

  def runIo(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: FreeC[IO, Long] = mapSuspensionFreeC(p1, CmdToIO(new TheRealDeal))
    val p3: Free[IO, Long] = p2.mapSuspension(coyoUnapply)
    val p4: IO[Long] = p3.runM(identity)
    val r: Long = p4.unsafePerformIO()
  }
}

//======================================================================================================================

import org.scalameter.api._

object FunctionalEffectBenchmark extends PerformanceTest.Microbenchmark {
// object FunctionalEffectBenchmark extends PerformanceTest.Quickbenchmark {
  val sizes = Gen.exponential("size")(10, 10000, 10)

  measure method "Manual" in {
    using(sizes) in { s =>
      val rd = new TheRealDeal
      (1 to s).foreach(_ => rd.add(1))
      rd.get()
    }
  }

  measure method "Free monad -> Fn0 Trampoline" in { using(sizes) in { FreeK.runF0 } }
  measure method "Free monad -> IO"             in { using(sizes) in { FreeK.runIo } }
  measure method "Free monad -> Reader[IO]"     in { using(sizes) in { FreeK.runReaderIo } }

  measure method "Free monad & coyoneda -> Fn0 Trampoline" in { using(sizes) in { Coyo.runF0 } }
  measure method "Free monad & coyoneda -> IO"             in { using(sizes) in { Coyo.runIo } }
  measure method "Free monad & coyoneda -> Reader[IO]"     in { using(sizes) in { Coyo.runReaderIo } }

}

