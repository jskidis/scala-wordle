package com.skidis.wordle
package strategy

trait ClusterAndFreqStrategy extends ClusterStrategy {

  override def scoreWord(remainingWords: WordSet)(potentialAnswer: XordlePhrase): Double = {
    super.scoreWord(remainingWords)(potentialAnswer) +
      Math.log10(WordFreqStrategy.scoreWord(remainingWords)(potentialAnswer)) *2
  }
}

trait ClusterAndFreqStrategyCaching extends ClusterAndFreqStrategy with CachingWordHintsGenerator
