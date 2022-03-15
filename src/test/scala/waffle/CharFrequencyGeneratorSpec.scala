package com.skidis.wordle
package waffle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class CharFrequencyGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Char Frequency Generator") {
    it("returns a map with each character with a frequency of 1 when given a word with no repeat letters") {
      val testWord = "ABCDEFG"
      val result = CharFreqMapGenerator.generateCharFreqMap(testWord)

      result.keys must contain theSameElementsAs testWord
      result.values.forall(count => count == 1) mustBe true
    }

    it("returns counts appropriate for repeated letters") {
      val testWord = "ABCABAD"
      val expectedResults = Map('A' ->3, 'B' -> 2, 'C' -> 1, 'D' ->1)

      CharFreqMapGenerator.generateCharFreqMap(testWord) mustBe expectedResults
    }

    it("return case insensitive map") {
      val testWord = "aBcABaD"
      val expectedResults = Map('A' ->3, 'B' -> 2, 'C' -> 1, 'D' ->1)

      CharFreqMapGenerator.generateCharFreqMap(testWord) mustBe expectedResults

    }
  }

}
