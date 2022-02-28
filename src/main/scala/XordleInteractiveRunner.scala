package com.skidis.wordle

import scala.io.StdIn

trait XordleInteractiveRunner extends XordleRunner {
  def runInteractive(): Unit = {
    val wordleNumber = inputPuzzleNumber()
    val processor = createInteractiveProcessor()
    val result = processor.process(guessSet, startGuess)

    if (result.isEmpty) println("Process aborted by user, or failure occurred")
    else printWordleBlock(result, wordleNumber, processor.maxGuesses)
  }

  def printWordleBlock(result: Seq[(String, WordHints)], wordleNumber: String, maxGuesses: Int): Unit = {
    println(Seq.fill(40)('*').mkString)
    println(s"$puzzleName $wordleNumber ${if (result.size <= maxGuesses) result.size else "X"}/6*")
    result.foreach { case (_, wordHints) => println(wordHints.mkString) }
    println()
    result.foreach { case (guess, _) => println(guess)}
  }

  def inputPuzzleNumber(): String = {
    print("Enter Puzzle Number: ")
    StdIn.readLine()
  }
}
