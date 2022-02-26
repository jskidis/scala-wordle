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
  val validResultChars: Seq[Char] = NerdleHintProps.inputChars
  val validGuessChars: Seq[Char] = ('0' to '9') ++ operators + "="

  case class NerdleEquation(expr: OperatorExpr) extends XordlePhrase {
    override def phrase: String = s"${expr.toString}=${expr.value}"
    override def compare(that: XordlePhrase): Int = toString.compareTo(that.toString)
  }

  object NerdleInPosHint extends InPosHint {
    override def inputChar: Char = 'G'
    override def colorBlock: String = "\uD83D\uDFE9"
  }

  object NerdleInWordHint extends InWordHint {
    override def inputChar: Char = 'R'
    override def colorBlock: String = "\uD83D\uDFEA"
  }

  object NerdleMissHint extends MissHint {
    override def inputChar: Char = 'B'
    override def colorBlock: String = "\u2B1B"
  }

  trait NerdleHintProps extends HintProps {
    override def inPosHint: InPosHint = NerdleInPosHint
    override def inWordHint: InWordHint = NerdleInWordHint
    override def missHint: MissHint = NerdleMissHint
  }
  object NerdleHintProps extends NerdleHintProps

}
