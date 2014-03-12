import scalaz.{Coyoneda, ~>, Functor, Free}
import Free.FreeC

object ScalazExt {

  type CoyonedaF[F[_]] = ({type A[α] = Coyoneda[F, α]})

  /** A free monad over a free functor of `S`. */
 def liftFC[S[_], A](s: S[A]): FreeC[S, A] = {
    val c: Coyoneda[S,A] = Coyoneda(s)
    Free.liftFU(c)
  }

  def FG_to_CFG[F[_], G[_] : Functor, A](t: F ~> G): (CoyonedaF[F]#A ~> G) = {
    type CF[A] = Coyoneda[F, A]
    type CG[A] = Coyoneda[G, A]
    val m: (CF ~> CG) = FG_to_CFCG(t)
    val n: (CG ~> G) = CF_to_F
    val o: (CF ~> G) = n compose m
    o
  }

  def FG_to_CFCG[F[_], G[_]](f: F ~> G): (CoyonedaF[F]#A ~> CoyonedaF[G]#A) =
    new (CoyonedaF[F]#A ~> CoyonedaF[G]#A) {
      def apply[X](c: CoyonedaF[F]#A[X]): CoyonedaF[G]#A[X] = c.trans(f)
    }

  def CF_to_F[F[_] : Functor]: (CoyonedaF[F]#A ~> F) =
    new (CoyonedaF[F]#A ~> F) {
      def apply[X](c: CoyonedaF[F]#A[X]): F[X] = c.run
    }
}
