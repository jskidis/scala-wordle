package com.skidis.wordle
package input

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class BasicResultValidatorSpec extends AnyFunSpec with Matchers  {

  object FiveCharBasicResultValidator extends BasicResultValidator {
    override def resultLength: Int = 5
    override def validChars: List[Char] = validBlockChars
  }

  describe("Input Validator Spec") {
    it("returns an error if input is not 5 chars in length") {
      FiveCharBasicResultValidator.validateResult(List.fill(1)(greenChar).mkString) must not be empty
      FiveCharBasicResultValidator.validateResult(List.fill(4)(greenChar).mkString) must not be empty
      FiveCharBasicResultValidator.validateResult(List.fill(6)(greenChar).mkString) must not be empty
      FiveCharBasicResultValidator.validateResult(List.fill(8)(greenChar).mkString) must not be empty
      FiveCharBasicResultValidator.validateResult(List.fill(5)(greenChar).mkString) mustBe empty
    }

    it("returns an error if any of characters in the string are 'g', 'y', or 'b'") {
      FiveCharBasicResultValidator.validateResult(
        List(greenChar, yellowChar, blankChar, '.', ',').mkString
      ) must not be empty
      FiveCharBasicResultValidator.validateResult(
        List('.', ',', greenChar, yellowChar, blankChar).mkString
      ) must not be empty
      FiveCharBasicResultValidator.validateResult(
        List(greenChar, '.', yellowChar, ',', blankChar).mkString
      ) must not be empty
      FiveCharBasicResultValidator.validateResult(
        List(greenChar, yellowChar, blankChar, greenChar, yellowChar).mkString
      ) mustBe empty
    }

    it("ignores case for matching chars") {
      FiveCharBasicResultValidator.validateResult(
        List(greenChar.toUpper, yellowChar.toUpper, blankChar.toUpper, greenChar.toUpper, yellowChar.toUpper).mkString
      ) mustBe empty
      FiveCharBasicResultValidator.validateResult(
        List(greenChar.toLower, yellowChar.toLower, blankChar.toLower, greenChar.toLower, yellowChar.toLower).mkString
      ) mustBe empty
      FiveCharBasicResultValidator.validateResult(
        List(greenChar.toUpper, yellowChar.toLower, blankChar.toUpper, greenChar.toLower, yellowChar.toUpper).mkString
      ) mustBe empty
    }
  }
}


