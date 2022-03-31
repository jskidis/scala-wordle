package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{NerdleOperator, operators}

import scala.annotation.tailrec

// I could have built a more generic version, but since nerdle expressions are limited to
// 1 or 2 operators, no parenthesis, and =-*/, get built a simple one based on those constraints
trait ExpressionParser {
  trait ExpressionToken
  case class OperatorToken(operator: NerdleOperator) extends ExpressionToken
  case class NumberToken(value: Int) extends ExpressionToken
  case class InvalidToken(reason: String) extends ExpressionToken

  val invalidExprMsg = "Equations may only contain digits and operators"
  val malformedExprMsg = "The expression was not formed as expected"
  val tooManyOpsMsg = "The expression can only contain one or two operators"
  val notIntExprMsg = "The expression is not a valid integer expression"
  val leadingZeroMsg = "Expression can not have leading zeros"

  def parseExpression(text: String): Either[String, IntExpression] = {
    def getValueTokens(tokens: Vector[ExpressionToken]): Vector[NumberToken] = tokens.flatMap {
      case nt: NumberToken => Option(nt)
      case _ => None
    }
    def getOperatorTokens(tokens: Vector[ExpressionToken]): Vector[OperatorToken] = tokens.flatMap {
      case ot: OperatorToken => Option(ot)
      case _ => None
    }

    val tokens = tokenize(text)
    validateTokens(tokens) match {
      case Some(msg) => Left(msg)
      case None =>
        val expr = createExprFromTokens(getValueTokens(tokens), getOperatorTokens(tokens))
        if (!expr.isValid) Left(notIntExprMsg) else Right(expr)
    }
  }

  private def tokenize(text: String): Vector[ExpressionToken] = {
    @tailrec
    def recurse(chars: String, currToken: String, tokenAcc: Vector[ExpressionToken]): Vector[ExpressionToken] = {
      if (chars.isEmpty) {
        if (currToken.isEmpty) tokenAcc
        else tokenAcc :+ generateNumberToken(currToken)
      }
      else if (operators.contains(chars.head)) {
        if(currToken.isEmpty) recurse(chars.tail, currToken = "", tokenAcc :+ OperatorToken(chars.head))
        else recurse(chars.tail, currToken = "", tokenAcc :+ generateNumberToken(currToken) :+ OperatorToken(chars.head))
      }
      else recurse(chars.tail, currToken + chars.head, tokenAcc)
    }

    def generateNumberToken(token: String): ExpressionToken = {
      token.toIntOption match {
        case None => InvalidToken(invalidExprMsg)
        case Some(value) =>
          if(token.length > 1 && token.head == '0') InvalidToken(leadingZeroMsg)
          else NumberToken(value)
      }
    }

    recurse(text, currToken = "", tokenAcc = Vector())
  }

  private def validateTokens(tokens: Vector[ExpressionToken]): Option[String] = {
    def tokensCorrectByPosition(): Boolean = tokens.zipWithIndex.forall {
      case (_: NumberToken, idx) if idx % 2 == 0 => true
      case (_:OperatorToken, idx) if idx % 2 == 1 => true
      case _ => false
    }

    val badTokenReasons = tokens.flatMap {
      case it: InvalidToken => Option(it.reason)
      case _ => None
    }

    if (badTokenReasons.nonEmpty) Option(badTokenReasons.mkString("\n"))
    else if (!tokensCorrectByPosition()) Option (malformedExprMsg)
    else if (tokens.size != 3 && tokens.size != 5) Option(tooManyOpsMsg)
    else None
  }

  private def createExprFromTokens(valTokens: Seq[NumberToken], opTokens: Seq[OperatorToken]): IntExpression = {
    if (opTokens.size == 1) {
      OneOperatorExpression(valTokens(0).value, opTokens(0).operator, valTokens(1).value)
    }
    else {
      TwoOperatorExpression(valTokens(0).value, opTokens(0).operator, valTokens(1).value,
        opTokens(1).operator, valTokens(2).value)
    }
  }
}

object ExpressionParser extends ExpressionParser
