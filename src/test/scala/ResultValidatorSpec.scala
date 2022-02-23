package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ResultValidatorSpec extends AnyFunSpec with Matchers  {

  object FiveCharResultValidator extends ResultValidator {
    override def resultLength: Int = 5
    override def validationErrorMsg: String = "Invalid Result"
  }

  describe("Input Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      FiveCharResultValidator.validateResult(List.fill(1)(greenChar).mkString) mustBe false
      FiveCharResultValidator.validateResult(List.fill(4)(greenChar).mkString) mustBe false
      FiveCharResultValidator.validateResult(List.fill(6)(greenChar).mkString) mustBe false
      FiveCharResultValidator.validateResult(List.fill(8)(greenChar).mkString) mustBe false
      FiveCharResultValidator.validateResult(List.fill(5)(greenChar).mkString) mustBe true
    }

    it("returns false if any of characters in the string are 'g', 'y', or 'b'") {
      FiveCharResultValidator.validateResult(List(greenChar, yellowChar, blankChar, '.', ',').mkString) mustBe false
      FiveCharResultValidator.validateResult(List('.', ',', greenChar, yellowChar, blankChar).mkString) mustBe false
      FiveCharResultValidator.validateResult(List(greenChar, '.', yellowChar, ',', blankChar).mkString) mustBe false
      FiveCharResultValidator.validateResult(List(greenChar, yellowChar, blankChar, greenChar, yellowChar).mkString) mustBe true
    }

    it("ignores case for matching chars") {
      FiveCharResultValidator.validateResult(List(greenChar.toUpper, yellowChar.toUpper, blankChar.toUpper, greenChar.toUpper, yellowChar.toUpper).mkString) mustBe true
      FiveCharResultValidator.validateResult(List(greenChar.toLower, yellowChar.toLower, blankChar.toLower, greenChar.toLower, yellowChar.toLower).mkString) mustBe true
      FiveCharResultValidator.validateResult(List(greenChar.toUpper, yellowChar.toLower, blankChar.toUpper, greenChar.toLower, yellowChar.toUpper).mkString) mustBe true
    }
  }
}


