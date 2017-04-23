
object Optics {

  type Optic[F[_, _], S, T, A, B] = F[A, B] => F[S, T]

  trait OpticP[Constraint[_[_, _]], S, T, A, B] {
    def apply[P[_, _]: Constraint]: Optic[P, S, T, A, B]
  }

  type Adapter[S, T, A, B] = OpticP[Profunctor, S, T, A, B]
  type Lens[S, T, A, B] = OpticP[Strong, S, T, A, B]
  type Prism[S, T, A, B] = OpticP[Choice, S, T, A, B]

  type Iso[S, A] = Adapter[S, S, A, A]

  // ===================================================================================================================
  import Instances._

  def lens[S, T, A, B](get: S => A, set: (S, B) => T): Lens[S, T, A, B] =
    new Lens[S, T, A, B] {
      override def apply[P[_, _]](implicit P: Strong[P]): P[A, B] => P[S, T] =
        pab => {
          val psaba: P[(S, A), (S, B)] = P.strongR[A, B, S](pab)
          val pst: P[S, T] = P.dimap(psaba)((s: S) => (s, get(s)), set.tupled)
          pst
        }
    }

  def prism[S, T, A, B](get: S => Either[T, A], set: B => T): Prism[S, T, A, B] =
    new Prism[S, T, A, B] {
      override def apply[P[_, _]](implicit P: Choice[P]): P[A, B] => P[S, T] =
        pab => {
          val ptata: P[Either[T, A], Either[T, B]] = P.choiceR[A, B, T](pab)
          val pst: P[S, T] = P.dimap(ptata)(get, _.fold(identity, set))
          pst
        }
    }

  def compose[F[_, _], S, T, A, B, X, Y](f: Optic[F, S, T, A, B], g: Optic[F, A, B, X, Y]): Optic[F, S, T, X, Y] =
    f compose g

  // ===================================================================================================================

  def testLens[S, T, A, B](lens: Lens[S, T, A, B])(s: S)(f: A => B): T = {
    val x: (A => B) => (S => T) = lens[Function1]
    x(f)(s)
  }



}
