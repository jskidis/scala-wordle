package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Divide, Multiply, Subtract}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ExpressionSpec extends AnyFunSpec with Matchers {
  describe("Operator Expression") {
    it("calculates expression value correctly when both operands are integers") {
      OperatorExpr(IntValueExpr(4), Add, IntValueExpr(2)).value mustBe 6
      OperatorExpr(IntValueExpr(-4), Add, IntValueExpr(2)).value mustBe -2
      OperatorExpr(IntValueExpr(2), Subtract, IntValueExpr(4)).value mustBe -2
      OperatorExpr(IntValueExpr(2), Subtract, IntValueExpr(-4)).value mustBe 6
      OperatorExpr(IntValueExpr(2), Multiply, IntValueExpr(4)).value mustBe 8
      OperatorExpr(IntValueExpr(-2), Multiply, IntValueExpr(4)).value mustBe -8
      OperatorExpr(IntValueExpr(4), Divide, IntValueExpr(2)).value mustBe 2
      OperatorExpr(IntValueExpr(4), Divide, IntValueExpr(-2)).value mustBe -2
    }

    it("calculates expression value is one or both operands are operator expressions") {
      OperatorExpr(IntValueExpr(4), Add, OperatorExpr(IntValueExpr(6), Divide, IntValueExpr(3))).value mustBe 6
      OperatorExpr(OperatorExpr(IntValueExpr(6), Multiply, IntValueExpr(3)), Subtract, IntValueExpr(8)).value mustBe 10
      OperatorExpr(OperatorExpr(IntValueExpr(6), Multiply, IntValueExpr(3)), Add, OperatorExpr(IntValueExpr(6), Divide, IntValueExpr(3))).value mustBe 20
    }

    it("isValid returns false if result is divide by zero") {
      OperatorExpr(IntValueExpr(4), Divide, IntValueExpr(0)).isValid mustBe false
      OperatorExpr(IntValueExpr(4), Divide, IntValueExpr(1)).isValid mustBe true
      OperatorExpr(IntValueExpr(4), Add, IntValueExpr(1)).isValid mustBe true
    }

    it("isValid returns false if result is a non-integer value") {
      OperatorExpr(IntValueExpr(4), Divide, IntValueExpr(3)).isValid mustBe false
      OperatorExpr(IntValueExpr(4), Divide, IntValueExpr(1)).isValid mustBe true
      OperatorExpr(IntValueExpr(4), Add, IntValueExpr(1)).isValid mustBe true
    }

    it("generates appropriate string representations") {
      IntValueExpr(4).toString mustBe "4"
      OperatorExpr(IntValueExpr(4), Add, IntValueExpr(2)).toString mustBe "4+2"
      OperatorExpr(IntValueExpr(4), Add, OperatorExpr(IntValueExpr(6), Divide, IntValueExpr(3))).toString mustBe "4+6/3"
      OperatorExpr(OperatorExpr(IntValueExpr(6), Multiply, IntValueExpr(3)), Subtract, IntValueExpr(8)).toString mustBe "6*3-8"
      OperatorExpr(OperatorExpr(IntValueExpr(6), Multiply, IntValueExpr(3)), Add, OperatorExpr(IntValueExpr(6), Divide, IntValueExpr(3))).toString mustBe "6*3+6/3"
    }
  }
}
