package com.skidis.wordle
package input

import TestFixtures._

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputToHintConversionSpec extends AnyFunSpec with Matchers {

  object HintConversionFixture extends InputToHintConversion with TestHintProps

  describe("Convert Input To Colors") {
    it("returns an empty color list if input is empty") {
      HintConversionFixture.convertInputToHints("") mustBe empty
    }

    it("returns the correct color for each char in input") {
      val input = Seq(inPosChar, inWordChar, missChar).mkString
      val expectedPattern = Seq(TInPosHint, TInWordHint, TMissHint)

      HintConversionFixture.convertInputToHints(input) mustBe expectedPattern
    }

    it("returns the correct color for each char in input for longer words") {
      val input = Seq(inPosChar, inWordChar, inPosChar, missChar, inWordChar).mkString
      val expectedPattern = Seq(TInPosHint, TInWordHint, TInPosHint, TMissHint, TInWordHint)

      HintConversionFixture.convertInputToHints(input) mustBe expectedPattern
    }
  }
}
