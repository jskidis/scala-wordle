package com.skidis.wordle
package strategy

trait WordFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {
  override def scoreWordFunction(remainingWords: WordSet): XordlePhrase => Double = {
    scoreWord(remainingWords)
  }

  def scoreWord(remainingWords: WordSet)(potentialAnswer: XordlePhrase): Double = {
    potentialAnswer match { case wf: XordlePhaseFreq => wf.frequency }
  }
}

object WordFreqStrategy extends WordFreqStrategy