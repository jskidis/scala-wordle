package com.skidis.wordle
package nerdle

import scala.annotation.tailrec

// I could have built a more generic version, but since nerdle expressions are limited to
// 1 or 2 operators, no parenthesis, and =-*/, get built a simple one based on those constraints
trait ExpressionParser {
  val invalidCharMsg = "Equations may only contain digits and operators"
  val malformedExprMsg = "The expression was not formed as expected"
  val tooManyOpsMsg = "The expression can only contain one or two operators"
  val notIntExprMsg = "The expression is not a valid integer expression"
  val leadingZeroMsg = "Expression can not have leading zeros"

  def parseExpression(text: String): Either[String, IntExpression] = {
    val tokens = tokenize(text)
    validateTokens(tokens) match {
      case Some(msg) => Left(msg)
      case None =>
        val expr = createExprFromTokens(tokens)
        if (!expr.isValid) Left(notIntExprMsg) else Right(expr)
    }
  }

  private def tokenize(text: String): List[String] = {
    @tailrec
    def recurse(chars: String, currToken: String, tokenAcc: List[String]): List[String] = {
      if (chars.isEmpty) {
        if (currToken.isEmpty) tokenAcc
        else tokenAcc :+ currToken
      }
      else if (operators.contains(chars.head)) {
        if(currToken.isEmpty) recurse(chars.tail, currToken = "", tokenAcc :+ chars.head.toString)
        else recurse(chars.tail, currToken = "", tokenAcc :+ currToken :+ chars.head.toString)
      }
      else recurse(chars.tail, currToken + chars.head, tokenAcc)
    }
    recurse(text, currToken = "", tokenAcc = Nil)
  }

  private def validateTokens(tokens: List[String]): Option[String] = {
    def isTokenF(indexes: List[Int], pred: String => Boolean): Boolean = {
      indexes.exists(idx => tokens.size > idx && pred(tokens(idx)))
    }

    if(!tokens.forall { t => t.forall { ch => validGuessChars.contains(ch) } }) Option(invalidCharMsg)
    else if (isTokenF(List(1, 3), { s: String => s.head.isDigit })) Option (malformedExprMsg)
    else if (isTokenF(List(0, 2, 4), { s: String => s.toIntOption.isEmpty})) Option(malformedExprMsg)
    else if (tokens.size != 3 && tokens.size != 5) Option(tooManyOpsMsg)
    else if (isTokenF(List(0, 2, 4), { s: String => s.length > 1 && s.head == '0'})) Option(leadingZeroMsg)
    else None
  }

  private def createExprFromTokens(tokens: List[String]): OperatorExpr = {
    def createOneOpExpr(val1: Int, op: Char, val2: Int) = {
      OperatorExpr(IntValueExpr(val1), op, IntValueExpr(val2))
    }
    def createLeftExpr(val1: Int, op1: Char, val2: Int, op2: Char, val3: Int) = {
      OperatorExpr(OperatorExpr(IntValueExpr(val1), op1, IntValueExpr(val2)), op2, IntValueExpr(val3))
    }
    def createRightExpr(val1: Int, op1: Char, val2: Int, op2: Char, val3: Int) = {
      OperatorExpr(IntValueExpr(val1), op1, OperatorExpr(IntValueExpr(val2), op2, IntValueExpr(val3)))
    }

    if (tokens.size == 3) {
      createOneOpExpr(tokens.head.toInt, tokens(1).head, tokens(2).toInt)
    }
    else if (tokens(1).head == '*' || tokens(1).head == '/' || tokens(3).head == '+' || tokens(3).head == '-') {
      createLeftExpr(tokens.head.toInt, tokens(1).head, tokens(2).toInt, tokens(3).head, tokens(4).toInt)
    }
    else {
      createRightExpr(tokens.head.toInt, tokens(1).head, tokens(2).toInt, tokens(3).head, tokens(4).toInt)
    }
  }
}

object ExpressionParser extends ExpressionParser
