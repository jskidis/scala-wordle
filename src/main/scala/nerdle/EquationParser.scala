package com.skidis.wordle
package nerdle

trait EquationParser extends ExpressionParser {
  val noEqualsMsg = "Equation must contain an equals sign and only one"
  val onlyNumAfterEqualsMsg = "Only digits allowed after equals sign"
  val equationUnequalMsg = "Equation is not equal on each side"

  def parseEquation(text: String): Either[String, NerdleEquation] = {
    val splits = text.split("=")
    if (splits.size != 2) Left(noEqualsMsg)
    else {
      val (exprText, valueText) = (splits(0), splits(1))
      (valueText.toIntOption, parseExpression(exprText)) match {
        case (None, _) => Left(onlyNumAfterEqualsMsg)
        case (Some(_), _) if valueText.length > 1 && valueText.head == '0' => Left(leadingZeroMsg)
        case (Some(_), Left(error)) => Left(error)
        case (Some(value), Right(expression)) if value != expression.value => Left(equationUnequalMsg)
        case (Some(_), Right(expression)) => Right(NerdleEquation(expression))
      }
    }
  }
}

object EquationParser extends EquationParser
