package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.operators

trait NerdleGuessableGenerator extends NerdleGuessProps {
  def generate8CharEquations(): Set[NerdleEquation] = {
    (generateOneOpEqs() ++ generateTwoOpEqs()).toSet
  }

  def generate6CharEquations(): Set[NerdleEquation] = {
    generateMiniEqs().toSet
  }

  def generateMiniEqs(): Seq[NerdleEquation] = {
    for {
      op1Digits: Int <- 1 to 2
      op2Digits: Int <- 1 to 3 - op1Digits
    } yield {
      for {
        operator <- operators
        operand1 <- rangeFromNumDigits(op1Digits)
        operand2 <- rangeFromNumDigits(op2Digits)
      } yield {
        val expr = OneOperatorExpression(operand1, operator, operand2)
        generateEquation(expr, 4 - op1Digits - op2Digits)
      }
    }.flatten
  }.flatten

  def generateOneOpEqs(): Seq[NerdleEquation] = {
    for {
      op1Digits: Int <- 1 to 4
      op2Digits: Int <- 1 to 5 - op1Digits
    } yield {
      for {
        operator <- operators
        operand1 <- rangeFromNumDigits(op1Digits)
        operand2 <- rangeFromNumDigits(op2Digits)
      } yield {
        val expr = OneOperatorExpression(operand1, operator, operand2)
        generateEquation(expr, 6 - op1Digits - op2Digits)
      }
    }.flatten
  }.flatten

  def generateTwoOpEqs(): Seq[NerdleEquation] = {
    val digitRanges = Seq((1, 1, 1, 2), (1, 1, 2, 1), (1, 2, 1, 1), (2, 1, 1, 1))
    for {
      (op1Digits, op2Digits, op3Digits, resultDigits) <- digitRanges
      operator1 <- operators
      operator2 <- operators
      operand1 <- rangeFromNumDigits(op1Digits)
      operand2 <- rangeFromNumDigits(op2Digits)
      operand3 <- rangeFromNumDigits(op3Digits)
    } yield {
      val expr = TwoOperatorExpression(operand1, operator1, operand2, operator2, operand3)
      generateEquation(expr, resultDigits)
    }
  }.flatten

  private def generateEquation(expr: IntExpression, resultDigits: Int): Option[NerdleEquation] = {
    if (!expr.isValid) None
    else if (!rangeFromNumDigits(resultDigits, allowZero = true).contains(expr.value)) None
    else Option(NerdleEquation(expr))
  }

  private def rangeFromNumDigits(numDigits: Int, allowZero: Boolean = false): Seq[Int] = numDigits match {
    case n if n==1 => if(allowZero) 0 to 9 else 1 to 9
    case n if n==2 => 10 to 99 // ++ (-9 to -1)
    case n if n==3 => 100 to 999 // ++ (-99 to -10)
    case _ => 1000 to 9999 // ++ (-999 to -100)
  }
}

object NerdleGuessableGenerator extends NerdleGuessableGenerator
