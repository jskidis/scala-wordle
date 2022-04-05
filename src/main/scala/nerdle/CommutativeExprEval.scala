package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Divide, Multiply, NerdleOperator, Subtract}

trait CommutativeExprEval {
  def getEquivalentEqs(expr: IntExpression): Seq[IntExpression] = expr match {
    case oneOpExp: OneOperatorExpression => evalOneOpExpression(oneOpExp)
    case twoOpExp: TwoOperatorExpression => evalTwoOpExpression(twoOpExp)
  }

  private def evalOneOpExpression(expr: OneOperatorExpression): Seq[IntExpression] = {
    if (expr.operator != Add && expr.operator != Multiply) Nil
    else Seq(OneOperatorExpression(expr.operand2, expr.operator, expr.operand1))
  }

  private def evalTwoOpExpression(expr: TwoOperatorExpression): Seq[IntExpression] = expr.treeRep match {
    case OperatorExpr(left: OperatorExpr, op: NerdleOperator, right: IntValueExpr) =>
      evalLeftSidedExpression(left, op, right)
    case OperatorExpr(left: IntValueExpr, op: NerdleOperator, right: OperatorExpr) =>
      evalRightSidedExpression(left, op, right)
  }

  private def evalLeftSidedExpression(left: OperatorExpr, operator: NerdleOperator, right: IntValueExpr): Seq[IntExpression] = {
    def swapInside(): Option[IntExpression] = {
      if (left.operator == Subtract || left.operator == Divide) None
      else Option(OperatorExpr(OperatorExpr(left.expr2, left.operator, left.expr1), operator, right))
    }
    def swapOutside(): Option[IntExpression] = {
      if (operator == Subtract || operator == Divide) None
      else Option(OperatorExpr(right, operator, left))
    }
    def swapInsideAndOut(): Option[IntExpression] = {
      if (left.operator == Subtract || left.operator == Divide ||
        operator == Subtract || operator == Divide) None
      else Option(OperatorExpr(right, operator, OperatorExpr(left.expr2, left.operator, left.expr1)))
    }
    def swapInsideRightToOut(): Option[IntExpression] = {
      if (
        (left.operator == operator && (operator == Add || operator == Multiply)) ||
          ((left.operator == Add || left.operator == Subtract) && (operator == Add || operator == Subtract)) ||
          (left.operator == Divide && operator == Divide)
      )
        Option(OperatorExpr(OperatorExpr(left.expr1, operator, right), left.operator, left.expr2))
      else None
    }
    def swapInsideLeftToOut(): Option[IntExpression] = {
      if (
        (left.operator == operator && (operator == Add || operator == Multiply)) ||
          (left.operator == Add && operator == Subtract)
      )
        Option(OperatorExpr(OperatorExpr(left.expr2, operator, right), left.operator, left.expr1))
      else None
    }
    Seq(swapInside(), swapOutside(), swapInsideAndOut(),
      swapInsideRightToOut(), swapInsideLeftToOut()).flatten
  }

  private def evalRightSidedExpression(left: IntValueExpr, operator: NerdleOperator, right: OperatorExpr): Seq[IntExpression] = {
    def swapInside(): Option[IntExpression] = {
      if (right.operator != Multiply) None
      else Option(OperatorExpr(left, operator, OperatorExpr(right.expr2, right.operator, right.expr1)))
    }
    def swapOutside(): Option[IntExpression] = {
      if (operator != Add) None
      else Option(OperatorExpr(right, operator, left))
    }
    def swapInsideAndOut(): Option[IntExpression] = {
      if (right.operator != Multiply || operator != Add) None
      else Option(OperatorExpr(OperatorExpr(right.expr2, right.operator, right.expr1), operator, left))
    }
    Seq(swapInside(), swapOutside(), swapInsideAndOut()).flatten
  }
}

object CommutativeExprEval extends CommutativeExprEval

