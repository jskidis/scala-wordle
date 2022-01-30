package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputValidatorSpec extends AnyFunSpec with Matchers  {
  describe("Input Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      InputValidator("a") mustBe false
      InputValidator("abcd") mustBe false
      InputValidator("abcdef") mustBe false
      InputValidator("abcdefghijklmnop") mustBe false
    }

    it("returns false if any of characters in the string are 'g', 'y', or 'b'") {
      InputValidator(List(greenChar, yellowChar, blackChar, '.', ',').mkString) mustBe false
      InputValidator(List('.', ',', greenChar, yellowChar, blackChar).mkString) mustBe false
      InputValidator(List(greenChar, '.', yellowChar, ',', blackChar).mkString) mustBe false
      InputValidator(List(greenChar, yellowChar, blackChar, greenChar, yellowChar).mkString) mustBe true
    }

    it("ignores case for matching chars") {
      InputValidator(List(greenChar.toUpper, yellowChar.toUpper, blackChar.toUpper, greenChar.toUpper, yellowChar.toUpper).mkString) mustBe true
      InputValidator(List(greenChar.toLower, yellowChar.toLower, blackChar.toLower, greenChar.toLower, yellowChar.toLower).mkString) mustBe true
      InputValidator(List(greenChar.toUpper, yellowChar.toLower, blackChar.toUpper, greenChar.toLower, yellowChar.toUpper).mkString) mustBe true
    }
  }
}
