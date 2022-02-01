package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordColorPatternGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Word Colors From (Potential) Answer") {
    it("identifies green letters") {
      val answer = "TRACE"
      WordColorPatternGenerator.generate(answer, "THANK") mustBe List(Green, Black, Green, Black, Black)
      WordColorPatternGenerator.generate(answer, "GRACE") mustBe List(Black, Green, Green, Green, Green)
    }

    it("identifies yellow letters") {
      val answer = "TRACE"
      WordColorPatternGenerator.generate(answer, "ABBEY") mustBe List(Yellow, Black, Black, Yellow, Black)
      WordColorPatternGenerator.generate(answer, "RACEX") mustBe List(Yellow, Yellow, Yellow, Yellow, Black)
    }

    it("identifies green and yellow letters") {
      val answer = "TRACE"
      WordColorPatternGenerator.generate(answer, "ABBEY") mustBe List(Yellow, Black, Black, Yellow, Black)
      WordColorPatternGenerator.generate(answer, "TREAK") mustBe List(Green, Green, Yellow, Yellow, Black)
    }

    it("marks a letter as black if the answer contains the letter but that letter been accounted for as green elsewhere in the word") {
      // The first A should be black, b/c while TRACE does contain an A it is accounted for by the Green in the 3rd position
      WordColorPatternGenerator.generate("TRACE", "ARATE") mustBe List(Black, Green, Green, Yellow, Green)

      // The second B should be black, b/c while BABES does contain two Bs they are accounted for by the Greens in the 1st and 3rd position
      WordColorPatternGenerator.generate("BABES", "BBBSX") mustBe List(Green, Black, Green, Yellow, Black)
    }

    it("marks a letter as letter as black if the letter has been accounted for as in previous yellow") {
      // The second A should be black, since the first A is already yellow and there's only one A in TRACE
      WordColorPatternGenerator.generate("TRACE", "TARAN") mustBe List(Green, Yellow, Yellow, Black, Black)

      // The third A should be black, since the first two A's are already yellow
      WordColorPatternGenerator.generate("TARAN", "ALALN") mustBe List(Yellow, Black, Yellow, Black, Green)
    }

    it("marks a letter as letter as black if the letter has been accounted for as in previous yellow and a green") {
      // The second A should be black, since the first A is already yellow and there's only one A in TRACE
      WordColorPatternGenerator.generate("TARAN", "AAALN") mustBe List(Yellow, Green, Black, Black, Green)
    }
  }
}
