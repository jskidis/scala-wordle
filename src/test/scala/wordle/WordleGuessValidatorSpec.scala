package com.skidis.wordle
package wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordleGuessValidatorSpec extends AnyFunSpec with Matchers {
  describe("Wordle Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      WordleGuessValidator.validateGuess("a") must not be empty
      WordleGuessValidator.validateGuess("abcd") must not be empty
      WordleGuessValidator.validateGuess("abcdef") must not be empty
      WordleGuessValidator.validateGuess("abcde") mustBe empty
    }

    it("returns false if any of characters in the string are not letters") {
      WordleGuessValidator.validateGuess("12345") must not be empty
      WordleGuessValidator.validateGuess("ab3ef") must not be empty
      WordleGuessValidator.validateGuess("1bcd5") must not be empty
      WordleGuessValidator.validateGuess("ABcDE") mustBe empty
    }

    it("returns false if guess is all color block characters") {
      WordleGuessValidator.validateGuess("BBBBB") must not be empty
      WordleGuessValidator.validateGuess("BGYBY") must not be empty
    }
  }
}
