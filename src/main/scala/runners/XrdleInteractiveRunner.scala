package com.skidis.wordle
package runners

import scala.io.StdIn

trait XrdleInteractiveRunner extends XrdleRunner with InteractiveProcessorFactory with ConsoleWriter {
  def runInteractive(): Either[String, Seq[(String, WordHints)]] = {
    val processor = createInteractiveProcessor()
    processor.process(guessSet)
  }

  def printWordleBlock(results: Either[String, Seq[(String, WordHints)]], maxGuesses: Int): Unit = {
    results match {
      case Left(message) => writeLine(s"STOPPED: $message")
      case Right(result) =>
        writeLine(Seq.fill(40)('*').mkString)
        val wordleNumber = inputPuzzleNumber()

        writeLine("")
        writeLine(s"$puzzleName $wordleNumber ${if (result.size <= maxGuesses) result.size else "X"}/$maxGuesses${if (hardMode) "*" else ""}")
        result.foreach { case (_, wordHints) => writeLine(wordHints.mkString) }

        writeLine("")
        result.foreach { case (guess, _) => writeLine(guess)}
    }
  }

  def inputPuzzleNumber(): String = {
    writeString("Enter Puzzle Number: ")
    StdIn.readLine()
  }
}
