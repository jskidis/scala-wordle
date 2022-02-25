package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.Add

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class EquationParserSpec extends AnyFunSpec with Matchers {
  // Equation parser delegates all of the parsing of the expression (left hand side) to the ExpressionParser
  // so these tests are based on valid expressions on the left

  describe("Equation Parser") {
    it("return the equation if it valid") {
      val eq = EquationParser.parseEquation("2+4=6")
      eq.isRight mustBe true
      eq.map { _ mustBe NerdleEquation(OperatorExpr(IntValueExpr(2), Add, IntValueExpr(4))) }
    }

    it("returns an error if there is no equals sign or are multiple equals") {
      EquationParser.parseEquation("2+2").isLeft mustBe true
      EquationParser.parseEquation("2+2=4=3+1").isLeft mustBe true
    }

    it("returns an error is right side value is not a number or has any leading zeros") {
      EquationParser.parseEquation("2ish").isLeft mustBe true
      EquationParser.parseEquation("0123").isLeft mustBe true
    }

    it("returns an error is the left hand expression couldn't be parsed") {
      EquationParser.parseEquation("2^4k=8").isLeft mustBe true
      EquationParser.parseEquation("842*+=34").isLeft mustBe true
    }

    it("returns an error is the left side doesn't equal the right side") {
      EquationParser.parseEquation("2+4=8").isLeft mustBe true
      EquationParser.parseEquation("8/2=16").isLeft mustBe true
    }
  }
}
