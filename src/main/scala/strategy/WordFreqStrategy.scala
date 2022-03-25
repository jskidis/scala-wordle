package com.skidis.wordle
package strategy

trait WordFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {
  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    case wf: XrdleFreqWord => Math.log10(wf.frequency)
  }
}

object WordFreqStrategy extends WordFreqStrategy