package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Divide, Multiply, NerdleOperator, Subtract}

trait IntExpression {
  def value: Int
  def isValid: Boolean
}

case class IntValueExpr(operand: Int) extends IntExpression {
  def value: Int = operand
  def isValid: Boolean = true
  override def toString: String = operand.toString
}

case class OperatorExpr(expr1: IntExpression, operator: NerdleOperator, expr2: IntExpression) extends IntExpression {
  def value: Int = operator match {
    case op if op == Add => expr1.value + expr2.value
    case op if op == Subtract => expr1.value - expr2.value
    case op if op == Multiply => expr1.value * expr2.value
    case op if op == Divide => expr1.value / expr2.value
  }

  override def isValid: Boolean = {
    expr1.isValid && expr2.isValid &&
      (operator != Divide || (expr2.value != 0 && expr1.value % expr2.value == 0))
  }

  override def toString: String = s"$expr1$operator$expr2"
}

case class OneOperatorExpression(operand1: Int, operator: NerdleOperator, operand2: Int) extends IntExpression {
  private lazy val expression = OperatorExpr(IntValueExpr(operand1), operator, IntValueExpr(operand2))

  override def value: Int = expression.value
  override def isValid: Boolean = expression.isValid
  override def toString: String = expression.toString
}

case class TwoOperatorExpression(operand1: Int, operator1: NerdleOperator, operand2: Int,
  operator2: NerdleOperator, operand3: Int) extends IntExpression {
  private lazy val expression =
    if ((operator2 == Multiply || operator2 == Divide) && (operator1 == Add || operator1 == Subtract)) {
      OperatorExpr(IntValueExpr(operand1), operator1,
        OperatorExpr(IntValueExpr(operand2), operator2, IntValueExpr(operand3)))
    }
    else OperatorExpr(OperatorExpr(IntValueExpr(operand1), operator1,
      IntValueExpr(operand2)), operator2, IntValueExpr(operand3) )

  override def value: Int = expression.value
  override def isValid: Boolean = expression.isValid
  override def toString: String = expression.toString
}
