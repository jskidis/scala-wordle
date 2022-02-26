package com.skidis.wordle
package input

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputToColorsConversionSpec extends AnyFunSpec with Matchers {
  val inPosChar: Char = AInPosHint.inputChar
  val inWordChar: Char = AInWordHint.inputChar
  val missChar: Char = AMissHint.inputChar

  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      InputToColorsConversion.convertInputToColors("", TestHintProps) mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = List(inPosChar, inWordChar, missChar).mkString
      val expectedPattern = List(AInPosHint, AInWordHint, AMissHint)

      InputToColorsConversion.convertInputToColors(input, TestHintProps) mustBe expectedPattern
    }

    it("returns the correct color for each char in input for longer words") {
      val input = List(inPosChar, inWordChar, inPosChar, missChar, inWordChar).mkString
      val expectedPattern = List(AInPosHint, AInWordHint, AInPosHint, AMissHint, AInWordHint)

      InputToColorsConversion.convertInputToColors(input, TestHintProps) mustBe expectedPattern
    }
  }
}
