package com.skidis.wordle

import scala.io.Source

object WordleProcessorIntegrationTest extends App {
  val candidateWords = WordReader.read(Source.fromResource("words.txt"))

  val results: Set[Option[(String, Int)]] = candidateWords.map { answer =>
    val colorPatternGenerator: ColorPatternGenerator = WordColorPatternGenerator.generateCurryable(answer)
    WordleProcessor.process(colorPatternGenerator, debugOutput = false)(candidateWords)
  }

  val groupedByGuesses: List[(Int, Set[Option[(String, Int)]])] = results.groupBy{
    case None => -1
    case Some((_, numGuesses)) => numGuesses
  }.toList.sortWith(_._1 < _._1)

  groupedByGuesses.foreach{
    case (numGuesses, results) => println(s"$numGuesses Guesses: ${results.size}")
  }
}
