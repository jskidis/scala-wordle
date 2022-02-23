package com.skidis.wordle
package nerdle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class NerdleGuessableGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Nerdle Guessable Generator, One Operator Equations") {
    val equations = NerdleGuessableGenerator.generateOneOpEqs()

    it("all equations must translate into 8 characters") {
      val non8CharEqs = equations.filter(_.string.length != 8)
      non8CharEqs mustBe empty
    }

    it("all expressions should be valid") {
      val nonValidEqs = equations.filter(!_.expr.isValid)
      nonValidEqs mustBe empty
    }

    // This test is kind of redundant since equations just encapsulate expressions which already have this check
    it("should have no divide by zero equations") {
      val nonValidEqs = equations.filter {
        case eq if eq.expr.operator == '/' => eq.expr.expr2.value == 0
        case _ => false
      }
      nonValidEqs mustBe empty
    }

    // This test is kind of redundant since equations just encapsulate expressions which already have this check
    it("should not have any non-integer values") {
      val nonValidEqs = equations.filter {
        case eq if eq.expr.operator == '/' => eq.expr.expr1.value % eq.expr.expr2.value != 0
        case _ => false
      }
      nonValidEqs mustBe empty
    }
  }

  describe("Nerdle Guessable Generator, Two Operator Equations") {
    val equations = NerdleGuessableGenerator.generateTwoOpEqs()

    it("all should equations translate into 8 characters") {
      val non8CharEqs = equations.filter(eq => eq.string.length != 8)
      non8CharEqs mustBe empty
    }

    it("all expressions should be valid") {
      val nonValidEqs = equations.filter(!_.expr.isValid)
      nonValidEqs mustBe empty
    }
  }
}