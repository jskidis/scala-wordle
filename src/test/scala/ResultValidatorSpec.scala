package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ResultValidatorSpec extends AnyFunSpec with Matchers  {
  describe("Input Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      ResultValidator.validateResult("a") mustBe false
      ResultValidator.validateResult("abcd") mustBe false
      ResultValidator.validateResult("abcdef") mustBe false
      ResultValidator.validateResult("abcdefghijklmnop") mustBe false
    }

    it("returns false if any of characters in the string are 'g', 'y', or 'b'") {
      ResultValidator.validateResult(List(greenChar, yellowChar, blackChar, '.', ',').mkString) mustBe false
      ResultValidator.validateResult(List('.', ',', greenChar, yellowChar, blackChar).mkString) mustBe false
      ResultValidator.validateResult(List(greenChar, '.', yellowChar, ',', blackChar).mkString) mustBe false
      ResultValidator.validateResult(List(greenChar, yellowChar, blackChar, greenChar, yellowChar).mkString) mustBe true
    }

    it("ignores case for matching chars") {
      ResultValidator.validateResult(List(greenChar.toUpper, yellowChar.toUpper, blackChar.toUpper, greenChar.toUpper, yellowChar.toUpper).mkString) mustBe true
      ResultValidator.validateResult(List(greenChar.toLower, yellowChar.toLower, blackChar.toLower, greenChar.toLower, yellowChar.toLower).mkString) mustBe true
      ResultValidator.validateResult(List(greenChar.toUpper, yellowChar.toLower, blackChar.toUpper, greenChar.toLower, yellowChar.toUpper).mkString) mustBe true
    }
  }
}

