package com.skidis.wordle
package nerdle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class NerdleGuessableGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Nerdle Guessable Generator, One Operator Equations") {
    val equations = NerdleGuessableGenerator.generateOneOpEqs()

    it("all equations must translate into 8 characters") {
      val non8CharEqs = equations.filter(_.text.length != 8)
      non8CharEqs mustBe empty
    }

    it("all expressions should be valid") {
      val nonValidEqs = equations.filter(!_.expr.isValid)
      nonValidEqs mustBe empty
    }
  }

  describe("Nerdle Guessable Generator, Two Operator Equations") {
    val equations = NerdleGuessableGenerator.generateTwoOpEqs()

    it("all should equations translate into 8 characters") {
      val non8CharEqs = equations.filter(eq => eq.text.length != 8)
      non8CharEqs mustBe empty
    }

    it("all expressions should be valid") {
      val nonValidEqs = equations.filter(!_.expr.isValid)
      nonValidEqs mustBe empty
    }
  }

  describe("Nerdle Guessable Generator, MiniNerdle Equations") {
    val equations = NerdleGuessableGenerator.generateMiniEqs()

    it("all should equations translate into 6 characters") {
      val non8CharEqs = equations.filter(eq => eq.text.length != 6)
      non8CharEqs mustBe empty
    }

    it("all expressions should be valid") {
      val nonValidEqs = equations.filter(!_.expr.isValid)
      nonValidEqs mustBe empty
    }
  }
}