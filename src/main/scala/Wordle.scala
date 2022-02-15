package com.skidis.wordle

import scala.io.Source

object Wordle extends App {
  val wordleNumber = if (args.length > 0) args.head else "Unknown"

//  val candidateWords = WordReader.readWords(Source.fromResource("answers.txt"))
  val candidateWords = WordReader.readWordFrequencies(Source.fromResource("word-frequency.txt"))
  val result = WordleProcessor.process(
    ClusterWithFrequencyStrategy, ResultInput.generatePatternCurryable())(candidateWords)

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
