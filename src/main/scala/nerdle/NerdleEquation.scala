package com.skidis.wordle
package nerdle

case class NerdleEquation(expr: OperatorExpr) extends XordlePhrase {
  override def phrase: String = s"${expr.toString}=${expr.value}"
}

case class NerdleEquationWithFreq(expr: OperatorExpr, frequency: Double) extends XordlePhaseFreq {
  override def phrase: String = s"${expr.toString}=${expr.value}"
}

