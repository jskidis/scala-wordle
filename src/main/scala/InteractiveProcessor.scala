package com.skidis.wordle

import input.{GuessInput, ResultInput}

import scala.io.StdIn

trait InteractiveProcessor extends XordleProcessor
  with GuessInput with ResultInput {

  override def readLine(): String = StdIn.readLine()
  override def writeLine(s: String): Unit = Console.println(s)
  override def writeString(s: String): Unit = Console.print(s)
  override def retrieveGuess(suggestion: String): String = getGuessFromInput(suggestion)
  override def retrieveWordHints(guess: String): WordHints = generatePattern()
}



