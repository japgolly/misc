package benchmark

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole
import scala.collection.immutable._

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(3)
@BenchmarkMode(Array(Mode.AverageTime /*, Mode.SampleTime , Mode.Throughput*/))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class MapConsume {

  @Param(Array("10", "100", "1000", "10000", "100000"))
  var size: Int = _

  @Param(Array(
    "list", "stream", "vector", "queue", "stack"
    ,
    "set", "listset", "hashset", "treeset", "bitset"
    // TODO Hmmm, I want to see Scalaz too
  ))
  var colType: String = _

  private var data: Traversable[Int] = _

  @Setup
  def setup(): Unit = {
    val r = 1 to size
    data = colType match {
      case "queue"   => Queue(r: _*)
      case "stack"   => Stack(r: _*)
      case "set"     => r.toSet
      case "listset" => ListSet.empty[Int] ++ r
      case "hashset" => HashSet.empty[Int] ++ r
      case "treeset" => TreeSet.empty[Int] ++ r
      case "bitset"  => BitSet.empty ++ r
      case "vector"  => r.toVector
      case "list"    => r.toList
      case "stream"  => r.toStream
    }
  }

  val id_int: Int => Int = _ + 1

  def consumeInts(bh: Blackhole, is: TraversableOnce[Int]): Unit =
    is foreach (bh consume _)

  @Benchmark def direct  (bh: Blackhole) = consumeInts(bh, data.map(id_int))
  @Benchmark def iterator(bh: Blackhole) = consumeInts(bh, data.toIterator.map(id_int))
}
