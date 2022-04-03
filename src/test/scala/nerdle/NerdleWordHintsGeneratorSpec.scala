package com.skidis.wordle
package nerdle

import hintgen.WordHintsGenerator

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class NerdleWordHintsGeneratorSpec extends AnyFunSpec with Matchers {

  val baseClassReturn: WordHints = Seq.fill(8)(NerdleMissHint)

  trait BaseWordHintsGenerator extends WordHintsGenerator with NerdleHintProps {
    override def generateWordHints(answer: String, word: String): WordHints = baseClassReturn
  }

  val testFixture = new BaseWordHintsGenerator with NerdleWordHintsGenerator

  describe("Nerdle Word Hints Generator") {
    it("returns value from super.generateWordHints if answer has equiv commutative expressions") {
      val answer = "60-40=20" // No commutative equivalents
      val word = "62-42-20"
      testFixture.generateWordHints(answer, word) mustBe baseClassReturn
    }

    it("returns value from super.generateWordHints if answer has equiv expressions but none of them match the word") {
      val answer = "2*3*4=12" // No commutative equivalents
      val word = "1*2*6=12"
      testFixture.generateWordHints(answer, word) mustBe baseClassReturn
    }

    it("return all in-position hints if answer has equiv expressions and matches the word") {
      val answer = "4*3-2=10" // No commutative equivalents
      val word = "3*4-2=10"
      testFixture.generateWordHints(answer, word) mustBe Seq.fill(answer.length)(NerdleInPosHint)
    }

    it("returns value from super.generateWordHints if either equation text can't be parsed") {
      val answer = "2+2=5" // No commutative equivalents
      val word = "2+3=5"
      testFixture.generateWordHints(answer, word) mustBe baseClassReturn
    }
  }

}
