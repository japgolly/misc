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

//@Benchmark def mapConsume_100_set            = sumInts(Data.   set_int_100         .map(id_int))
  @Benchmark def mapConsume_100_list           = sumInts(Data.  list_int_100         .map(id_int))
  @Benchmark def mapConsume_100_stream         = sumInts(Data.stream_int_100         .map(id_int))
  @Benchmark def mapConsume_100_vector         = sumInts(Data.vector_int_100         .map(id_int))
//@Benchmark def mapConsume_100_setIterator    = sumInts(Data.   set_int_100.iterator.map(id_int))
  @Benchmark def mapConsume_100_listIterator   = sumInts(Data.  list_int_100.iterator.map(id_int))
  @Benchmark def mapConsume_100_streamIterator = sumInts(Data.stream_int_100.iterator.map(id_int))
  @Benchmark def mapConsume_100_vectorIterator = sumInts(Data.vector_int_100.iterator.map(id_int))

//@Benchmark def mapConsume_10000_set            = sumInts(Data.   set_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_list           = sumInts(Data.  list_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_stream         = sumInts(Data.stream_int_10000         .map(id_int))
  @Benchmark def mapConsume_10000_vector         = sumInts(Data.vector_int_10000         .map(id_int))
//@Benchmark def mapConsume_10000_setIterator    = sumInts(Data.   set_int_10000.iterator.map(id_int))
  @Benchmark def mapConsume_10000_listIterator   = sumInts(Data.  list_int_10000.iterator.map(id_int))
  @Benchmark def mapConsume_10000_streamIterator = sumInts(Data.stream_int_10000.iterator.map(id_int))
  @Benchmark def mapConsume_10000_vectorIterator = sumInts(Data.vector_int_10000.iterator.map(id_int))

  
//@Benchmark def flatmapConsume_100_set            = sumInts(Data.   set_int_100         .flatMap(dupIntSet))
  @Benchmark def flatmapConsume_100_list           = sumInts(Data.  list_int_100         .flatMap(dupIntList))
  @Benchmark def flatmapConsume_100_stream         = sumInts(Data.stream_int_100         .flatMap(dupIntStream))
  @Benchmark def flatmapConsume_100_vector         = sumInts(Data.vector_int_100         .flatMap(dupIntVector))
//@Benchmark def flatmapConsume_100_setIterator    = sumInts(Data.   set_int_100.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_100_listIterator   = sumInts(Data.  list_int_100.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_100_streamIterator = sumInts(Data.stream_int_100.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_100_vectorIterator = sumInts(Data.vector_int_100.iterator.flatMap(dupIntIterator))

//@Benchmark def flatmapConsume_10000_set            = sumInts(Data.   set_int_10000         .flatMap(dupIntSet))
  @Benchmark def flatmapConsume_10000_list           = sumInts(Data.  list_int_10000         .flatMap(dupIntList))
  @Benchmark def flatmapConsume_10000_stream         = sumInts(Data.stream_int_10000         .flatMap(dupIntStream))
  @Benchmark def flatmapConsume_10000_vector         = sumInts(Data.vector_int_10000         .flatMap(dupIntVector))
//@Benchmark def flatmapConsume_10000_setIterator    = sumInts(Data.   set_int_10000.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_10000_listIterator   = sumInts(Data.  list_int_10000.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_10000_streamIterator = sumInts(Data.stream_int_10000.iterator.flatMap(dupIntIterator))
  @Benchmark def flatmapConsume_10000_vectorIterator = sumInts(Data.vector_int_10000.iterator.flatMap(dupIntIterator))

//@Benchmark def mapMapConsume_100_set            = sumInts(Data.   set_int_100         .map(id_int).map(id_int))
  @Benchmark def mapMapConsume_100_list           = sumInts(Data.  list_int_100         .map(id_int).map(id_int))
  @Benchmark def mapMapConsume_100_stream         = sumInts(Data.stream_int_100         .map(id_int).map(id_int))
  @Benchmark def mapMapConsume_100_vector         = sumInts(Data.vector_int_100         .map(id_int).map(id_int))
//@Benchmark def mapMapConsume_100_setIterator    = sumInts(Data.   set_int_100.iterator.map(id_int).map(id_int))
  @Benchmark def mapMapConsume_100_listIterator   = sumInts(Data.  list_int_100.iterator.map(id_int).map(id_int))
  @Benchmark def mapMapConsume_100_streamIterator = sumInts(Data.stream_int_100.iterator.map(id_int).map(id_int))
  @Benchmark def mapMapConsume_100_vectorIterator = sumInts(Data.vector_int_100.iterator.map(id_int).map(id_int))

