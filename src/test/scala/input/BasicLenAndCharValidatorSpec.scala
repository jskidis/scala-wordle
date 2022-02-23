package com.skidis.wordle
package input

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class BasicLenAndCharValidatorSpec extends AnyFunSpec with Matchers  {

  val len: Int = 3
  val chars: Seq[Char] = "ABCXYZ".toList
  val resultChars: Seq[Char] = "XYZ"

  describe("Input Validator") {
    it("returns an error if input is not ${resultLength} chars in length") {
      BasicLenAndCharValidator.validateInput(List.fill(1)("A").mkString, len, chars) must not be empty
      BasicLenAndCharValidator.validateInput(List.fill(6)("A").mkString, len, chars) must not be empty
      BasicLenAndCharValidator.validateInput(List.fill(3)("A").mkString, len, chars) mustBe empty
    }

    it("returns an error if any of characters in the string are not a valid char ('A', 'B', 'C')") {
      BasicLenAndCharValidator.validateInput("A C", len, chars) must not be empty
      BasicLenAndCharValidator.validateInput("123", len, chars) must not be empty
      BasicLenAndCharValidator.validateInput("ABC", len, chars) mustBe empty
    }

    it("returns an error if all characters in the string are result characters") {
      // In this case A is a valid char but is part of
      BasicLenAndCharValidator.validateGuess("XYZ", len, chars, resultChars) must not be empty
      BasicLenAndCharValidator.validateGuess("XXX", len, chars, resultChars) must not be empty
      BasicLenAndCharValidator.validateGuess("AXZ", len, chars, resultChars) mustBe empty
    }
  }
}


