package com.skidis.wordle

import input.BasicResultValidator
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

  case class NerdleEquation(expr: OperatorExpr) extends XordlePhrase {
    override def phrase: String = s"${expr.toString}=${expr.value}"
    override def compare(that: XordlePhrase): Int = expr.value
  }

  trait NerdleResultValidator extends BasicResultValidator {
    override def resultLength: Int = 8
    override def validChars: List[Char] = validBlockChars
  }
  object NerdleResultValidator extends NerdleResultValidator

}
