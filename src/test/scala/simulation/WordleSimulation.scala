package com.skidis.wordle
package simulation

import strategy.{ClusterAndFreqStrategy, ReverseClusterStrategy}
import wordle.WordleHintProps

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.io.Source

object WordleSimulation extends App with WordReader with SimResultsPrinter {
  case class Parameters(startWord: String, wordSet: WordSet, processorFactory: String => SimulationProcessor)

  val parameters = (if (args.length > 0) args(0) else "") match {
    case s if s == "answer-only" => Parameters("SLATE",
      readWords(Source.fromResource("answers.txt")),
      (answer: String) => new SimulationProcessor(answer, WordleHintProps) with ClusterAndFreqStrategy
    )
    case s if s == "reverse" => Parameters("JAZZY",
      readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt")),
      (answer: String) => new SimulationProcessor(answer, WordleHintProps) with ReverseClusterStrategy
    )
    case _ => Parameters("SLATE",
      readWordFrequencies(Source.fromResource("word-frequency-filtered.txt")),
      (answer: String) => new SimulationProcessor(answer, WordleHintProps) with ClusterAndFreqStrategy
    )
  }

  val answers = readWords(Source.fromResource("answers.txt"))
  val startTimestamp = System.currentTimeMillis()

  val results: Vector[Seq[(String, WordHints)]] = Await.result(
    Future.sequence(answers.map(answer => runWordle(answer.phrase))), 1.hour
  ).toVector
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

  def runWordle(answer: String): Future[Seq[(String, WordHints)]] = Future {
    val processor = parameters.processorFactory(answer)
    val result = processor.process(parameters.wordSet, parameters.startWord)
    //    println("Processed")
    result
  }
}
