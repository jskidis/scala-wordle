package com.skidis.wordle
package input

import TestFixtures.TestHintProps

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class BasicResultAndGuessValidatorSpec extends AnyFunSpec with Matchers {

  object ValidatorFixture extends BasicResultAndGuessValidator with TestHintProps {
    override def guessWordLength: Int = 3
    override def validGuessChars: Set[Char] = "ABCXYZ".toSet
    override def invalidGuessCharError: String = "Invalid Chars"
    override def validHintChars: Set[Char] = "XYZ".toSet
  }
  
  describe("Input Validator") {
    it("returns an error if result is not guessWordLength chars in length") {
      ValidatorFixture.validateResult(Seq.fill(1)("X").mkString) must not be empty
      ValidatorFixture.validateResult(Seq.fill(6)("X").mkString) must not be empty
      ValidatorFixture.validateResult(Seq.fill(3)("X").mkString) mustBe empty
    }

    it("returns an error if any of characters in the result are not a validHintChar") {
      ValidatorFixture.validateResult("X Y") must not be empty
      ValidatorFixture.validateResult("123") must not be empty
      ValidatorFixture.validateResult("XYZ") mustBe empty
    }

    it("returns an error if guess is not guessWordLength chars in length") {
      ValidatorFixture.validateGuess(Seq.fill(1)("A").mkString) must not be empty
      ValidatorFixture.validateGuess(Seq.fill(6)("A").mkString) must not be empty
      ValidatorFixture.validateGuess(Seq.fill(3)("A").mkString) mustBe empty
    }

    it("returns an error if any of characters in the guess are not a validGuessChar") {
      ValidatorFixture.validateGuess("A C") must not be empty
      ValidatorFixture.validateGuess("123") must not be empty
      ValidatorFixture.validateGuess("ABC") mustBe empty
    }

    it("returns an error if all characters in the string are result characters") {
      // In this case A is a valid char but is part of
      ValidatorFixture.validateGuess("XYZ") must not be empty
      ValidatorFixture.validateGuess("XXX") must not be empty
      ValidatorFixture.validateGuess("AXZ") mustBe empty
    }
  }
}
