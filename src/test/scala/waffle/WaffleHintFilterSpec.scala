package com.skidis.wordle
package waffle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WaffleHintFilterSpec extends AnyFunSpec with Matchers with WaffleHintProps {
  // Note: Doesn't have to be a full 6-word-detail grid, makes it easier for testing to have smaller grids
  // Note: Not testing the underlying word pattern matcher function, assume it works as intended
  describe("Waffle Hint Filter") {
    it("filters out words that don't match pattern") {
      val word = WaffleWord("ABCDE")
      val wordHints = Seq(inPosHint, inWordHint, missHint, missHint, inPosHint)
      val matchingWords = Set(WaffleWord("AXBYE"), WaffleWord("AVXBE"))
      val notMatchingWords = Set(
        WaffleWord("XBAEY"), // A & E aren't in right position
        WaffleWord("AXYZE"), // No B, in-word hint
        WaffleWord("ABXYE"), // B, in-word hint, is same position as in word
        WaffleWord("ADBCE") // C & D, miss hitns, is in word
      )

      val grid = WaffleDetailGrid(Seq(WaffleWordDetail(word, wordHints, matchingWords ++ notMatchingWords)))
      val resultGrid = WaffleHintFilter.filterOnHints(grid)
      resultGrid(0).wordSet must contain theSameElementsAs matchingWords
    }

    it("handles case where an in-word hint as it an intersection (positions 0, 2, or 4)") {
      val word = WaffleWord("ABCDE")
      val wordHints = Seq(inWordHint, inWordHint, inWordHint, missHint, inPosHint)
      val matchingWords = Set(
        WaffleWord("XYZBE"), // A&C don't have to be in word since those in-word hints are at an intersection
        WaffleWord("CYZBE"), // but they could be in the word at a different position
        WaffleWord("XYABE") // but they could be in the word at a different position
      )
      val notMatchingWords = Set(
        WaffleWord("XYZAE"), // B is not an intersection so must be in word
        WaffleWord("XBYZE"), // B can't be in same position is in comparison word
        WaffleWord("AYZBE"), // A can't be in same position is in comparison word (even though it's at an intersection)
        WaffleWord("XYCBE") // B can't be in same position is in comparison word (even though it's at an intersection)
      )

      val grid = WaffleDetailGrid(Seq(WaffleWordDetail(word, wordHints, matchingWords ++ notMatchingWords)))
      val resultGrid = WaffleHintFilter.filterOnHints(grid)
      resultGrid(0).wordSet must contain theSameElementsAs matchingWords
    }

    it("if comparison word or hints aren't 5 in length an empty word set is returned") {
      val grid = WaffleDetailGrid(Seq(
        WaffleWordDetail(WaffleWord("ABCDE"), Seq.fill(5)(inPosHint), Set(WaffleWord("ABCDE"))), // should be good
        WaffleWordDetail(WaffleWord("ABCD"), Seq.fill(4)(inPosHint), Set(WaffleWord("ABCD"))) // should be filtered out
      ))

      val resultGrid = WaffleHintFilter.filterOnHints(grid)
      resultGrid(0).wordSet must have size 1
      resultGrid(1).wordSet mustBe empty
    }
  }
}
