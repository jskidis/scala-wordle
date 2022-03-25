package com.skidis.wordle
package runners

import scala.io.StdIn

trait XrdleInteractiveRunner extends XrdleRunner with InteractiveProcessorFactory {
  def runInteractive(): Unit = {
    val wordleNumber = inputPuzzleNumber()
    val processor = createInteractiveProcessor()

    processor.process(guessSet) match {
      case Left(message) => println(s"STOPPED: $message")
      case Right(result) => printWordleBlock(result, wordleNumber, processor.maxGuesses)
    }
  }

  def printWordleBlock(result: Seq[(String, WordHints)], wordleNumber: String, maxGuesses: Int): Unit = {
    println(Seq.fill(40)('*').mkString)
    println(s"$puzzleName $wordleNumber ${if (result.size <= maxGuesses) result.size else "X"}/6${if (hardMode) "*" else ""}")
    result.foreach { case (_, wordHints) => println(wordHints.mkString) }
    println()
    result.foreach { case (guess, _) => println(guess)}
  }

  def inputPuzzleNumber(): String = {
    print("Enter Puzzle Number: ")
    StdIn.readLine()
  }
}
