package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Divide, Multiply, Subtract}

trait NerdleGuessableGenerator {
  def generatateEquations(): Set[NerdleWord] = {
    (generateOneOpEqs() ++ generateTwoOpEqs()).toSet
  }

  def generateOneOpEqs(): Seq[NerdleWord] = {
    def createExpression(operand1: Int, operator: Char, operand2: Int): OperatorExpr = {
      OperatorExpr(IntExpr(operand1), operator, IntExpr(operand2))
    }

    for {
      op1Digits: Int <- 1 to 4
      op2Digits: Int <- 1 to 5 - op1Digits
    } yield {
      for {
        operator <- operators
        operand1 <- rangeFromNumDigits(op1Digits)
        operand2 <- rangeFromNumDigits(op2Digits)
      } yield {
        val expr = createExpression(operand1, operator, operand2)
        generateEquation(expr, 6 - op1Digits - op2Digits)
      }
    }.flatten
  }.flatten

  def generateTwoOpEqs(): Seq[NerdleWord] = {
    def createExpression(operand1: Int, operator1: Char, operand2: Int, operator2: Char, operand3: Int): OperatorExpr = {
      if ((operator2 == Multiply || operator2 == Divide) && (operator1 == Add || operator1 == Subtract)) {
        OperatorExpr(IntExpr(operand1), operator1, OperatorExpr(IntExpr(operand2), operator2, IntExpr(operand3)))
      }
      else OperatorExpr(OperatorExpr(IntExpr(operand1), operator1, IntExpr(operand2)), operator2, IntExpr(operand3) )
    }

    val digitRanges = List((1, 1, 1, 2), (1, 1, 2, 1), (1, 2, 1, 1), (2, 1, 1, 1))
    for {
      (op1Digits, op2Digits, op3Digits, resultDigits) <- digitRanges
      operator1 <- operators
      operator2 <- operators
      operand1 <- rangeFromNumDigits(op1Digits)
      operand2 <- rangeFromNumDigits(op2Digits)
      operand3 <- rangeFromNumDigits(op3Digits)
    } yield {
      val expr = createExpression(operand1, operator1, operand2, operator2, operand3)
      generateEquation(expr, resultDigits)
    }
  }.flatten

  private def generateEquation(expr: OperatorExpr, resultDigits: Int): Option[NerdleWord] = {
    if (!expr.isValid) None
    else if (!rangeFromNumDigits(resultDigits).contains(expr.value)) None
    else Option(NerdleWord(expr))
  }

  private def rangeFromNumDigits(numDigits: Int): Seq[Int] = numDigits match {
    case n if n==1 => 0 to 9
    case n if n==2 => (10 to 99) ++ (-9 to -1)
    case n if n==3 => (100 to 999) ++ (-99 to -10)
    case _ => (1000 to 9999) ++ (-999 to -100)
  }
}

object NerdleGuessableGenerator extends NerdleGuessableGenerator
