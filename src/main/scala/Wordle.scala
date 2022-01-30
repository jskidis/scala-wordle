package com.skidis.wordle

import scala.io.{Source, StdIn}

object Wordle extends App {
  val initialCandidate = "TRACE"
  val candidateWords = WordReader(Source.fromResource("words.txt"))


  val colorPattern = GatherResult(StdIn.readLine, Console.println, InputValidator.apply)
  println(colorPattern match {
    case Nil => "Process Aborted By User"
    case _ => colorPattern.mkString(", ")
  })

}
