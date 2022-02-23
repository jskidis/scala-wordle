package com.skidis.wordle

package object nerdle {
  val operators = List('+', '-', '*', '/')

  case class NerdleWord(expr: OperatorExpr) extends WordleWord {
    override def string: String = s"${expr.toString}=${expr.value}"
    override def compare(that: WordleWord): Int = expr.value
  }
}
