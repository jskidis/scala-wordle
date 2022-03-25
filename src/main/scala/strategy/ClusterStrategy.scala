package com.skidis.wordle
package strategy

import hintgen.{CachingWordHintsGenerator, WordHintsGenerator}

trait ClusterStrategy extends WordScoringStrategy with HardModeWordElimStrategy with ClusterGenerator {
  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    (word: XrdleWord) => {
      val uniquePatterns = generateUniquePatterns(word, remainingWords)
      uniquePatterns.size
    }
  }
}

object ClusterStrategy extends ClusterStrategy

trait ClusterStrategyCaching extends ClusterStrategy with CachingWordHintsGenerator

trait ClusterGenerator extends WordHintsGenerator with GenericHintProps {
  def generateUniquePatterns(potentialAnswer: XrdleWord, wordSet: WordSet): Set[WordHints] = {
    wordSet.map { word: XrdleWord => generateWordHints(potentialAnswer, word) }
  }
}

object ClusterGenerator extends ClusterGenerator
