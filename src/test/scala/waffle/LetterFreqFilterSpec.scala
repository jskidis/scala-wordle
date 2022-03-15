package com.skidis.wordle
package waffle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class LetterFreqFilterSpec extends AnyFunSpec with Matchers with WaffleHintProps {
  // Doesn't have to be a full 6-word-detail grid, makes it easier for testing to have smaller grids

  describe("Letter Frequency Filter") {
    val charFreqMap = Map('A' -> 3, 'B' -> 2, 'C' -> 1, 'D' -> 1, 'E' -> 2, 'F' -> 1)
    val validWords = Seq(WaffleWord("ABCDE"), WaffleWord("BDEFE"), WaffleWord("AAABB"))
    val notInMapWords = Seq(WaffleWord("VWXYZ"), WaffleWord("AXYZE"), WaffleWord("BCECB"))
    val tooManyCharWords = Seq(WaffleWord("ABCCD"), WaffleWord("ABBBA"), WaffleWord("CCEFF"))

  it("filters out words in word set if letter isn't in frequency map") {
      val grid = WaffleDetailGrid(Seq(
        WaffleWordDetail(validWords(0), Seq.fill(5)(inWordHint), notInMapWords.toSet)
      ))

      // should filter out all words in word set
      val resultGrid = LetterFreqFilter.filterByLetterFreq(grid, charFreqMap)
      resultGrid(0).wordSet mustBe empty
    }

    it("filters out words in word set if letter is in word more times than it's in frequency map") {
      val grid = WaffleDetailGrid(Seq(
        WaffleWordDetail(validWords(0), Seq.fill(5)(inWordHint), tooManyCharWords.toSet)
      ))

      // should filter out all words in word set
      val resultGrid = LetterFreqFilter.filterByLetterFreq(grid, charFreqMap)
      resultGrid(0).wordSet mustBe empty
    }

    it("filters out appropriately leaving valid words in word sets") {
      val grid = WaffleDetailGrid(Seq(
        WaffleWordDetail(validWords(0), Seq.fill(5)(inWordHint), Set(tooManyCharWords(0), validWords(0), notInMapWords(0))),
        WaffleWordDetail(validWords(1), Seq.fill(5)(inWordHint), Set(tooManyCharWords(1), validWords(1), notInMapWords(1))),
        WaffleWordDetail(validWords(2), Seq.fill(5)(inWordHint), Set(tooManyCharWords(2), validWords(2), notInMapWords(2)))
      ))

      // should filter out all but valid words
      val resultGrid = LetterFreqFilter.filterByLetterFreq(grid, charFreqMap)
      resultGrid(0).wordSet mustBe Set(validWords(0))
      resultGrid(1).wordSet mustBe Set(validWords(1))
      resultGrid(2).wordSet mustBe Set(validWords(2))
    }
  }
}
