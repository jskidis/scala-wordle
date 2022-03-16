package com.skidis.wordle
package strategy

trait ClusterAndFreqStrategy extends ClusterStrategy {

  override def scoreWord(potentialAnswer: XordlePhrase, remainingWords: WordSet): Double = {
    val clusterScore = super.scoreWord(potentialAnswer, remainingWords)
    potentialAnswer match {
      case wf: XordlePhaseFreq => clusterScore + Math.log10(wf.frequency)*2
    }
  }
}

trait ClusterAndFreqStrategyCaching extends ClusterAndFreqStrategy with CachingWordHintsGenerator
