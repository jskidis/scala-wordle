package com.skidis.wordle
package wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordleResultValidatorSpec extends AnyFunSpec with Matchers {
  describe("Wordle Result Validator") {
    val inPosChar: Char = AInPosHint.inputChar
    val inWordChar: Char = AInWordHint.inputChar
    val missChar: Char = AMissHint.inputChar

    it("returns an error if input is not ${resultLength} chars in length") {
      WordleResulValidator.validateResult(Seq.fill(1)(inPosChar).mkString) must not be empty
      WordleResulValidator.validateResult(Seq.fill(8)(inPosChar).mkString) must not be empty
      WordleResulValidator.validateResult(Seq.fill(5)(inPosChar).mkString) mustBe empty
    }

    it("returns an error if any of characters in the string are 'g', 'y', or 'b'") {
      WordleResulValidator.validateResult(
        Seq(inPosChar, inWordChar, missChar, '.', ',').mkString
      ) must not be empty
      WordleResulValidator.validateResult(
        Seq('.', ',', inPosChar, inWordChar, missChar).mkString
      ) must not be empty
      WordleResulValidator.validateResult(
        Seq(inPosChar, '.', inWordChar, ',', missChar).mkString
      ) must not be empty
      WordleResulValidator.validateResult(
        Seq(inPosChar, inWordChar, missChar, inPosChar, inWordChar).mkString
      ) mustBe empty
    }

    it("ignores case for matching chars") {
      WordleResulValidator.validateResult(
        Seq(inPosChar.toUpper, inWordChar.toUpper, missChar.toUpper, inPosChar.toUpper, inWordChar.toUpper).mkString
      ) mustBe empty
      WordleResulValidator.validateResult(
        Seq(inPosChar.toLower, inWordChar.toLower, missChar.toLower, inPosChar.toLower, inWordChar.toLower).mkString
      ) mustBe empty
      WordleResulValidator.validateResult(
        Seq(inPosChar.toUpper, inWordChar.toLower, missChar.toUpper, inPosChar.toLower, inWordChar.toUpper).mkString
      ) mustBe empty
    }
  }
}
