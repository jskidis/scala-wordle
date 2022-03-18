package com.skidis.wordle

import scala.io.BufferedSource

trait MultiSimulationRunner extends XrdleSimulationRunner {
  def simulateFirstGuesses(wordSet: WordSet, strategy: SolveStrategy, numToTry: Int): Unit = {
    val sorted = strategy.generateNextGuesses(wordSet, Nil, numToTry)
    val startingGuessesSet = sorted.map { w => Seq(w) }

    val results = runMultipleSimulations(startingGuessesSet)
    results.sortBy(_._2).foreach { case (guesses: Seq[String], avgGuesses: Double) =>
      println(f"Words: ${guesses.mkString(",")} - Avg Guesses: $avgGuesses%5.3f")
    }
  }

  def simulateFirstTwoGuesses(wordSet: WordSet, wordSource: BufferedSource, strategy: SolveStrategy, numToTry: Int, missHint: MissHint): Unit = {
    val sorted = strategy.generateNextGuesses(wordSet, Nil, numToTry)
    val startingGuessesSet: Seq[Seq[String]] = for {
      firstGuess <- sorted
      secondGuess <- {
        val firstGuessPattern = firstGuess.map { ch => (ch, missHint) }
        val secondGuessWordSet = wordSet.filter { secondGuess: XrdleWord =>
          WordPatternMatcher.doesWordMatch(secondGuess.text, firstGuessPattern)
        }
        strategy.generateNextGuesses(secondGuessWordSet, Nil, numToTry / 2)
      }
    } yield Seq(firstGuess, secondGuess)

    val results = runMultipleSimulations(startingGuessesSet)
    results.sortBy(_._2).foreach { case (guesses: Seq[String], avgGuesses: Double) =>
      println(f"Words: ${guesses.mkString(",")} - Avg Guesses: $avgGuesses%5.3f")
    }
  }
}
