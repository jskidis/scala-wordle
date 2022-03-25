package com.skidis.wordle
package strategy

trait CharAndWordFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val charFreqScoreFunc = charFreqStrategy.scoreWordFunction(remainingWords)
    val wordFreqScoreFunc = wordFreqStrategy.scoreWordFunction(remainingWords)
    (word: XrdleWord) => charFreqScoreFunc(word) + wordFreqScoreFunc(word) *10
  }

  lazy val charFreqStrategy: CharFreqStrategy = CharFreqStrategy
  lazy val wordFreqStrategy: WordFreqStrategy = WordFreqStrategy
}

object CharAndWordFreqStrategy extends CharAndWordFreqStrategy
