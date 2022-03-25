package com.skidis.wordle
package strategy

trait CharAndWordFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val charFreqScoreFunc = charFreqStrategy.scoreWordFunction(remainingWords)
    val wordFreqScoreFunc = wordFreqStrategy.scoreWordFunction(remainingWords)
    (word: XrdleWord) => charFreqScoreFunc(word) + wordFreqScoreFunc(word) *10
  }

  def charFreqStrategy: CharFreqStrategy = CharFreqStrategy
  def wordFreqStrategy: WordFreqStrategy = WordFreqStrategy
}

object CharAndWordFreqStrategy extends CharAndWordFreqStrategy
