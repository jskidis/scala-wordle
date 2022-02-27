package com.skidis.wordle
package input

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputToHintConversionSpec extends AnyFunSpec with Matchers {
  val inPosChar: Char = AInPosHint.inputChar
  val inWordChar: Char = AInWordHint.inputChar
  val missChar: Char = AMissHint.inputChar

  object TInputToHintConversion extends InputToHintConversion with TestHintProps

  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      TInputToHintConversion.convertInputToHints("") mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = Seq(inPosChar, inWordChar, missChar).mkString
      val expectedPattern = Seq(AInPosHint, AInWordHint, AMissHint)

      TInputToHintConversion.convertInputToHints(input) mustBe expectedPattern
    }

    it("returns the correct color for each char in input for longer words") {
      val input = Seq(inPosChar, inWordChar, inPosChar, missChar, inWordChar).mkString
      val expectedPattern = Seq(AInPosHint, AInWordHint, AInPosHint, AMissHint, AInWordHint)

      TInputToHintConversion.convertInputToHints(input) mustBe expectedPattern
    }
  }
}
