package com.skidis.wordle

import BlockColor.{Blank, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputToColorsConversionSpec extends AnyFunSpec with Matchers {
  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      InputToColorsConversion.convertInputToColors("") mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = List(greenChar, yellowChar, blankChar).mkString
      val expectedPattern = List(Green, Yellow, Blank)

      InputToColorsConversion.convertInputToColors(input) mustBe expectedPattern
    }

    it("returns the correct color for each char in input for longer words") {
      val input = List(greenChar, yellowChar, greenChar, blankChar, yellowChar).mkString
      val expectedPattern = List(Green, Yellow, Green, Blank, Yellow)

      InputToColorsConversion.convertInputToColors(input) mustBe expectedPattern
    }
  }
}
