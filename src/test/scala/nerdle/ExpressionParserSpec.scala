package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Subtract}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ExpressionParserSpec extends AnyFunSpec with Matchers {
  describe("Expression Parser") {
    it("handles a single operator expressions") {
      val addExpression = ExpressionParser.parseExpression("2+3")
      addExpression.isRight mustBe true
      addExpression.map { x => x mustBe OneOperatorExpression(2, Add, 3) }

      val subExpression = ExpressionParser.parseExpression("34-12")
      subExpression.isRight mustBe true
      subExpression.map { x => x mustBe OneOperatorExpression(34, Subtract, 12) }
    }

    it("handles a two operator expressions") {
      val addSubExpression = ExpressionParser.parseExpression("2+34-5")
      addSubExpression.isRight mustBe true
      addSubExpression.map { x => x mustBe TwoOperatorExpression(2, Add, 34, Subtract, 5)
      }
    }

    it("generates an error if expression contains anything other than digits or operators") {
      ExpressionParser.parseExpression("9^2*Three").isLeft mustBe true
    }

    it("generates an error if the expression is malformed") {
      ExpressionParser.parseExpression("*9+4").isLeft mustBe true
      ExpressionParser.parseExpression("9+*4").isLeft mustBe true
      ExpressionParser.parseExpression("9+4*").isLeft mustBe true
    }

    it("generates an error there are not 1 or 2 operators") {
      ExpressionParser.parseExpression("34").isLeft mustBe true
      ExpressionParser.parseExpression("3*4/12-1").isLeft mustBe true
    }

    it("generates an error if the expression is not a valid integer expression") {
      ExpressionParser.parseExpression("20/0").isLeft mustBe true
      ExpressionParser.parseExpression("13/7").isLeft mustBe true
    }

    it("generates an error if any operands have leading zeros") {
      ExpressionParser.parseExpression("03+4*2").isLeft mustBe true
      ExpressionParser.parseExpression("4+03").isLeft mustBe true
      ExpressionParser.parseExpression("4-3*-09").isLeft mustBe true
    }
  }
}
