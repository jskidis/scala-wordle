package com.skidis.wordle
package input

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GuessValidatorSpec extends AnyFunSpec with Matchers {
  describe("Word Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      GuessValidator.validateGuess("a") mustBe false
      GuessValidator.validateGuess("abcd") mustBe false
      GuessValidator.validateGuess("abcdef") mustBe false
      GuessValidator.validateGuess("abcdefghijklmnop") mustBe false
    }

    it("returns false if any of characters in the string are not letters") {
      GuessValidator.validateGuess("12345") mustBe false
      GuessValidator.validateGuess("ab3ef") mustBe false
      GuessValidator.validateGuess("1bcd5") mustBe false
      GuessValidator.validateGuess("abcde") mustBe true
    }

    it("returns false if guess is all color block characters") {
      GuessValidator.validateGuess("BBBBB") mustBe false
      GuessValidator.validateGuess("BGYBY") mustBe false
    }
  }
}
