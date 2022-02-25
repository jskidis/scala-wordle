package com.skidis.wordle

import BlockColor.{Blank, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordColorPatternGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Word Colors From (Potential) Answer") {
    it("identifies green letters") {
      val answer = "TRACE"
      WordColorPatternGenerator.generateStringColorPattern(answer, "THANK") mustBe List(Green, Blank, Green, Blank, Blank)
      WordColorPatternGenerator.generateStringColorPattern(answer, "GRACE") mustBe List(Blank, Green, Green, Green, Green)
    }

    it("identifies yellow letters") {
      val answer = "TRACE"
      WordColorPatternGenerator.generateStringColorPattern(answer, "ABBEY") mustBe List(Yellow, Blank, Blank, Yellow, Blank)
      WordColorPatternGenerator.generateStringColorPattern(answer, "RACEX") mustBe List(Yellow, Yellow, Yellow, Yellow, Blank)
    }

    it("identifies green and yellow letters") {
      val answer = "TRACE"
      WordColorPatternGenerator.generateStringColorPattern(answer, "ABBEY") mustBe List(Yellow, Blank, Blank, Yellow, Blank)
      WordColorPatternGenerator.generateStringColorPattern(answer, "TREAK") mustBe List(Green, Green, Yellow, Yellow, Blank)
    }

    it("marks a letter as black if the answer contains the letter but that letter been accounted for as green elsewhere in the word") {
      // The first A should be black, b/c while TRACE does contain an A it is accounted for by the Green in the 3rd position
      WordColorPatternGenerator.generateStringColorPattern("TRACE", "ARATE") mustBe List(Blank, Green, Green, Yellow, Green)

      // The second B should be black, b/c while BABES does contain two Bs they are accounted for by the Greens in the 1st and 3rd position
      WordColorPatternGenerator.generateStringColorPattern("BABES", "BBBSX") mustBe List(Green, Blank, Green, Yellow, Blank)
    }

    it("marks a letter as letter as black if the letter has been accounted for as in previous yellow") {
      // The second A should be black, since the first A is already yellow and there's only one A in TRACE
      WordColorPatternGenerator.generateStringColorPattern("TRACE", "TARAN") mustBe List(Green, Yellow, Yellow, Blank, Blank)

      // The third A should be black, since the first two A's are already yellow
      WordColorPatternGenerator.generateStringColorPattern("TARAN", "ALALN") mustBe List(Yellow, Blank, Yellow, Blank, Green)
    }

    it("marks a letter as letter as black if the letter has been accounted for as in previous yellow and a green") {
      // The second A should be black, since the first A is already yellow and there's only one A in TRACE
      WordColorPatternGenerator.generateStringColorPattern("TARAN", "AAALN") mustBe List(Yellow, Green, Blank, Blank, Green)
    }

    it("handles the situation found from a rare use case found in the wild") {
      val pattern = WordColorPatternGenerator.generateStringColorPattern("67*7=469", "49-7*6=7")
      pattern mustBe List(Yellow, Yellow, Blank, Green, Yellow, Yellow, Yellow, Yellow)
    }
  }
}
