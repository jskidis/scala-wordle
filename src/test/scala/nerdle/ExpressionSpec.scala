package com.skidis.wordle
package nerdle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ExpressionSpec extends AnyFunSpec with Matchers {
  describe("Operator Expression") {
    it("calculates expression value correctly when both operands are integers") {
      OperatorExpr(IntExpr(4), '+', IntExpr(2)).value mustBe 6
      OperatorExpr(IntExpr(-4), '+', IntExpr(2)).value mustBe -2
      OperatorExpr(IntExpr(2), '-', IntExpr(4)).value mustBe -2
      OperatorExpr(IntExpr(2), '-', IntExpr(-4)).value mustBe 6
      OperatorExpr(IntExpr(2), '*', IntExpr(4)).value mustBe 8
      OperatorExpr(IntExpr(-2), '*', IntExpr(4)).value mustBe -8
      OperatorExpr(IntExpr(4), '/', IntExpr(2)).value mustBe 2
      OperatorExpr(IntExpr(4), '/', IntExpr(-2)).value mustBe -2
    }

    it("calculates expression value is one or both operands are operator expressions") {
      OperatorExpr(IntExpr(4), '+', OperatorExpr(IntExpr(6), '/', IntExpr(3))).value mustBe 6
      OperatorExpr(OperatorExpr(IntExpr(6), '*', IntExpr(3)), '-', IntExpr(8)).value mustBe 10
      OperatorExpr(OperatorExpr(IntExpr(6), '*', IntExpr(3)), '+', OperatorExpr(IntExpr(6), '/', IntExpr(3))).value mustBe 20
    }

    it("isValid returns false if result is divide by zero") {
      OperatorExpr(IntExpr(4), '/', IntExpr(0)).isValid mustBe false
      OperatorExpr(IntExpr(4), '/', IntExpr(1)).isValid mustBe true
      OperatorExpr(IntExpr(4), '+', IntExpr(1)).isValid mustBe true
    }

    it("isValid returns false if result is a non-integer value") {
      OperatorExpr(IntExpr(4), '/', IntExpr(3)).isValid mustBe false
      OperatorExpr(IntExpr(4), '/', IntExpr(1)).isValid mustBe true
      OperatorExpr(IntExpr(4), '+', IntExpr(1)).isValid mustBe true
    }

    it("generates appropriate string representations") {
      IntExpr(4).toString mustBe "4"
      OperatorExpr(IntExpr(4), '+', IntExpr(2)).toString mustBe "4+2"
      OperatorExpr(IntExpr(4), '+', OperatorExpr(IntExpr(6), '/', IntExpr(3))).toString mustBe "4+6/3"
      OperatorExpr(OperatorExpr(IntExpr(6), '*', IntExpr(3)), '-', IntExpr(8)).toString mustBe "6*3-8"
      OperatorExpr(OperatorExpr(IntExpr(6), '*', IntExpr(3)), '+', OperatorExpr(IntExpr(6), '/', IntExpr(3))).toString mustBe "6*3+6/3"
    }
  }
}
