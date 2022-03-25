package com.skidis.wordle
package strategy

trait CharAndWordFreqStrategy extends CharFreqStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val baseScoreFunction = super.scoreWordFunction(remainingWords)
    (word: XrdleWord) => {
      baseScoreFunction(word) +
        Math.log10(WordFreqStrategy.scoreWord(remainingWords)(word)) *10
    }
  }
}

object CharAndWordFreqStrategy extends CharAndWordFreqStrategy
