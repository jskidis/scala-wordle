package com.skidis.wordle

trait SolveStrategy {
  def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: ColorPattern): WordSet
  def generateNextGuess(remainingWords: WordSet): (String, String)
}
