package com.skidis.wordle

import scala.io.Source

object Wordle extends App {
  val candidateWords = WordReader.read(Source.fromResource("guessable-words.txt"))
  val result = WordleProcessor.process(ResultInput.generatePatternCurryable())(candidateWords)

  println(result match {
    case None => "Process Aborted By User"
    case Some((word, num)) => s"Correct Answer: $word was guessed in $num guesses"
  })
}
