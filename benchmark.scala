import org.scalameter.api._

object Benchmark extends PerformanceTest.Quickbenchmark {

  val fn_ii: Int => Int      = _ / 2 + 100
  def method_ii(i: Int): Int = i / 2 + 100

  val fn_is: Int => String      = i => (i - 100).toString
  def method_is(i: Int): String = (i - 100).toString

  val fn_si: String => Int      = _.length + 100
  def method_si(i: String): Int = i.length + 100

  val fn_ss: String => String      = _ + "!"
  def method_ss(i: String): String = i + "!"

  val sizes = Gen.single("size")(1000000)

  val ranges = for {
    size <- sizes
  } yield 0 until size

  val rangeS = ranges.map(_.map(_.toString))

  performance of "Dyn: int -> int" in {
    measure method "method"   in { using(ranges) in { _ map method_ii }}
    measure method "function" in { using(ranges) in { _ map fn_ii }}
  }

  performance of "Direct: int -> int" in {
    measure method "method"   in { using(ranges) in { _.foreach(i => method_ii(i+1)) }}
    measure method "function" in { using(ranges) in { _.foreach(i => fn_ii(i+1)) }}
  }

  performance of "Dyn: int -> string" in {
    measure method "method"   in { using(ranges) in { _ map method_is }}
    measure method "function" in { using(ranges) in { _ map fn_is }}
  }

  performance of "Direct: int -> string" in {
    measure method "method"   in { using(ranges) in { _.foreach(i => method_is(i+1)) }}
    measure method "function" in { using(ranges) in { _.foreach(i => fn_is(i+1)) }}
  }

  performance of "Dyn: string -> int" in {
    measure method "method"   in { using(rangeS) in { _ map method_si }}
    measure method "function" in { using(rangeS) in { _ map fn_si }}
  }

  performance of "Direct: string -> int" in {
    measure method "method"   in { using(rangeS) in { _.foreach(i => method_si(i+1)) }}
    measure method "function" in { using(rangeS) in { _.foreach(i => fn_si(i+1)) }}
  }

  performance of "Dyn: string -> string" in {
    measure method "method"   in { using(rangeS) in { _ map method_ss }}
    measure method "function" in { using(rangeS) in { _ map fn_ss }}
  }

  performance of "Direct: string -> string" in {
    measure method "method"   in { using(rangeS) in { _.foreach(i => method_ss(i+1)) }}
    measure method "function" in { using(rangeS) in { _.foreach(i => fn_ss(i+1)) }}
  }
}
