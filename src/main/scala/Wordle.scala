package com.skidis.wordle

import scala.io.{Source, StdIn}

object Wordle extends App with WordReader {
  case class Parameters(startWord: String, strategy: SolveStrategy, wordSet: WordSet)

  val parameters = (if(args.length > 0) args(0) else "") match {
    case s if s == "answer-only" => Parameters("SLATE", ClusterStrategy,
      readWords(Source.fromResource("answers.txt")))
    case s if s == "reverse" => Parameters("JAZZY", ReverseClusterStrategy,
      readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt")))
    case _ => Parameters("SLATE", ClusterStrategy,
      readWordFrequencies(Source.fromResource("word-frequency-filtered.txt")))
  }

  val wordleNumber = if (args.length > 1) args(1) else "Unknown"

  val result = WordleProcessor.process(
    parameters.strategy, ManualInput.retrieveGuess, ManualInput.retrievePattern
  )(parameters.wordSet, parameters.startWord)

  if (result.isEmpty) println("Process Aborted By User")
  else printWordleBlock(result)

  def printWordleBlock(result: List[(String, ColorPattern)]): Unit = {
    println(List.fill(40)('*').mkString)
    println()
    println(s"Wordle $wordleNumber ${if(result.size <=6) result.size else "X"}/6")
    println()
    result.foreach { case(_, colorPattern) => println(colorPattern.mkString) }
    println()
  }
}

object ManualInput extends GuessInput with ResultInput with GuessValidator with ResultValidator {
  override def readLine(): String = StdIn.readLine()
  override def writeLine(s: String): Unit = Console.print(s)

  def retrieveGuess(suggestion: String): String = {
    getGuessFromInput(suggestion)
  }

  def retrievePattern(guess: String): ColorPattern = {
    generatePattern()
  }
}
