package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordHintsGeneratorSpec extends AnyFunSpec with Matchers {
  val inPosChar: Char = AInPosHint.inputChar
  val inWordChar: Char = AInWordHint.inputChar
  val missChar: Char = AMissHint.inputChar

  describe("Word Colors From (Potential) Answer") {
    it("identifies in-position letters") {
      val answer = "TRACE"
      WordHintsGenerator.generateWordWordHints(answer, "THANK", TestHintProps) mustBe
        List(AInPosHint, AMissHint, AInPosHint, AMissHint, AMissHint)

      WordHintsGenerator.generateWordWordHints(answer, "GRACE", TestHintProps) mustBe
        List(AMissHint, AInPosHint, AInPosHint, AInPosHint, AInPosHint)
    }

    it("identifies in-word letters") {
      val answer = "TRACE"
      WordHintsGenerator.generateWordWordHints(answer, "ABBEY", TestHintProps) mustBe
        List(AInWordHint, AMissHint, AMissHint, AInWordHint, AMissHint)

      WordHintsGenerator.generateWordWordHints(answer, "RACEX", TestHintProps) mustBe
        List(AInWordHint, AInWordHint, AInWordHint, AInWordHint, AMissHint)
    }

    it("identifies in-position and in-word letters") {
      val answer = "TRACE"
      WordHintsGenerator.generateWordWordHints(answer, "ABBEY", TestHintProps) mustBe
        List(AInWordHint, AMissHint, AMissHint, AInWordHint, AMissHint)

      WordHintsGenerator.generateWordWordHints(answer, "TREAK", TestHintProps) mustBe
        List(AInPosHint, AInPosHint, AInWordHint, AInWordHint, AMissHint)
    }

    it("marks a letter as a miss if the answer contains the letter but that letter been accounted for as in-position elsewhere in the word") {
      // The first A should be black, b/c while TRACE does contain an A it is accounted for by the AInPosHint in the 3rd position
      WordHintsGenerator.generateWordWordHints("TRACE", "ARATE", TestHintProps) mustBe
        List(AMissHint, AInPosHint, AInPosHint, AInWordHint, AInPosHint)

      // The second B should be black, b/c while BABES does contain two Bs they are accounted for by the AInPosHints in the 1st and 3rd position
      WordHintsGenerator.generateWordWordHints("BABES", "BBBSX", TestHintProps) mustBe
        List(AInPosHint, AMissHint, AInPosHint, AInWordHint, AMissHint)
    }

    it("marks a letter as letter as a miss if the letter has been accounted for as in a previous in-word hint") {
      // The second A should be black, since the first A is already AInWordHint and there's only one A in TRACE
      WordHintsGenerator.generateWordWordHints("TRACE", "TARAN", TestHintProps) mustBe
        List(AInPosHint, AInWordHint, AInWordHint, AMissHint, AMissHint)

      // The third A should be black, since the first two A's are already AInWordHint
      WordHintsGenerator.generateWordWordHints("TARAN", "ALALN", TestHintProps) mustBe
        List(AInWordHint, AMissHint, AInWordHint, AMissHint, AInPosHint)
    }

    it("marks a letter as letter as as a miss if the letter has been accounted for as in a previous in-word and a in-position") {
      // The second A should be black, since the first A is already AInWordHint and there's only one A in TRACE
      WordHintsGenerator.generateWordWordHints("TARAN", "AAALN", TestHintProps) mustBe
        List(AInWordHint, AInPosHint, AMissHint, AMissHint, AInPosHint)
    }

    it("handles the situation found from a rare use case found in the wild") {
      WordHintsGenerator.generateWordWordHints("67*7=469", "49-7*6=7", TestHintProps) mustBe
        List(AInWordHint, AInWordHint, AMissHint, AInPosHint, AInWordHint, AInWordHint, AInWordHint, AInWordHint)
    }
  }
}
