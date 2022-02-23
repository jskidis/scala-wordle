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

  case class NerdleWord(expr: OperatorExpr) extends WordleWord {
    override def string: String = s"${expr.toString}=${expr.value}"
    override def compare(that: WordleWord): Int = expr.value
  }
}
