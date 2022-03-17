package com.skidis.wordle
package strategy

trait ReverseClusterStrategy extends ClusterStrategy {

  override def scoreWord(remainingWords: WordSet)(potentialAnswer: XordlePhrase): Double = {
    -super.scoreWord(remainingWords)(potentialAnswer)
  }
}

trait ReverseClusterStrategyCaching extends ReverseClusterStrategy with CachingWordHintsGenerator
