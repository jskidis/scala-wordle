package com.skidis.wordle

import BlockColor.BlockColor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.io.Source

object WordleProcessorIntegrationTest extends App {
  val candidateWords = WordReader.readWords(Source.fromResource("guessable-words-filtered.txt"))
//  val candidateWords = WordReader.readWords(Source.fromResource("answers.txt"))
  val answers = WordReader.readWords(Source.fromResource("answers.txt"))

  val startTimestamp = System.currentTimeMillis()

  val results: Set[List[(String, List[BlockColor])]] = Await.result(
    Future.sequence(answers.map(w=> runWordle(w.wordString()))), 10.minutes
  )
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp)/1000}")

  def runWordle(answer: String): Future[List[(String, List[BlockColor])]] = Future {
    val colorPatternGenerator: ColorPatternGenerator = WordColorPatternGenerator.generateCurryable(answer)
    WordleProcessor.process(colorPatternGenerator, debugOutput = false)(candidateWords)
  }

  def printResults(results: Set[List[(String, List[BlockColor])]]): Unit = {
    val groupedByGuesses: List[(Int, Set[List[(String, List[BlockColor])]])] = results.groupBy{
      case Nil => -1
      case result => result.size
    }.toList.sortWith(_._1 < _._1)

    groupedByGuesses.foreach{
      case (numGuesses, results) => println(s"$numGuesses Guesses: ${results.size}")
    }
  }
}
