package com.skidis.wordle
package nerdle

case class NerdleEquation(expr: IntExpression) extends XrdleWord {
  override def text: String = s"${expr.toString}=${expr.value}"
}