//@Benchmark def mapMapConsume_10000_set            = sumInts(Data.   set_int_10000         .map(id_int).map(id_int))
  @Benchmark def mapMapConsume_10000_list           = sumInts(Data.  list_int_10000         .map(id_int).map(id_int))
  @Benchmark def mapMapConsume_10000_stream         = sumInts(Data.stream_int_10000         .map(id_int).map(id_int))
  @Benchmark def mapMapConsume_10000_vector         = sumInts(Data.vector_int_10000         .map(id_int).map(id_int))
//@Benchmark def mapMapConsume_10000_setIterator    = sumInts(Data.   set_int_10000.iterator.map(id_int).map(id_int))
  @Benchmark def mapMapConsume_10000_listIterator   = sumInts(Data.  list_int_10000.iterator.map(id_int).map(id_int))
  @Benchmark def mapMapConsume_10000_streamIterator = sumInts(Data.stream_int_10000.iterator.map(id_int).map(id_int))
  @Benchmark def mapMapConsume_10000_vectorIterator = sumInts(Data.vector_int_10000.iterator.map(id_int).map(id_int))

  
//@Benchmark def flatmapFlatmapConsume_100_set            = sumInts(Data.   set_int_100         .flatMap(dupIntSet)     .flatMap(dupIntSet))
  @Benchmark def flatmapFlatmapConsume_100_list           = sumInts(Data.  list_int_100         .flatMap(dupIntList)    .flatMap(dupIntList))
  @Benchmark def flatmapFlatmapConsume_100_stream         = sumInts(Data.stream_int_100         .flatMap(dupIntStream)  .flatMap(dupIntStream))
  @Benchmark def flatmapFlatmapConsume_100_vector         = sumInts(Data.vector_int_100         .flatMap(dupIntVector)  .flatMap(dupIntVector))
