package com.skidis.wordle
package waffle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ValidWordGridGeneratorSpec extends AnyFunSpec with Matchers with WaffleHintProps {
  describe("Valid Word Grid Generator") {
    val charFreqMap = Map(
      'A' -> 1, 'B' -> 1, 'C' -> 1,
      'D' -> 1, 'E' -> 1, 'F' -> 1,
      'G' -> 1, 'H' -> 1, 'I' -> 1,
      'J' -> 1, 'K' -> 1, 'L' -> 1, 'M' -> 1,
      'X' -> 4, 'Y' -> 4
    )

    it("generates a set of word grids based on the cartesian product of thw wordSets in the source grid") {
      val wordSets = Seq(
        Set(WaffleWord("AJBKC"), WaffleWord("AKBJC")), // Neither of these change the character counts and only change
        Set(WaffleWord("DLEMF"), WaffleWord("DMELF")), // at non intersection positions so should still be valid
        Set(WaffleWord("GXHYI")),
        Set(WaffleWord("AXDYG")),
        Set(WaffleWord("BXEYH")),
        Set(WaffleWord("CXFYI"))
      )

      // Create a detail grid based on the word set
      val sourceGrid = WaffleDetailGrid(wordSets.map { ws => WaffleWordDetail(ws.head, Seq.fill(3)(inWordHint), ws) } )

      // There should be 4 possible word grids, 2 words in 2 word set and the rest with 1 = 2x2x1x1x1x1
      val expectedResults = Seq(
        WaffleWordGrid(Seq(WaffleWord("AJBKC"), WaffleWord("DLEMF"),
          WaffleWord("GXHYI"), WaffleWord("AXDYG"), WaffleWord("BXEYH"), WaffleWord("CXFYI")
        )),
        WaffleWordGrid(Seq(WaffleWord("AJBKC"), WaffleWord("DMELF"),
          WaffleWord("GXHYI"), WaffleWord("AXDYG"), WaffleWord("BXEYH"), WaffleWord("CXFYI")
        )),
        WaffleWordGrid(Seq(WaffleWord("AKBJC"), WaffleWord("DLEMF"),
          WaffleWord("GXHYI"), WaffleWord("AXDYG"), WaffleWord("BXEYH"), WaffleWord("CXFYI")
        )),
        WaffleWordGrid(Seq(WaffleWord("AKBJC"), WaffleWord("DMELF"),
          WaffleWord("GXHYI"), WaffleWord("AXDYG"), WaffleWord("BXEYH"), WaffleWord("CXFYI")
        ))
      )

      val results = ValidWordGridGenerator.generateValidGrids(sourceGrid, charFreqMap)
      results must contain theSameElementsAs expectedResults
    }
  }
}
