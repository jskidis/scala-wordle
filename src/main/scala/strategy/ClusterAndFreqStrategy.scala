package com.skidis.wordle
package strategy

trait ClusterAndFreqStrategy extends ClusterStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val baseScoreFunction = super.scoreWordFunction(remainingWords)
    (word: XrdleWord) => {
      baseScoreFunction(word) +
        Math.log10(WordFreqStrategy.scoreWord(remainingWords)(word)) *2
    }
  }
}

trait ClusterAndFreqStrategyCaching extends ClusterAndFreqStrategy with CachingWordHintsGenerator