//@Benchmark def flatmapFlatmapConsume_100_setIterator    = sumInts(Data.   set_int_100.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
  @Benchmark def flatmapFlatmapConsume_100_listIterator   = sumInts(Data.  list_int_100.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
  @Benchmark def flatmapFlatmapConsume_100_streamIterator = sumInts(Data.stream_int_100.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
  @Benchmark def flatmapFlatmapConsume_100_vectorIterator = sumInts(Data.vector_int_100.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))

//@Benchmark def flatmapFlatmapConsume_10000_set            = sumInts(Data.   set_int_10000         .flatMap(dupIntSet)     .flatMap(dupIntSet))
  @Benchmark def flatmapFlatmapConsume_10000_list           = sumInts(Data.  list_int_10000         .flatMap(dupIntList)    .flatMap(dupIntList))
  @Benchmark def flatmapFlatmapConsume_10000_stream         = sumInts(Data.stream_int_10000         .flatMap(dupIntStream)  .flatMap(dupIntStream))
  @Benchmark def flatmapFlatmapConsume_10000_vector         = sumInts(Data.vector_int_10000         .flatMap(dupIntVector)  .flatMap(dupIntVector))
//@Benchmark def flatmapFlatmapConsume_10000_setIterator    = sumInts(Data.   set_int_10000.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
  @Benchmark def flatmapFlatmapConsume_10000_listIterator   = sumInts(Data.  list_int_10000.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
  @Benchmark def flatmapFlatmapConsume_10000_streamIterator = sumInts(Data.stream_int_10000.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
  @Benchmark def flatmapFlatmapConsume_10000_vectorIterator = sumInts(Data.vector_int_10000.iterator.flatMap(dupIntIterator).flatMap(dupIntIterator))
}

@State(Scope.Benchmark)
class Start2 {

  // list -> set
  // vector -> set

  @Benchmark def listToSet_100_fold: Set[Int] =
    Data.list_cci_100.foldLeft(Set.empty[Int])(_ + _.int)

  @Benchmark def listToSet_100_builder: Set[Int] = {
    val b = Set.newBuilder[Int]
    Data.list_cci_100.foreach(b += _.int)
    b.result()
  }

  @Benchmark def listToSet_100_breakOut: Set[Int] =
    Data.list_cci_100.map(_.int)(collection.breakOut)

  @Benchmark def listToSet_100_iterator: Set[Int] =
    Data.list_cci_100.iterator.map(_.int).toSet
}


@State(Scope.Benchmark)
class Start3 {

  def sumInts(is: Traversable[Int]): Int = {
    var i = 0
    is foreach (i += _)
    i
  }

  val range_100 = (1 to 100)
  def it_100 = range_100.iterator

  @Benchmark def itStableConsume_100_list   = sumInts(it_100.toList)
  @Benchmark def itStableConsume_100_seq    = sumInts(it_100.toSeq)
  @Benchmark def itStableConsume_100_vector = sumInts(it_100.toVector)
  @Benchmark def itStableConsume_100_stream = sumInts(it_100.toStream)
  @Benchmark def itStableConsume_100_array  = sumInts(it_100.toArray)
}


object VectorIndex {
    implicit class VectorExt[A](private val as: Vector[A]) extends AnyVal {

      def getOrNull(index: Int)(implicit ev: Null <:< A): A =
        if (index >= 0 && index < as.length)
          as(index)
        else
          null.asInstanceOf[A]
    }
}

@State(Scope.Benchmark)
class VectorIndex {
  import VectorIndex._

  val vector = Vector("1", "3", "5")

  def get_null(i: Int): String =
    vector.getOrNull(i)

  def get_catch(i: Int): Option[String] =
    try
      Some(vector(i))
    catch {
      case _: IndexOutOfBoundsException => None
    }

  def get_check(i: Int): Option[String] =
    if (i >= 0 && i < vector.length)
      Some(vector(i))
    else
      None

  @Benchmark def get_ok_catch = get_catch(1)
  @Benchmark def get_ok_check = get_check(1)
  @Benchmark def get_ok_null  = get_check(1)

  @Benchmark def get_ko_catch = get_catch(5)
  @Benchmark def get_ko_check = get_check(5)
  @Benchmark def get_ko_null  = get_check(5)

}


@State(Scope.Benchmark)
class BuildSets {

  /*
[info] # Run complete. Total time: 00:04:02
[info]
[info] Benchmark                     Mode  Cnt         Score        Error  Units

[info] BuildSets.listSet_build_4_1  thrpt   20   3492017.289 ±  95198.464  ops/s
[info] BuildSets.hashSet_build_4_1  thrpt   20   4805881.904 ± 111257.665  ops/s
[info] BuildSets.set_build_4_1      thrpt   20   5827574.148 ± 135814.536  ops/s

[info] BuildSets.set_fold_4_1       thrpt   20   6264597.574 ± 164306.460  ops/s
[info] BuildSets.hashSet_fold_4_1   thrpt   20   7036739.420 ± 383055.183  ops/s
[info] BuildSets.listSet_fold_4_1   thrpt   20  16278173.068 ± 294011.380  ops/s

   */

  import scala.collection.immutable.{HashSet, ListSet}
  
  val items_4_1 = List(1, 3, 7, 3, 5).map(_.toString)

  def set_fold[A](as: List[A]): Set[A] =
    as.foldLeft(Set.empty[A])(_ + _)

  def set_build[A](as: List[A]): Set[A] = {
    val b = Set.newBuilder[A]
    as foreach (b += _)
    b.result()
  }

  def hashSet_fold[A](as: List[A]): HashSet[A] =
    as.foldLeft(HashSet.empty[A])(_ + _)

  def hashSet_build[A](as: List[A]): HashSet[A] = {
    val b = HashSet.newBuilder[A]
    as foreach (b += _)
    b.result()
  }

  def listSet_fold[A](as: List[A]): ListSet[A] =
    as.foldLeft(ListSet.empty[A])(_ + _)

  def listSet_build[A](as: List[A]): ListSet[A] = {
    val b = ListSet.newBuilder[A]
    as foreach (b += _)
    b.result()
  }

  @Benchmark def set_fold_4_1      = set_fold(items_4_1)
  @Benchmark def set_build_4_1     = set_build(items_4_1)
  @Benchmark def hashSet_fold_4_1  = hashSet_fold(items_4_1)
  @Benchmark def hashSet_build_4_1 = hashSet_build(items_4_1)
  @Benchmark def listSet_fold_4_1  = listSet_fold(items_4_1)
  @Benchmark def listSet_build_4_1 = listSet_build(items_4_1)
}