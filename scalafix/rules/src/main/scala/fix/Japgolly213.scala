package fix

import scalafix.v1._
import scala.meta._

class Japgolly213 extends SemanticRule("Japgolly213") {

  private val function0 = SymbolMatcher.exact("scala/Function0#")
  private val function1 = SymbolMatcher.exact("scala/Function1#")
  private val function2 = SymbolMatcher.exact("scala/Function2#")
  private val function3 = SymbolMatcher.exact("scala/Function3#")
  private val function4 = SymbolMatcher.exact("scala/Function4#")

  private val iterableOnce = SymbolMatcher.exact(
    "scala/package.IterableOnce#",
    "scala/collection/IterableOnce#",
    "scala/collection/IterableOnceExtensionMethods#")

  private val iterator = Term.Name("iterator")

  private val iterableOnceMethodWhitelist = Set("toString", "iterator", "##", "hashCode", "equals")

  private def safely[A](fallback: => A)(a: => A): A =
    try
      a
    catch {
      case t: Throwable =>
        println("ERROR: " + t)
        fallback
    }


  override def fix(implicit doc: SemanticDocument): Patch = {
    // println("Tree.structureLabeled: " + doc.tree.structureLabeled)
    // println("="*160)

    doc.tree.collect {

      case Term.Select(subj, Term.Name(method)) if !iterableOnceMethodWhitelist.contains(method) =>
        // println(s"$subj.$method")
        // println("1) " + subj.structureLabeled)
        // println("2) " + subj.symbol)
        // println("3) " + subj.symbol.normalized)
        // println("4) " + subj.symbol.info)
        // println("5) " + subj.symbol.info.structure)
        // println("6) " + subj.symbol.info.map(_.signature))
        // println("7) " + subj.symbol.info.map(_.signature.structure))
        // println("7) " + subj.symbol.info.map(_.symbol))
        // println("8) " + subj.symbol.info.map(_.symbol.structure))
        // println("="*160)
        createPatch(subj).getOrElse(Patch.empty)

      case Enumerator.Generator(_, subj) =>
        createPatch(subj).getOrElse(Patch.empty)

    }.asPatch
  }

  private def createPatch(subj: Term)(implicit doc: SemanticDocument): Option[Patch] =
    safely(Option.empty[Patch]) {
      val info = subj.symbol.info

      // println(subj)
      // println("1) " + info)
      // println("2) " + info.map(_.signature))
      // println("="*160)

      info.map(_.signature).collect {
        case ValueSignature(TypeRef(_, iterableOnce(_), _))
           | ValueSignature(ByNameType(TypeRef(_, iterableOnce(_), _)))
           | ValueSignature(TypeRef(_, function0(_), List(TypeRef(_, iterableOnce(_), _))))
           | ValueSignature(TypeRef(_, function1(_), List(_, TypeRef(_, iterableOnce(_), _))))
           | ValueSignature(TypeRef(_, function2(_), List(_, _, TypeRef(_, iterableOnce(_), _))))
           | ValueSignature(TypeRef(_, function3(_), List(_, _, _, TypeRef(_, iterableOnce(_), _))))
           | ValueSignature(TypeRef(_, function4(_), List(_, _, _, _, TypeRef(_, iterableOnce(_), _))))
           | MethodSignature(_, _, TypeRef(_, iterableOnce(_), _)) =>
          Patch.addRight(subj, ".iterator")
        }
    }
}
