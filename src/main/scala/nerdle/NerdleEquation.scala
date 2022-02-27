package com.skidis.wordle
package nerdle

case class NerdleEquation(expr: OperatorExpr) extends XordlePhrase {
  override def phrase: String = s"${expr.toString}=${expr.value}"
  override def compare(that: XordlePhrase): Int = toString.compareTo(that.toString)
}
