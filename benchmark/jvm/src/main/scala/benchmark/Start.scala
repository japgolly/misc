package benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class Start {

  val id_int: Int => Int = _ + 1

  def sumInts(is: TraversableOnce[Int]): Int = {
    var i = 0
    is foreach (i += _)
    i
  }

  val dupIntList    : Int => List    [Int] = i => i :: -i :: Nil
  val dupIntSet     : Int => Set     [Int] = i => Set(i, -i)
  val dupIntVector  : Int => Vector  [Int] = i => Vector(i, -i)
  val dupIntStream  : Int => Stream  [Int] = i => Stream(i, -i)
  val dupIntIterator: Int => Iterator[Int] = i => (i :: -i :: Nil).iterator

  @Benchmark def mapConsume_100_set            = sumInts(Data.   set_int_100         .map(id_int))
  @Benchmark def mapConsume_100_list           = sumInts(Data.  list_int_100         .map(id_int))
  @Benchmark def mapConsume_100_stream         = sumInts(Data.stream_int_100         .map(id_int))
  @Benchmark def mapConsume_100_vector         = sumInts(Data.vector_int_100         .map(id_int))
  @Benchmark def mapConsume_100_setIterator    = sumInts(Data.   set_int_100.iterator.map(id_int))
  @Benchmark def mapConsume_100_listIterator   = sumInts(Data.  list_int_100.iterator.map(id_int))
  @Benchmark def mapConsume_100_streamIterator = sumInts(Data.stream_int_100.iterator.map(id_int))
  @Benchmark def mapConsume_100_vectorIterator = sumInts(Data.vector_int_100.iterator.map(id_int))

  @Benchmark def mapConsume_10000_set            = sumInts(Data.   set_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_list           = sumInts(Data.  list_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_stream         = sumInts(Data.stream_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_vector         = sumInts(Data.vector_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_setIterator    = sumInts(Data.   set_int_10000.iterator.map(id_int))
  @Benchmark def mapConsume_10000_listIterator   = sumInts(Data.  list_int_10000.iterator.map(id_int))
  @Benchmark def mapConsume_10000_streamIterator = sumInts(Data.stream_int_10000.iterator.map(id_int))
  @Benchmark def mapConsume_10000_vectorIterator = sumInts(Data.vector_int_10000.iterator.map(id_int))

  
  @Benchmark def flatmapConsume_100_set            = sumInts(Data.   set_int_100         .flatMap(dupIntSet))
  @Benchmark def flatmapConsume_100_list           = sumInts(Data.  list_int_100         .flatMap(dupIntList))
  @Benchmark def flatmapConsume_100_stream         = sumInts(Data.stream_int_100         .flatMap(dupIntStream))
  @Benchmark def flatmapConsume_100_vector         = sumInts(Data.vector_int_100         .flatMap(dupIntVector))
  @Benchmark def flatmapConsume_100_setIterator    = sumInts(Data.   set_int_100.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_100_listIterator   = sumInts(Data.  list_int_100.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_100_streamIterator = sumInts(Data.stream_int_100.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_100_vectorIterator = sumInts(Data.vector_int_100.iterator.flatMap(dupIntIterator))

  @Benchmark def flatmapConsume_10000_set            = sumInts(Data.   set_int_10000         .flatMap(dupIntSet))
  @Benchmark def flatmapConsume_10000_list           = sumInts(Data.  list_int_10000         .flatMap(dupIntList))
  @Benchmark def flatmapConsume_10000_stream         = sumInts(Data.stream_int_10000         .flatMap(dupIntStream))
  @Benchmark def flatmapConsume_10000_vector         = sumInts(Data.vector_int_10000         .flatMap(dupIntVector))
  @Benchmark def flatmapConsume_10000_setIterator    = sumInts(Data.   set_int_10000.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_10000_listIterator   = sumInts(Data.  list_int_10000.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_10000_streamIterator = sumInts(Data.stream_int_10000.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_10000_vectorIterator = sumInts(Data.vector_int_10000.iterator.flatMap(dupIntIterator))
}
