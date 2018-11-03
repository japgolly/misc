package ssrdemo

import org.graalvm.polyglot.{Context, Engine, Source}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

class Graal2 {

  val engine = Engine.newBuilder().build()

  def readFile(path: String) =
    scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(path), "UTF-8").mkString

  def eval(s: String)(implicit ctx: Context) = ctx.eval("js", s)

  def loadFile(filename: String)(implicit ctx: Context): Unit = {
    println(s"Loading $filename ... ")
    eval(readFile(filename))
  }

  def loadSource(filename: String): Source =
    Source.create("js", readFile(filename))

  val S1 = loadSource("react/node_modules/react/umd/react.production.min.js")
  val S2 = loadSource("react/node_modules/react-dom/umd/react-dom-server.browser.production.min.js")
  val S3 = loadSource("webapp-client-public-opt.js")

  implicit val ec = ExecutionContext.global

  def test(warmup: Boolean): Future[Unit] = Future {
    println("=" * 80)

    implicit val ctx = Context.newBuilder("js").engine(engine).build()
    ctx.enter()

    ctx.eval(S1)
    ctx.eval(S2)
    eval("window = {console: console, location: {href: 'https://shipreq.com'}, navigator: {userAgent: ''}}")
    ctx.eval(S3)

    val acomp = Source.create("js", "A.comp2()")

    val (warm,runs) = (10000, 100)
    if (warmup) {
      println(s"\nWarming up ($warm) ...")
      Stats.times(warm, ctx.eval(acomp))
    }
    println(s"Benchmarking ($runs) ...")
    val times = Stats.times(runs, ctx.eval(acomp))
    printf("p50 = %,3d ms\n", Stats.percentile(50)(times))
    printf("p90 = %,3d ms\n", Stats.percentile(90)(times))
    printf("p95 = %,3d ms\n", Stats.percentile(95)(times))
    printf("p98 = %,3d ms\n", Stats.percentile(98)(times))
    printf("p99 = %,3d ms\n", Stats.percentile(99)(times))

    // if (!warmup) println(s"\n${ctx.eval(acomp).asString()}\n")

    ctx.leave()
    ctx.close()
  }

  Await.result(test(true), 2 minutes)
  Await.result(test(false), 2 minutes)
  println("=" * 80)
}
