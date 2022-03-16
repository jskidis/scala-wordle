package com.skidis.wordle
package strategy

trait CharAndWordFreqStrategy extends CharFreqStrategy {
  override def scoreWord(charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int])
    (potentialAnswer: XordlePhrase, remainingWords: WordSet)
  : Double = {
    val charFreqScore = super.scoreWord(charFreqMap, charIdxFreqMap)(potentialAnswer, remainingWords)
    potentialAnswer match {
      case wf: XordlePhaseFreq => charFreqScore + Math.log10(wf.frequency)*2
    }
  }
}
