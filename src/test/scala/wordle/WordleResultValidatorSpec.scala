package com.skidis.wordle
package wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordleResultValidatorSpec extends AnyFunSpec with Matchers {
  describe("Wordle Result Validator") {
    it("returns an error if input is not ${resultLength} chars in length") {
      WordleResulValidator.validateResult(List.fill(1)(greenChar).mkString) must not be empty
      WordleResulValidator.validateResult(List.fill(8)(greenChar).mkString) must not be empty
      WordleResulValidator.validateResult(List.fill(5)(greenChar).mkString) mustBe empty
    }

    it("returns an error if any of characters in the string are 'g', 'y', or 'b'") {
      WordleResulValidator.validateResult(
        List(greenChar, yellowChar, blankChar, '.', ',').mkString
      ) must not be empty
      WordleResulValidator.validateResult(
        List('.', ',', greenChar, yellowChar, blankChar).mkString
      ) must not be empty
      WordleResulValidator.validateResult(
        List(greenChar, '.', yellowChar, ',', blankChar).mkString
      ) must not be empty
      WordleResulValidator.validateResult(
        List(greenChar, yellowChar, blankChar, greenChar, yellowChar).mkString
      ) mustBe empty
    }

    it("ignores case for matching chars") {
      WordleResulValidator.validateResult(
        List(greenChar.toUpper, yellowChar.toUpper, blankChar.toUpper, greenChar.toUpper, yellowChar.toUpper).mkString
      ) mustBe empty
      WordleResulValidator.validateResult(
        List(greenChar.toLower, yellowChar.toLower, blankChar.toLower, greenChar.toLower, yellowChar.toLower).mkString
      ) mustBe empty
      WordleResulValidator.validateResult(
        List(greenChar.toUpper, yellowChar.toLower, blankChar.toUpper, greenChar.toLower, yellowChar.toUpper).mkString
      ) mustBe empty
    }
  }
}
