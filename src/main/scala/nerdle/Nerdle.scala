package com.skidis.wordle
package nerdle

import input.{GuessInput, ResultInput}
import strategy.ClusterStrategy

import scala.io.StdIn

object Nerdle extends App {
  val wordleNumber = if (args.length > 1) args(1) else "Unknown"

  val startEquation = "58-42=16"
  val equations = NerdleGuessableGenerator.generatateEquations()
  val processor = new InteractiveNerdleProcessor with ClusterStrategy
  val result = processor.process(equations, startEquation)

  if (result.isEmpty) println("Process Aborted By User")
  else printWordleBlock(result)

  def printWordleBlock(result: List[(String, WordHints)]): Unit = {
    println(List.fill(40)('*').mkString)
    println()
    println(s"nerdlegame $wordleNumber ${if (result.size <= 6) result.size else "X"}/6")
    println()
    result.foreach { case (_, colorPattern) => println(colorPattern.mkString) }
    println()
  }

  trait InteractiveNerdleProcessor extends XordleProcessor
    with GuessInput with ResultInput with NerdleGuessValidator with NerdleResultValidator {

    override def hintProps: HintProps = NerdleHintProps
    override def winningColorPattern: WordHints = List.fill(8)(hintProps.inPosHint)
    override def readLine(): String = StdIn.readLine()
    override def writeLine(s: String): Unit = Console.println(s)
    override def writeString(s: String): Unit = Console.print(s)
    override def retrieveGuess(suggestion: String): String = getGuessFromInput(suggestion)
    override def retrieveColorPattern(guess: String): WordHints = generatePattern(hintProps)
  }
}
