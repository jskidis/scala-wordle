package com.skidis.wordle

import TestFixtures.{TInPosHint, TInWordHint, TMissHint, TestHintProps}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordHintsGeneratorSpec extends AnyFunSpec with Matchers {
  object GeneratorFixture extends WordHintsGenerator with TestHintProps

  describe("Word Colors From (Potential) Answer") {
    it("identifies in-position letters") {
      val answer = "TRACE"
      GeneratorFixture.generateWordWordHints(answer, "THANK") mustBe
        Seq(TInPosHint, TMissHint, TInPosHint, TMissHint, TMissHint)

      GeneratorFixture.generateWordWordHints(answer, "GRACE") mustBe
        Seq(TMissHint, TInPosHint, TInPosHint, TInPosHint, TInPosHint)
    }

    it("identifies in-word letters") {
      val answer = "TRACE"
      GeneratorFixture.generateWordWordHints(answer, "ABBEY") mustBe
        Seq(TInWordHint, TMissHint, TMissHint, TInWordHint, TMissHint)

      GeneratorFixture.generateWordWordHints(answer, "RACEX") mustBe
        Seq(TInWordHint, TInWordHint, TInWordHint, TInWordHint, TMissHint)
    }

    it("identifies in-position and in-word letters") {
      val answer = "TRACE"
      GeneratorFixture.generateWordWordHints(answer, "ABBEY") mustBe
        Seq(TInWordHint, TMissHint, TMissHint, TInWordHint, TMissHint)

      GeneratorFixture.generateWordWordHints(answer, "TREAK") mustBe
        Seq(TInPosHint, TInPosHint, TInWordHint, TInWordHint, TMissHint)
    }

    it("marks a letter as a miss if the answer contains the letter but that letter been accounted for as in-position elsewhere in the word") {
      // The first A should be black, b/c while TRACE does contain an A it is accounted for by the AInPosHint in the 3rd position
      GeneratorFixture.generateWordWordHints("TRACE", "ARATE") mustBe
        Seq(TMissHint, TInPosHint, TInPosHint, TInWordHint, TInPosHint)

      // The second B should be black, b/c while BABES does contain two Bs they are accounted for by the AInPosHints in the 1st and 3rd position
      GeneratorFixture.generateWordWordHints("BABES", "BBBSX") mustBe
        Seq(TInPosHint, TMissHint, TInPosHint, TInWordHint, TMissHint)
    }

    it("marks a letter as letter as a miss if the letter has been accounted for as in a previous in-word hint") {
      // The second A should be black, since the first A is already AInWordHint and there's only one A in TRACE
      GeneratorFixture.generateWordWordHints("TRACE", "TARAN") mustBe
        Seq(TInPosHint, TInWordHint, TInWordHint, TMissHint, TMissHint)

      // The third A should be black, since the first two A's are already AInWordHint
      GeneratorFixture.generateWordWordHints("TARAN", "ALALN") mustBe
        Seq(TInWordHint, TMissHint, TInWordHint, TMissHint, TInPosHint)
    }

    it("marks a letter as letter as as a miss if the letter has been accounted for as in a previous in-word and a in-position") {
      // The second A should be black, since the first A is already AInWordHint and there's only one A in TRACE
      GeneratorFixture.generateWordWordHints("TARAN", "AAALN") mustBe
        Seq(TInWordHint, TInPosHint, TMissHint, TMissHint, TInPosHint)
    }

    it("handles the situation found from a rare use case found in the wild") {
      GeneratorFixture.generateWordWordHints("67*7=469", "49-7*6=7") mustBe
        Seq(TInWordHint, TInWordHint, TMissHint, TInPosHint, TInWordHint, TInWordHint, TInWordHint, TInWordHint)
    }
  }
}
