package com.skidis.wordle

import scala.io.Source

object WordleProcessorIntegrationTest extends App {
  val candidateWords = WordReader.read(Source.fromResource("guessable-words.txt"))
  val answers = WordReader.read(Source.fromResource("answers.txt"))

  val startTimestamp = System.currentTimeMillis()
  val results: Set[Option[(String, Int)]] = answers.map { answer =>
    val colorPatternGenerator: ColorPatternGenerator = WordColorPatternGenerator.generateCurryable(answer)
    WordleProcessor.process(colorPatternGenerator, debugOutput = false)(candidateWords)
  }

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp)/1000}")

  val groupedByGuesses: List[(Int, Set[Option[(String, Int)]])] = results.groupBy{
    case None => -1
    case Some((_, numGuesses)) => numGuesses
  }.toList.sortWith(_._1 < _._1)

  groupedByGuesses.foreach{
    case (numGuesses, results) => println(s"$numGuesses Guesses: ${results.size}")
  }
}
