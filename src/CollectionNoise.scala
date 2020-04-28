package scalabm

import nyaya.gen.Gen
import org.openjdk.jmh.infra.Blackhole
import scala.collection.immutable.ArraySeq

object CollectionNoise {

  def apply(bh: Blackhole, size: Int): Unit = {
    val half = size >> 1

    val crap: Gen[Vector[String]] =
      for {
        _   <- Gen.setSeed(0)
        map <- Gen.int.list(0 to size).mapBy(Gen.ascii.string(16 to 64))(half to size)
        vec <- Gen.char.vector(half to size)
      } yield {
        val vl = map.toVector.foldLeft(Vector.empty[List[Int]])(_ :+ _._2)
        val vr = vl.reverse.foldRight(ArraySeq.empty[String])(_.toString +: _)
        val al = vr.foldLeft(ArraySeq.empty[Int])(_ :+ _.length)
        val ar = al.foldRight(0)(_ + _)
        bh.consume(ar)
        map.toVector.filter(_._1.length % 8 == 0).map {
          case (str, intList) =>
            val a = intList.map(_ >> 1).to(ArraySeq) ++ str.toVector.map(_.toInt + 1)
            bh.consume(a)
            intList.iterator.map(i => s"[$i]").to(ArraySeq) ++ a.map(_.toChar.toString) :+ vec.mkString
        }.flatMap(_.iterator)
      }

    crap.samples().take(size << 2).foreach(bh.consume)
  }
}
