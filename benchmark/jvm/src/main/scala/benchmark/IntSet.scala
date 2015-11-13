package benchmark

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole
import scala.collection.immutable._
import scala.collection.mutable

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(3)
@BenchmarkMode(Array(Mode.AverageTime /*, Mode.SampleTime , Mode.Throughput*/))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class IntSet {

  @Param(Array("10", "100", "1000", "10000"))
  var size: Int = _

  var colType: String = _

  private var is: List[Int] = null

  @Setup
  def setup(): Unit = {
    // Puts it in a non-linear, deterministic order then change to disrupt hash order
    is = (-size to -1).toSet.iterator.map(-(_: Int)).toList
  }

  @Benchmark def immutableSet = {
    var s = Set.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def mutableSet = {
    val s = mutable.Set.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def intMap = {
    var s = IntMap.empty[Unit]
    for (i <- is) if (s contains i) ??? else s = s.updated(i, ())
    s
  }

  @Benchmark def listSet = {
    var s = ListSet.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def immutableHashSet = {
    var s = HashSet.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def mutableHashSet = {
    val s = mutable.HashSet.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def immutableTreeSet = {
    var s = TreeSet.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def mutableTreeSet = {
    val s = mutable.TreeSet.empty[Int]
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def immutableBitSet = {
    var s = BitSet.empty
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def mutableBitSet = {
    val s = mutable.BitSet.empty
    for (i <- is) if (s contains i) ??? else s += i
    s
  }

  @Benchmark def mutableBitSetAdd = {
    val s = mutable.BitSet.empty
    for (i <- is) if (!s.add(i)) ???
    s
  }

  // ===================================================================================================================
  // Add then check by ref

  @Benchmark def immutableSetEq = {
    var s = Set.empty[Int]
    for (i <- is) {val b = s; s += i; if (b eq s) ???}
    s
  }

  @Benchmark def listSetEq = {
    var s = ListSet.empty[Int]
    for (i <- is) {val b = s; s += i; if (b eq s) ???}
    s
  }

  @Benchmark def immutableHashSetEq = {
    var s = HashSet.empty[Int]
    for (i <- is) {val b = s; s += i; if (b eq s) ???}
    s
  }

  @Benchmark def immutableBitSetEq = {
    var s = BitSet.empty
    for (i <- is) {val b = s; s += i; if (b eq s) ???}
    s
  }

  /*

  // TRUE
  def test_immutableSet = {
    var s = Set.empty[Int]
    (1 to 1000).forall { i =>
      s += i
      val a = s
      s += i
      a eq s
    }
  }

  // false
  def test_intMap = {
    var s = IntMap.empty[Unit]
    (1 to 1000).forall { i =>
      s = s.updated(i, ())
      val a = s
      s = s.updated(i, ())
      a eq s
    }
  }

  // TRUE
  def test_listSet = {
    var s = ListSet.empty[Int]
    (1 to 1000).forall { i =>
      s += i
      val a = s
      s += i
      a eq s
    }
  }

  // TRUE
  def test_immutableHashSet = {
    var s = HashSet.empty[Int]
    (1 to 1000).forall { i =>
      s += i
      val a = s
      s += i
      a eq s
    }
  }

  // false
  def test_immutableTreeSet = {
    var s = TreeSet.empty[Int]
    (1 to 1000).forall { i =>
      s += i
      val a = s
      s += i
      a eq s
    }
  }

  // TRUE
  def test_immutableBitSet = {
    var s = BitSet.empty
    (1 to 1000).forall { i =>
      s += i
      val a = s
      s += i
      a eq s
    }
  }

  */
}
