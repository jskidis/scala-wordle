package com.skidis.wordle

import BlockColor.BlockColor

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.io.Source

object WordleProcessorIntegrationTest extends App with WordReader {
  case class Parameters(startWord: String, wordSet: WordSet, processorFactory: String => SimulationWordleProcessor)

  val parameters = (if(args.length > 0) args(0) else "") match {
    case s if s == "answer-only" => Parameters("SLATE",
      readWords(Source.fromResource("answers.txt")),
      (answer:String) => new SimulationWordleProcessor(answer) with ClusterStrategy
    )
    case s if s == "reverse" => Parameters("JAZZY",
      readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt")),
      (answer:String) => new SimulationWordleProcessor(answer) with ReverseClusterStrategy
    )
    case _ => Parameters("SLATE",
      readWordFrequencies(Source.fromResource("word-frequency-filtered.txt")),
      (answer:String) => new SimulationWordleProcessor(answer) with ClusterStrategy
    )
  }

  val answers = readWords(Source.fromResource("answers.txt"))
  val startTimestamp = System.currentTimeMillis()

  val results: List[List[(String, List[BlockColor])]] = Await.result(
    Future.sequence(answers.map(answer => runWordle(answer.string))), 1.hour
  ).toList
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp)/1000}")

  def runWordle(answer: String): Future[List[(String, List[BlockColor])]] = Future {
    val processor = parameters.processorFactory(answer)
    val result = processor.process(parameters.wordSet, parameters.startWord)
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

trait CachingWordColorPatternGenerator extends WordColorPatternGenerator {
  val patternCache: mutable.Map[(WordleWord, WordleWord), ColorPattern] =
    collection.mutable.Map[(WordleWord, WordleWord), ColorPattern]()

  override def generateWordColorPattern(answer: WordleWord, word: WordleWord): ColorPattern = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordColorPattern(answer, word))
  }
}

abstract class SimulationWordleProcessor(answer: String) extends WordleProcessor with CachingWordColorPatternGenerator {
  override def retrieveColorPattern(guess: String): ColorPattern = generateStringColorPattern(answer, guess)
  override def retrieveGuess(suggestion: String): String = suggestion
  override def writeLine(line: String): Unit = {}
  override def writeString(s: String): Unit = {}
}