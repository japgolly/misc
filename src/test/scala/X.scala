import org.scalatest.FunSuite

class X extends FunSuite {
  test("Thread X") {
    for (i <- 1 to 5) {
      println("X: " + i)
      Thread.sleep(500)
    }
  }
}
class Y extends FunSuite {
  test("Thread Y") {
    for (i <- 1 to 5) {
      println("Y: " + i)
      Thread.sleep(500)
    }
  }
}
