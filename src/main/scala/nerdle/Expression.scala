package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Divide, Multiply, NerdleOperator, Subtract}

trait IntExpression {
  def value: Int
  def isValid: Boolean
}

case class IntExpr(operand: Int) extends IntExpression {
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
