package com.skidis.wordle
package waffle

trait LetterFreqFilter {
  def filterByLetterFreq(grid: WaffleDetailGrid, gridFreqMap: CharFreqMap): WaffleDetailGrid = {
    WaffleDetailGrid(
      grid().map { wwd =>
        val newWordSet = wwd.wordSet.filter { w: XordlePhrase =>
          val wordFreqMap = CharFreqMapGenerator.generateCharFreqMap(w.phrase)
          w.phrase.forall { ch =>
              wordFreqMap.getOrElse(ch, 0) <= gridFreqMap.getOrElse(ch, 0)
          }
        }
        wwd.replaceWordSet(newWordSet)
      }
    )
  }
}

object LetterFreqFilter extends LetterFreqFilter


