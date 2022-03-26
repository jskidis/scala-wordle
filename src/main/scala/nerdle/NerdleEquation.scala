package com.skidis.wordle
package nerdle

case class NerdleEquation(expr: OperatorExpr) extends XrdleWord {
  override def text: String = s"${expr.toString}=${expr.value}"
}

