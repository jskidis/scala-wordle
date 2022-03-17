package com.skidis.wordle
package strategy

trait WordFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {
  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    scoreWord(remainingWords)
  }

  def scoreWord(remainingWords: WordSet)(potentialAnswer: XrdleWord): Double = {
    potentialAnswer match { case wf: XrdleFreqWord => wf.frequency }
  }
}

object WordFreqStrategy extends WordFreqStrategy