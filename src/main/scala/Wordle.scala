package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green}

import scala.io.Source

object Wordle extends App {
  val currentGuess = "TRACE"
  val candidateWords = WordReader(Source.fromResource("words.txt"))

//  val colorPattern = GatherResult(StdIn.readLine, Console.println, InputValidator.apply)
  val colorPattern = List(Green, Green, Black, Black, Green)
  println(colorPattern match {
    case Nil => "Process Aborted By User"
    case _ => colorPattern.mkString(", ")
  })

  val wordPattern: List[(Char, BlockColor)] = currentGuess.toList zip colorPattern

//  val matchingDetail = GenerateMatchingDetail(currentGuess, colorPattern)
  val remainingWords = candidateWords.filter(WordMatchesWordPattern(_, wordPattern))

  remainingWords foreach { println }
}
