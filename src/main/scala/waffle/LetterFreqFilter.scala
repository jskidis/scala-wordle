package com.skidis.wordle
package waffle

trait LetterFreqFilter {
  def filterByLetterFreq(grid: WaffleDetailGrid, gridFreqMap: CharFreqMap): WaffleDetailGrid = {
    WaffleDetailGrid(
      grid().map { wwd =>
        val newWordSet = wwd.wordSet.filter { w: XrdleWord =>
          val wordFreqMap = CharFreqMapGenerator.generateCharFreqMap(w.text)
          w.text.forall { ch =>
              wordFreqMap.getOrElse(ch, 0) <= gridFreqMap.getOrElse(ch, 0)
          }
        }
        wwd.replaceWordSet(newWordSet)
      }
    )
  }
}

object LetterFreqFilter extends LetterFreqFilter


