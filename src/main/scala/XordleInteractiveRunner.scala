package com.skidis.wordle

import scala.io.StdIn

trait XordleInteractiveRunner extends XordleRunner {
  def runInteractive(): Unit = {
    val wordleNumber = inputPuzzleNumber()
    val processor = createStaticProcessor()
    val result = processor.process(guessSet, startPhrase)

    if (result.isEmpty) println("Process Aborted By User")
    else printWordleBlock(result, wordleNumber, processor.maxGuesses)
  }

  def printWordleBlock(result: Seq[(String, WordHints)], wordleNumber: String, maxGuesses: Int): Unit = {
    println(Seq.fill(40)('*').mkString)
    println()
    println(s"$puzzleName $wordleNumber ${if (result.size <= maxGuesses) result.size else "X"}/6*")
    println()
    result.foreach { case (_, wordHints) => println(wordHints.mkString) }
    println()
  }

  def inputPuzzleNumber(): String = {
    print("Enter Puzzle Number: ")
    StdIn.readLine()
  }
}
