package com.skidis.wordle

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.io.Source

object WordleProcessorIntegrationTest extends App {
  val candidateWords = WordReader.read(Source.fromResource("guessable-words.txt"))
  val answers = WordReader.read(Source.fromResource("answers.txt"))

  val startTimestamp = System.currentTimeMillis()

  val results: Set[Option[(String, Int)]] = Await.result(Future.sequence(answers.map(runWordle)), 10.minutes)
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp)/1000}")

  def runWordle(answer: String): Future[Option[(String, Int)]] = Future {
    val colorPatternGenerator: ColorPatternGenerator = WordColorPatternGenerator.generateCurryable(answer)
    WordleProcessor.process(colorPatternGenerator, debugOutput = false)(candidateWords)
  }

  def printResults(results: Set[Option[(String, Int)]]): Unit = {
    val groupedByGuesses: List[(Int, Set[Option[(String, Int)]])] = results.groupBy{
      case None => -1
      case Some((_, numGuesses)) => numGuesses
    }.toList.sortWith(_._1 < _._1)

    groupedByGuesses.foreach{
      case (numGuesses, results) => println(s"$numGuesses Guesses: ${results.size}")
    }
  }
}
