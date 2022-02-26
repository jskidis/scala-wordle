package com.skidis.wordle
package simulation

import strategy.{ClusterAndFreqStrategy, ReverseClusterStrategy}
import wordle.WordleHintProps

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.io.Source

object WordleSimulation extends App with WordReader with SimResultsPrinter {
  case class Parameters(startWord: String, wordSet: WordSet,
    processorFactory: String => SimulationProcessor, maxSimulations: Int = Int.MaxValue)

  val answerOnlyParams = Parameters("SLATE",
    readWords(Source.fromResource("answers.txt")),
    (answer: String) => new SimulationProcessor(answer, WordleHintProps) with ClusterAndFreqStrategy
  )

  val reverseStratParam = Parameters("JAZZY",
    readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt")),
    (answer: String) => new SimulationProcessor(answer, WordleHintProps) with ReverseClusterStrategy
  )

  val standardParams = Parameters("SLATE",
    readWordFrequencies(Source.fromResource("word-frequency-filtered.txt")),
    (answer: String) => new SimulationProcessor(answer, WordleHintProps) with ClusterAndFreqStrategy
  )

  val numSimulations = if (args.length > 1) args(1).toInt else Int.MaxValue

  val parameters = ((if (args.length > 0) args(0) else "") match {
    case s if s == "answer-only" => answerOnlyParams
    case s if s == "reverse" => reverseStratParam
    case _ => standardParams
  }).copy(maxSimulations = numSimulations)


  runSimulation(parameters)

  def runSimulation(parameters: Parameters = standardParams): Unit = {
    val answers = readWords(Source.fromResource("answers.txt")).take(parameters.maxSimulations)
    val startTimestamp = System.currentTimeMillis()

    val results: Vector[Seq[(String, WordHints)]] = Await.result(
      Future.sequence(answers.map(answer => runWordle(answer.phrase, parameters))), 1.hour
    ).toVector
    printResults(results)

    val endTimestamp = System.currentTimeMillis()
    println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")
  }

  def runWordle(answer: String, parameters: Parameters): Future[Seq[(String, WordHints)]] = Future {
    val processor = parameters.processorFactory(answer)
    val result = processor.process(parameters.wordSet, parameters.startWord)
    //    println("Processed")
    result
  }
}
