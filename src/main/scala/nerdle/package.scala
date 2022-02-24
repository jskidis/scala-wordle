package com.skidis.wordle

import nerdle.NerdleOperator.{Add, Divide, Multiply, Subtract}

package object nerdle {
  object NerdleOperator extends Enumeration {
    type NerdleOperator = Char
    val Add = '+'
    val Subtract = '-'
    val Multiply = '*'
    val Divide = '/'
  }
  val operators = List(Add, Subtract, Multiply, Divide)

  val inputLength: Int = 8
  val validResultChars: Seq[Char] = validBlockChars
  val validGuessChars: Seq[Char] = ('0' to '9') ++ operators + "="

  case class NerdleEquation(expr: OperatorExpr) extends XordlePhrase {
    override def phrase: String = s"${expr.toString}=${expr.value}"
    override def compare(that: XordlePhrase): Int = expr.value
  }


}
