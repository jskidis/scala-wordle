package com.skidis.wordle
package waffle

trait ValidWordGridGenerator {
  def generateValidGrids(grid: WaffleDetailGrid, charFreqMap: CharFreqMap): Seq[WaffleWordGrid] = {
    // If I was going to expand the waffle concept further, I would check to make sure grid is 6 words, maybe return an option if it's not
    val wordGrids = genPossibleGrids(grid)
    wordGrids.filter { wordGrid =>
      doesGridMaintainCharFreq(wordGrid, charFreqMap)
    }
  }

  private def genPossibleGrids(grid: WaffleDetailGrid): Seq[WaffleWordGrid] = {
    // there's got to be a cleaner way to do this
    for {
      w0 <- grid(0).wordSet
      w1 <- grid(1).wordSet
      w2 <- grid(2).wordSet
      w3 <- grid(3).wordSet
      w4 <- grid(4).wordSet
      w5 <- grid(5).wordSet
    } yield WaffleWordGrid(Seq(w0, w1, w2, w3, w4, w5))
  }.toSeq

  private def doesGridMaintainCharFreq(grid: WaffleWordGrid, charFreqMap: CharFreqMap): Boolean = {
    val charMap = CharFreqMapGenerator.generateCharFreqMap(grid.gridLetters)
    charMap.forall{ case (ch: Char, cnt: Int) =>
      cnt == charFreqMap.getOrElse(ch, 0)
    }
  }
}

object ValidWordGridGenerator extends ValidWordGridGenerator
