import org.scalatest.{Stepwise, Sequential, ParallelTestExecution, Suites}

class S extends Suites(
  new X, new Y
)

