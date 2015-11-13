package benchmark

object Data {

  private def ints(count: Int): Iterator[Int] =
    (1 to count).iterator

  val   list_int_100 = ints(100).toList
  val    set_int_100 = ints(100).toSet
  val vector_int_100 = ints(100).toVector
  val stream_int_100 = ints(100).toStream

  val   list_int_10000 = ints(10000).toList
  val    set_int_10000 = ints(10000).toSet
  val vector_int_10000 = ints(10000).toVector
  val stream_int_10000 = ints(10000).toStream
}
