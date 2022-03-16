package com.skidis.wordle
package strategy

trait ReverseClusterStrategy extends ClusterStrategy {

  override def scoreWord(potentialAnswer: XordlePhrase, remainingWords: WordSet): Double =
    -super.scoreWord(potentialAnswer, remainingWords)
}

trait ReverseClusterStrategyCaching extends ReverseClusterStrategy with CachingWordHintsGenerator
