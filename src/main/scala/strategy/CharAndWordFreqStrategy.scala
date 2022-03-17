package com.skidis.wordle
package strategy

trait CharAndWordFreqStrategy extends CharFreqStrategy {

  override def scoreWord(charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int], remainingWords: WordSet)
    (potentialAnswer: XrdleWord): Double = {

    super.scoreWord(charFreqMap, charIdxFreqMap, remainingWords)(potentialAnswer) +
      Math.log10(WordFreqStrategy.scoreWord(remainingWords)(potentialAnswer))*10
  }
}
