package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class StandardResultValidatorSpec extends AnyFunSpec with Matchers  {
  describe("Input Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      StandardResultValidator.validate("a") mustBe false
      StandardResultValidator.validate("abcd") mustBe false
      StandardResultValidator.validate("abcdef") mustBe false
      StandardResultValidator.validate("abcdefghijklmnop") mustBe false
    }

    it("returns false if any of characters in the string are 'g', 'y', or 'b'") {
      StandardResultValidator.validate(List(greenChar, yellowChar, blackChar, '.', ',').mkString) mustBe false
      StandardResultValidator.validate(List('.', ',', greenChar, yellowChar, blackChar).mkString) mustBe false
      StandardResultValidator.validate(List(greenChar, '.', yellowChar, ',', blackChar).mkString) mustBe false
      StandardResultValidator.validate(List(greenChar, yellowChar, blackChar, greenChar, yellowChar).mkString) mustBe true
    }

    it("ignores case for matching chars") {
      StandardResultValidator.validate(List(greenChar.toUpper, yellowChar.toUpper, blackChar.toUpper, greenChar.toUpper, yellowChar.toUpper).mkString) mustBe true
      StandardResultValidator.validate(List(greenChar.toLower, yellowChar.toLower, blackChar.toLower, greenChar.toLower, yellowChar.toLower).mkString) mustBe true
      StandardResultValidator.validate(List(greenChar.toUpper, yellowChar.toLower, blackChar.toUpper, greenChar.toLower, yellowChar.toUpper).mkString) mustBe true
    }
  }
}

class StandardWordValidatorSpec extends AnyFunSpec with Matchers  {
  describe("Word Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      StandardGuessValidator.validate("a") mustBe false
      StandardGuessValidator.validate("abcd") mustBe false
      StandardGuessValidator.validate("abcdef") mustBe false
      StandardGuessValidator.validate("abcdefghijklmnop") mustBe false
    }

    it("returns false if any of characters in the string are not letters") {
      StandardGuessValidator.validate("12345") mustBe false
      StandardGuessValidator.validate("ab3ef") mustBe false
      StandardGuessValidator.validate("1bcd5") mustBe false
      StandardGuessValidator.validate("abcde") mustBe true
    }

    it("returns false if guess is all color block characters") {
      StandardGuessValidator.validate("BBBBB") mustBe false
      StandardGuessValidator.validate("BGYBY") mustBe false
    }
  }
}
