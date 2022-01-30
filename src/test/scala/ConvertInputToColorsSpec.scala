package com.skidis.wordle

import BlockColors.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ConvertInputToColorsSpec extends AnyFunSpec with Matchers {
  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      val colors = ConvertInputToColors("")
      colors mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = List(greenChar, yellowChar, blackChar).mkString
      val expectedColors = List(Green, Yellow, Black)

      ConvertInputToColors(input) mustBe expectedColors
    }
  }
}
