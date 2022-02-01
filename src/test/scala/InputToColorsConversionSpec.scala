package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputToColorsConversionSpec extends AnyFunSpec with Matchers {
  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      InputToColorsConversion.convert("") mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = List(greenChar, yellowChar, blackChar).mkString
      val expectedPattern = List(Green, Yellow, Black)

      InputToColorsConversion.convert(input) mustBe expectedPattern
    }

    it("returns the correct color for each char in input for longer words") {
      val input = List(greenChar, yellowChar, greenChar, blackChar, yellowChar).mkString
      val expectedPattern = List(Green, Yellow, Green, Black, Yellow)

      InputToColorsConversion.convert(input) mustBe expectedPattern
    }
  }
}