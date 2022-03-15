package com.skidis.wordle
package waffle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordSetsFromGridsGeneratorSpec extends AnyFunSpec with Matchers with WaffleHintProps {
  // Note: Doesn't have to be a full 6-word-detail grid, makes it easier for testing to have smaller grids
  describe("Word Sets From Grids Generator") {
    it("updates word sets from a single word grid") {
      val detail1 = WaffleWordDetail(WaffleWord("IDC1"), Seq.fill(5)(inWordHint), Set())
      val detail2 = WaffleWordDetail(WaffleWord("IDC2"), Seq.fill(5)(inWordHint), Set())
      val originalGrid = WaffleDetailGrid(Seq(detail1, detail2))

      val row0Words = Seq(WaffleWord("R0-W0"))
      val row1Words = Seq(WaffleWord("R1-W0"))
      val wordGrids = Seq(
        WaffleWordGrid(Seq(row0Words(0), row1Words(0)))
      )

      val resultGrid = WordSetsFromGridsGenerator.genWordSetsFromGrids(originalGrid, wordGrids)
      resultGrid(0).wordSet must contain theSameElementsAs row0Words
      resultGrid(1).wordSet must contain theSameElementsAs row1Words
    }

    it("updates word sets from a multiple word grids") {
      val detail1 = WaffleWordDetail(WaffleWord("IDC1"), Seq.fill(5)(inWordHint), Set())
      val detail2 = WaffleWordDetail(WaffleWord("IDC2"), Seq.fill(5)(inWordHint), Set())
      val originalGrid = WaffleDetailGrid(Seq(detail1, detail2))

      val row0Words = Seq(WaffleWord("R0-W0"), WaffleWord("R0-W1"), WaffleWord("R0_W2"))
      val row1Words = Seq(WaffleWord("R1-W0"), WaffleWord("R1-W1"))
      val wordGrids = Seq(
        WaffleWordGrid(Seq(row0Words(0), row1Words(0))),
        WaffleWordGrid(Seq(row0Words(0), row1Words(1))),
        WaffleWordGrid(Seq(row0Words(1), row1Words(0))),
        WaffleWordGrid(Seq(row0Words(1), row1Words(1))),
        WaffleWordGrid(Seq(row0Words(2), row1Words(0))),
        WaffleWordGrid(Seq(row0Words(2), row1Words(1))),
      )

      val resultGrid = WordSetsFromGridsGenerator.genWordSetsFromGrids(originalGrid, wordGrids)
      resultGrid(0).wordSet must contain theSameElementsAs row0Words
      resultGrid(1).wordSet must contain theSameElementsAs row1Words
    }
  }

}
