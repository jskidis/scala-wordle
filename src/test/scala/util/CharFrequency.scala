package com.skidis.wordle
package util

import strategy.{CharFreqStrategy, WordScore}
import wordle.WordleMissHint

import scala.io.Source

object CharFrequency extends App with WordReader with CharFreqStrategy {
  val wordSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-answers.txt"))

  val firstRoundGuesses = scoreRound(wordSet).take(10)
  val firstGuess = firstRoundGuesses(0)
  val firstGuessPattern = firstGuess.word.text.map{ ch => (ch, WordleMissHint) }
  val secondGuessWordSet = wordSet.filter { w: XrdleWord =>
    WordPatternMatcher.doesWordMatch(w.text, firstGuessPattern)
  }

  val secondRoundGuesses = scoreRound(secondGuessWordSet).take(10)

  firstRoundGuesses.foreach(ws => println(f"${ws.word.text} - ${ws.score}%5.3f"))
  println()
  secondRoundGuesses.foreach(ws => println(f"${ws.word.text} - ${ws.score}%5.3f"))

  def scoreRound(wordSet: WordSet): Vector[WordScore] = {
    val scoreFunction = scoreWordFunction(wordSet)
    generateSortedWordScores(scoreFunction, wordSet)
  }
}
