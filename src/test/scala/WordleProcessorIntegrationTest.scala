package com.skidis.wordle

import BlockColor.BlockColor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.io.Source

object WordleProcessorIntegrationTest extends App with WordReader {
  val answers = readWords(Source.fromResource("answers.txt")).take(100)

  val candidateWords = readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt"))
//  val candidateWords = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
//  val candidateWords = readWords(Source.fromResource("answers.txt"))

  val strategy = ReverseClusterStrategy
  val firstWord = "JAZZY"

  val startTimestamp = System.currentTimeMillis()

  val results: List[List[(String, List[BlockColor])]] = Await.result(
    Future.sequence(answers.map(w=> runWordle(w.string))), 1.hour
  ).toList
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp)/1000}")

  def dummyLineWriter(line: String): Unit = {}
  def guessGatherer(suggestion: String): String = suggestion

  def runWordle(answer: String): Future[List[(String, List[BlockColor])]] = Future {
    val colorPatternGenerator: ColorPatternGenerator = WordColorPatternGenerator.generateColorPatternCurryable(answer)
    val result = WordleProcessor.process(
      strategy, guessGatherer, colorPatternGenerator, dummyLineWriter
    )(candidateWords, firstWord)
//    println("Processed")
    result
  }

  def printResults(results: List[List[(String, List[BlockColor])]]): Unit = {
    val groupedByGuesses = results.groupBy{
      case Nil => -1
      case result => result.size
    }.toList.sortWith(_._1 < _._1)

    groupedByGuesses.foreach{
      case (numGuesses, results) => println(s"$numGuesses Guesses: ${results.size}")
    }
  }
}
