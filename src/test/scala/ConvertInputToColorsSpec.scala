package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ConvertInputToColorsSpec extends AnyFunSpec with Matchers {
  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      ConvertInputToColors("") mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = List(greenChar, yellowChar, blackChar).mkString
      val expectedPattern = List(Green, Yellow, Black)

      ConvertInputToColors(input) mustBe expectedPattern
    }
  }
}
