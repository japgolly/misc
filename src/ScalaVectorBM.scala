package scalabm

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import scala.collection.AbstractIterator
import scala.reflect.ClassTag

object ScalaVectorBM {

  def foreachWhile1[A, B](as: Vector[A])(f: A => Unit): Unit = {
    var i = 0
    while (i < as.length) {
      val a = as(i)
      f(a)
      i += 1
    }
  }

  def foldLeftWhile1[A, B](as: Vector[A], z: B)(f: (B, A) => B): B = {
    var b = z
    var i = 0
    while (i < as.length) {
      val a = as(i)
      b = f(b, a)
      i += 1
    }
    b
  }

  def foldRightRevIt[A, B](as: Vector[A], z: B)(f: (A, B) => B): B = {
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

  def foldRightWhile1[A, B](as: Vector[A], z: B)(f: (A, B) => B): B = {
    var b = z
    var i = as.length
    while (i > 0) {
      i -= 1
      val a = as(i)
      b = f(a, b)
    }
    b
  }
}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ScalaVectorBM {

  val S = ScalaVectorBM

  @Param(Array("100", "1000", "10000", "100000"))
  var size: String = _

  var as: Vector[String] = _

  @Setup
  def setup(): Unit = {
    as = Vector.tabulate(size.toInt)(_.toString)
  }

  @Benchmark def foreachCurrent = {var i=0; as.foreach(a => i += a.length); i}
  @Benchmark def foreachWhile1  = {var i=0; S.foreachWhile1(as)(a => i += a.length); i}

  @Benchmark def foldLeftCurrent = as.foldLeft(0)(_ + _.length)
  @Benchmark def foldLeftWhile1  = S.foldLeftWhile1(as, 0)(_ + _.length)

  @Benchmark def foldRightCurrent = as.foldRight(0)(_.length + _)
  @Benchmark def foldRightRevIt   = S.foldRightRevIt(as, 0)(_.length + _)
  @Benchmark def foldRightWhile1  = S.foldRightWhile1(as, 0)(_.length + _)
}