package com.skidis.wordle
package strategy

import hintgen.WordHintsGenerator

trait ClusterStrategy extends WordScoringStrategy with HardModeWordElimStrategy with ClusterGenerator {
  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    (word: XrdleWord) => {
      val uniquePatterns = generateUniquePatterns(word, remainingWords)
      uniquePatterns.size
    }
  }
}

trait ClusterGenerator extends WordHintsGenerator {
  def generateUniquePatterns(potentialAnswer: XrdleWord, wordSet: WordSet): Set[WordHints] = {
    wordSet.map { word: XrdleWord => generateWordHints(potentialAnswer, word) }
  }
}
