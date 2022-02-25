package com.skidis.wordle
package wordle

import BlockColor.Green
import input.{GuessInput, ResultInput}
import strategy.{ClusterAndFreqStrategy, ReverseClusterStrategy}

import scala.io.{Source, StdIn}

object Wordle extends App with WordReader {
  case class Parameters(startWord: String, wordSet: WordSet, wordleProcessor: XordleProcessor)

  val parameters = (if (args.length > 0) args(0) else "") match {
    case s if s == "answer-only" => Parameters("SLATE",
      readWords(Source.fromResource("answers.txt")),
      new InteractiveWordleProcessor with ClusterAndFreqStrategy
    )
    case s if s == "reverse" => Parameters("JAZZY",
      readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt")),
      new InteractiveWordleProcessor with ReverseClusterStrategy
    )
    case _ => Parameters("SLATE",
      readWordFrequencies(Source.fromResource("word-frequency-filtered.txt")),
      new InteractiveWordleProcessor with ClusterAndFreqStrategy
    )
  }
  val wordleNumber = if (args.length > 1) args(1) else "Unknown"

  val result = parameters.wordleProcessor.process(parameters.wordSet, parameters.startWord)

  if (result.isEmpty) println("Process Aborted By User")
  else printWordleBlock(result)

  def printWordleBlock(result: List[(String, ColorPattern)]): Unit = {
    println(List.fill(40)('*').mkString)
    println()
    println(s"Wordle $wordleNumber ${if (result.size <= 6) result.size else "X"}/6*")
    println()
    result.foreach { case (_, colorPattern) => println(colorPattern.mkString) }
    println()
  }

  trait InteractiveWordleProcessor extends XordleProcessor
    with GuessInput with ResultInput with WordleGuessValidator with WordleResulValidator {

    override def winningColorPattern: ColorPattern = List.fill(5)(Green)
    override def readLine(): String = StdIn.readLine()
    override def writeLine(s: String): Unit = Console.println(s)
    override def writeString(s: String): Unit = Console.print(s)
    override def retrieveGuess(suggestion: String): String = getGuessFromInput(suggestion)
    override def retrieveColorPattern(guess: String): ColorPattern = generatePattern()
  }
}
