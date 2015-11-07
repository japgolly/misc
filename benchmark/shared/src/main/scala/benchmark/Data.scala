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
  
  case class CCI(int: Int)

  private def ccis(count: Int): Iterator[CCI] =
    ints(count).map(CCI)

  val   list_cci_100 = ccis(100).toList
//  val    set_cci_100 = ccis(100).toSet
//  val vector_cci_100 = ccis(100).toVector
//  val stream_cci_100 = ccis(100).toStream
//
  val   list_cci_10000 = ccis(10000).toList
//  val    set_cci_10000 = ccis(10000).toSet
//  val vector_cci_10000 = ccis(10000).toVector
//  val stream_cci_10000 = ccis(10000).toStream

}
