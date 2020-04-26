package scalabm

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import scala.collection.AbstractIterator
import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

object ScalaArraySeqBM {

  def foreachWhile1[A, B](as: ArraySeq[A])(f: A => Unit): Unit = {
    var i = 0
    while (i < as.length) {
      val a = as(i)
      f(a)
      i += 1
    }
  }

  def foreachWhile2[A, B](as: ArraySeq[A])(f: A => Unit): Unit = {
    val array = as.unsafeArray
    var i = 0
    while (i < array.length) {
      val a = array(i).asInstanceOf[A]
      f(a)
      i += 1
    }
  }

  def foldLeftWhile1[A, B](as: ArraySeq[A], z: B)(f: (B, A) => B): B = {
    var b = z
    var i = 0
    while (i < as.length) {
      val a = as(i)
      b = f(b, a)
      i += 1
    }
    b
  }

  def foldLeftWhile2[A, B](as: ArraySeq[A], z: B)(f: (B, A) => B): B = {
    val array = as.unsafeArray
    var b = z
    var i = 0
    while (i < array.length) {
      val a = array(i).asInstanceOf[A]
      b = f(b, a)
      i += 1
    }
    b
  }

  def foldRightRevIt[A, B](as: ArraySeq[A], z: B)(f: (A, B) => B): B = {
    val it = new AbstractIterator[A] {
      private var i = as.length
      override def hasNext = i > 0
      override def next(): A = {
        i -= 1
        as(i)
      }
    }
    var b = z
    while (it.hasNext)
      b = f(it.next(), b)
    b
  }

  def foldRightWhile1[A, B](as: ArraySeq[A], z: B)(f: (A, B) => B): B = {
    var b = z
    var i = as.length
    while (i > 0) {
      i -= 1
      val a = as(i)
      b = f(a, b)
    }
    b
  }

  def foldRightWhile2[A, B](as: ArraySeq[A], z: B)(f: (A, B) => B): B = {
    val array = as.unsafeArray
    var b = z
    var i = array.length
    while (i > 0) {
      i -= 1
      val a = array(i).asInstanceOf[A]
      b = f(a, b)
    }
    b
  }

  def concat1[A <: AnyRef: ClassTag](x: ArraySeq[A], y: ArraySeq[A]): ArraySeq[A] =
    if (x.isEmpty)
      y
    else if (y.isEmpty)
      x
    else {
      val ax = x.unsafeArray.asInstanceOf[Array[AnyRef]]
      val ay = y.unsafeArray.asInstanceOf[Array[AnyRef]]
      val a = new Array[AnyRef](x.length + y.length)
      System.arraycopy(ax, 0, a, 0, ax.length)
      System.arraycopy(ay, 0, a, ax.length, ay.length)
      ArraySeq.unsafeWrapArray(a).asInstanceOf[ArraySeq[A]]
    }
}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ScalaArraySeqBM {

  val S = ScalaArraySeqBM

  @Param(Array("100", "1000", "10000", "100000"))
  var size: String = _

  var as: ArraySeq[String] = _

  @Setup
  def setup(): Unit = {
    as = ArraySeq.tabulate(size.toInt)(_.toString)
  }

  @Benchmark def foreachCurrent = {var i=0; as.foreach(a => i += a.length); i}
  @Benchmark def foreachWhile1  = {var i=0; S.foreachWhile1(as)(a => i += a.length); i}
  @Benchmark def foreachWhile2  = {var i=0; S.foreachWhile2(as)(a => i += a.length); i}

  @Benchmark def foldLeftCurrent = as.foldLeft(0)(_ + _.length)
  @Benchmark def foldLeftWhile1  = S.foldLeftWhile1(as, 0)(_ + _.length)
  @Benchmark def foldLeftWhile2  = S.foldLeftWhile2(as, 0)(_ + _.length)

  @Benchmark def foldRightCurrent = as.foldRight(0)(_.length + _)
  @Benchmark def foldRightRevIt   = S.foldRightRevIt(as, 0)(_.length + _)
  @Benchmark def foldRightWhile1  = S.foldRightWhile1(as, 0)(_.length + _)
  @Benchmark def foldRightWhile2  = S.foldRightWhile2(as, 0)(_.length + _)

  @Benchmark def concatCurrent    = as ++ as
  @Benchmark def concat1          = S.concat1(as, as)
}