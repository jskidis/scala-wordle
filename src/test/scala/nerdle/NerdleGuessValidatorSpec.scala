package com.skidis.wordle
package nerdle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class NerdleGuessValidatorSpec extends AnyFunSpec with Matchers {
  // This validator mostly delegates to the EquationParser and BasicLengthAndCharsValidator
  // So this tests just make sure things are wired up as expected

  describe("NerdleGuessValidator") {
    it("does not return an error if the equation could be parsed") {
      NerdleGuessValidator.validateGuess("10+20=30") mustBe empty
      NerdleGuessValidator.validateGuess("2*3+6=12") mustBe empty
    }

    it("returns an error if the length is not 8 or has characters that or not digits or operators") {
      NerdleGuessValidator.validateGuess("1+2=3") must not be empty
      NerdleGuessValidator.validateGuess("sadjfkl;asd") must not be empty
    }

    it("returns an error if the equation is not valid") {
      NerdleGuessValidator.validateGuess("1+2=30") must not be empty
      NerdleGuessValidator.validateGuess("8/5=1") must not be empty
    }
  }
}
