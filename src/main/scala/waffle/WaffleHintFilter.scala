package com.skidis.wordle
package waffle

import scala.annotation.tailrec

trait WaffleHintFilter extends WaffleHintProps with WaffleGuessProps {
  def filterOnHints(grid: WaffleDetailGrid): WaffleDetailGrid = {
    WaffleDetailGrid(grid().map { wwd: WaffleWordDetail =>
      if (wwd.word.text.length == guessWordLength && wwd.hints.length == guessWordLength) {
        val hintSets = hintVariants(Seq(wwd.hints))
        val wordPatterns = hintSets.map { hints => wwd.word.text.zip(hints) }
        val remainingWords = wwd.wordSet.filter { rw: XrdleWord =>
          wordPatterns.exists { wp => WordPatternMatcher.doesWordMatch(rw.text, wp) }
        }
        wwd.replaceWordSet(remainingWords)
      }
      else wwd.replaceWordSet(Set())
    })
  }

  // An in-word hint that is at an intersection (positions 0, 2, & 4 of a word) that hint may represent that the letter
  //  is in the word, but it may also may mean that the letter is in a different position of the intersecting word
  // For each in-word hint at an intersection generate 2 variants, one with the in-word hint and one with a miss hint
  // This will generate 1, 2, 4, or 8 for each variants depending on the number of intersection letter with miss hints
  @tailrec
  private def hintVariants(hintSet:Seq[WordHints], index: Int = 0): Seq[WordHints] = {
    if (index > 4) hintSet
    else if (hintSet.head(index) != inWordHint) hintVariants(hintSet, index +2)
    else {
      val newHintSets: Seq[WordHints] = hintSet.flatMap { hint =>
        val addtnlHint: WordHints = hint.zipWithIndex.map { case (b: HintBlock, i: Int) =>
          if (i != index) b else missHint
        }
        Seq(hint, addtnlHint)
      }
      hintVariants(newHintSets, index +2)
    }
  }
}

object WaffleHintFilter extends WaffleHintFilter
