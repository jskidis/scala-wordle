package com.skidis.wordle
package nerdle

import hintgen.WordHintsGenerator

trait NerdleWordHintsGenerator extends WordHintsGenerator {

  override def generateWordHints(answer: String, word: String): WordHints = {
    val areEquiv = (EquationParser.parseEquation(answer), EquationParser.parseEquation(word)) match {
      case (Right(a), Right(w)) => areAnswerAndWordCommutative(a, w)
      case _ => false
    }
    if (!areEquiv) super.generateWordHints(answer, word)
    else Seq.fill(answer.length)(inPosHint)
  }

  def areAnswerAndWordCommutative(answer: NerdleEquation, word: NerdleEquation): Boolean = {
    CommutativeExprEval.getEquivalentEqs(answer.expr).exists{ equiv =>
      equiv.toString == word.expr.toString
    }
  }
}