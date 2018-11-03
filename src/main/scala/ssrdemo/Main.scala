package ssrdemo

object Main {

  def main(args: Array[String]): Unit = {
//    new NashronSSR2
//    new Graal
    new Graal2
//    issue.GraalWarmup.run()
  }
}

object Stats {
  def percentile(p: Int)(seq: Seq[Long]): Long = {
    require(0 <= p && p <= 100)                      // some value requirements
    require(seq.nonEmpty)                            // more value requirements
    val sorted = seq.sorted
    val k = math.ceil((seq.length - 1) * (p / 100.0)).toInt
    sorted(k)
  }

  def time[A](name: String, a: => A): A = {
    val start = System.currentTimeMillis()
    val result = a
    val end = System.currentTimeMillis()
    val ms = end - start
    printf("%s: %,d ms\n", name, ms)
    result
  }

  def times[A <: AnyRef](n: Int, a: => A): Vector[Long] = {
    var i = 0
    val stats = Vector.fill(n) {
      val start = System.currentTimeMillis()
      a
      val end = System.currentTimeMillis()
      if (a eq null) i += i
      end - start
    }
    if (i == 934983845) println("AH")
    stats
  }
}