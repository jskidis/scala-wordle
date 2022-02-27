package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordHintsGeneratorSpec extends AnyFunSpec with Matchers {
  val inPosChar: Char = AInPosHint.inputChar
  val inWordChar: Char = AInWordHint.inputChar
  val missChar: Char = AMissHint.inputChar
  
  object TWordHintsGenerator extends WordHintsGenerator with TestHintProps

  describe("Word Colors From (Potential) Answer") {
    it("identifies in-position letters") {
      val answer = "TRACE"
      TWordHintsGenerator.generateWordWordHints(answer, "THANK") mustBe
        Seq(AInPosHint, AMissHint, AInPosHint, AMissHint, AMissHint)

      TWordHintsGenerator.generateWordWordHints(answer, "GRACE") mustBe
        Seq(AMissHint, AInPosHint, AInPosHint, AInPosHint, AInPosHint)
    }

    it("identifies in-word letters") {
      val answer = "TRACE"
      TWordHintsGenerator.generateWordWordHints(answer, "ABBEY") mustBe
        Seq(AInWordHint, AMissHint, AMissHint, AInWordHint, AMissHint)

      TWordHintsGenerator.generateWordWordHints(answer, "RACEX") mustBe
        Seq(AInWordHint, AInWordHint, AInWordHint, AInWordHint, AMissHint)
    }

    it("identifies in-position and in-word letters") {
      val answer = "TRACE"
      TWordHintsGenerator.generateWordWordHints(answer, "ABBEY") mustBe
        Seq(AInWordHint, AMissHint, AMissHint, AInWordHint, AMissHint)

      TWordHintsGenerator.generateWordWordHints(answer, "TREAK") mustBe
        Seq(AInPosHint, AInPosHint, AInWordHint, AInWordHint, AMissHint)
    }

    it("marks a letter as a miss if the answer contains the letter but that letter been accounted for as in-position elsewhere in the word") {
      // The first A should be black, b/c while TRACE does contain an A it is accounted for by the AInPosHint in the 3rd position
      TWordHintsGenerator.generateWordWordHints("TRACE", "ARATE") mustBe
        Seq(AMissHint, AInPosHint, AInPosHint, AInWordHint, AInPosHint)

      // The second B should be black, b/c while BABES does contain two Bs they are accounted for by the AInPosHints in the 1st and 3rd position
      TWordHintsGenerator.generateWordWordHints("BABES", "BBBSX") mustBe
        Seq(AInPosHint, AMissHint, AInPosHint, AInWordHint, AMissHint)
    }

    it("marks a letter as letter as a miss if the letter has been accounted for as in a previous in-word hint") {
      // The second A should be black, since the first A is already AInWordHint and there's only one A in TRACE
      TWordHintsGenerator.generateWordWordHints("TRACE", "TARAN") mustBe
        Seq(AInPosHint, AInWordHint, AInWordHint, AMissHint, AMissHint)

      // The third A should be black, since the first two A's are already AInWordHint
      TWordHintsGenerator.generateWordWordHints("TARAN", "ALALN") mustBe
        Seq(AInWordHint, AMissHint, AInWordHint, AMissHint, AInPosHint)
    }

    it("marks a letter as letter as as a miss if the letter has been accounted for as in a previous in-word and a in-position") {
      // The second A should be black, since the first A is already AInWordHint and there's only one A in TRACE
      TWordHintsGenerator.generateWordWordHints("TARAN", "AAALN") mustBe
        Seq(AInWordHint, AInPosHint, AMissHint, AMissHint, AInPosHint)
    }

    it("handles the situation found from a rare use case found in the wild") {
      TWordHintsGenerator.generateWordWordHints("67*7=469", "49-7*6=7") mustBe
        Seq(AInWordHint, AInWordHint, AMissHint, AInPosHint, AInWordHint, AInWordHint, AInWordHint, AInWordHint)
    }
  }
}
