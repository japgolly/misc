import scalaz.{Coyoneda, Free, ~>, Functor, ReaderT, Kleisli}
import scalaz.Scalaz.Id
import scalaz.effect.IO
import scalaz.Free.{liftFC, FreeC}
import scalaz.Coyoneda.liftTF
import scalaz.std.function.function0Instance

class TheRealDeal {
  private val ai = new java.util.concurrent.atomic.AtomicLong()
  def add(b: Long): Unit = ai.addAndGet(b)
  def get(): Long = ai.get
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
    val p2: ReaderIO[Long] = p1.foldMap(CmdToReaderIO)
    val r: Long = p2.run(new TheRealDeal).unsafePerformIO()
  }

  def CmdToF0(rd: TheRealDeal): Cmd ~> Function0 = new (Cmd ~> Function0) {
    override def apply[A](m: Cmd[A]): () => A = m match {
      case Add(n, k) => () => { rd.add(n); k() }
      case Get(k)    => () => { k(rd.get) }
    }
  }

  def runF0_run(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: Free[Function0, Long] = p1.mapSuspension(CmdToF0(new TheRealDeal))
    val r: Long = p2.run
  }

  def runF0_fold(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: Function0[Long] = p1.foldMap(CmdToF0(new TheRealDeal))
    val r: Long = p2()
  }

  def CmdToIO(rd: TheRealDeal): Cmd ~> IO = new (Cmd ~> IO) {
    override def apply[A](m: Cmd[A]): IO[A] = m match {
      case Add(n, k) => IO{ rd.add(n); k() }
      case Get(k)    => IO{ k(rd.get) }
    }
  }

  def runIo(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: IO[Long] = p1.foldMap(CmdToIO(new TheRealDeal))
    val r: Long = p2.unsafePerformIO()
  }

  type ReaderF[A] = ReaderT[Function0, TheRealDeal, A]
  implicit object CmdToReaderF extends (Cmd ~> ReaderF) {
    def io[A](f: TheRealDeal => A) = Kleisli((rd: TheRealDeal) => () => f(rd))
    override def apply[A](ta: Cmd[A]): ReaderF[A] = ta match {
      case Add(n, k) => io{ rd => rd.add(n); k() }
      case Get(k)    => io{ rd => k(rd.get) }
    }
  }

  def runReaderF(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: ReaderF[Long] = p1.foldMap(CmdToReaderF)
    val r: Long = p2.run(new TheRealDeal)()
  }
}

//======================================================================================================================

object Coyo {

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
  val CmdToReaderIO_ = liftTF(CmdToReaderIO)

  def build(adds: Int) = {
    val add: FreeCmd[Unit] = Add(1)
    val manyAdds: FreeCmd[Unit] = List.fill(adds - 1)(add).foldLeft(add)((a,b) => a >>= (_ => b))
    val program: FreeCmd[Long] = manyAdds >>= (_ => Get)
    program
  }

  def runReaderIo(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: ReaderIO[Long] = p1.foldMap(CmdToReaderIO_)
    val r: Long = p2.run(new TheRealDeal).unsafePerformIO()
  }

  def CmdToF0(rd: TheRealDeal): Cmd ~> Function0 = new (Cmd ~> Function0) {
    override def apply[A](m: Cmd[A]): () => A = m match {
      case Add(n) => () => rd.add(n)
      case Get    => () => rd.get
    }
  }

  def runF0_fold(adds: Int): Unit = {
    val p1 = build(adds)
    val nt = liftTF(CmdToF0(new TheRealDeal))
    val p2: Function0[Long] = p1.foldMap(nt)
    val r: Long = p2()
  }

  def runF0_run(adds: Int): Unit = {
    val p1 = build(adds)
    val nt = liftTF(CmdToF0(new TheRealDeal))
    val p2: Free[Function0, Long] = p1.mapSuspension(nt)
    val r: Long = p2.run
  }

  def CmdToIO(rd: TheRealDeal): Cmd ~> IO = new (Cmd ~> IO) {
    override def apply[A](m: Cmd[A]): IO[A] = m match {
      case Add(n) => IO{ rd.add(n) }
      case Get    => IO{ rd.get }
    }
  }

  def runIo(adds: Int): Unit = {
    val p1 = build(adds)
    val nt = liftTF(CmdToIO(new TheRealDeal))
    val p2: IO[Long] = p1.foldMap(nt)
    val r: Long = p2.unsafePerformIO()
  }

  type ReaderF[A] = ReaderT[Function0, TheRealDeal, A]
  implicit object CmdToReaderF extends (Cmd ~> ReaderF) {
    def io[A](f: TheRealDeal => A) = Kleisli((rd: TheRealDeal) => () => f(rd))
    override def apply[A](ta: Cmd[A]): ReaderF[A] = ta match {
      case Add(n) => io{ _.add(n) }
      case Get    => io{ _.get }
    }
  }
  val CmdToReaderF_ = liftTF(CmdToReaderF)

  def runReaderF(adds: Int): Unit = {
    val p1 = build(adds)
    val p2: ReaderF[Long] = p1.foldMap(CmdToReaderF_)
    val r: Long = p2.run(new TheRealDeal)()
  }
}

//======================================================================================================================

import org.scalameter.api._

object FunctionalEffectBenchmark extends PerformanceTest.Microbenchmark {
// object FunctionalEffectBenchmark extends PerformanceTest.Quickbenchmark {

//  val sizes = Gen.exponential("size")(10, 10000, 10)
  val sizes = Gen.single("size")(10000)

  measure method "Manual" in {
    using(sizes) in { s =>
      val rd = new TheRealDeal
      (1 to s).foreach(_ => rd.add(1))
      rd.get()
    }
  }

  val s = "================================================\n::Benchmark "

  measure method s"${s}FreeM -> Fn0 Tramp (run)"  in { using(sizes) in { FreeK.runF0_run } }
  measure method "FreeM -> Fn0 Tramp (fold)"      in { using(sizes) in { FreeK.runF0_fold } }
  measure method "FreeM -> Reader[F0]"            in { using(sizes) in { FreeK.runReaderF } }
  measure method "FreeM -> IO"                    in { using(sizes) in { FreeK.runIo } }
  measure method "FreeM -> Reader[IO]"            in { using(sizes) in { FreeK.runReaderIo } }

  measure method s"${s}FreeM & CoYo -> Fn0 Tramp (run)" in { using(sizes) in { Coyo.runF0_run } }
  measure method "FreeM & CoYo -> Fn0 Tramp (fold)"     in { using(sizes) in { Coyo.runF0_fold } }
  measure method "FreeM & CoYo -> Reader[F0]"           in { using(sizes) in { Coyo.runReaderF } }
  measure method "FreeM & CoYo -> IO"                   in { using(sizes) in { Coyo.runIo } }
  measure method "FreeM & CoYo -> Reader[IO]"           in { using(sizes) in { Coyo.runReaderIo } }

}

