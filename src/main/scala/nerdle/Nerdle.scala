package com.skidis.wordle
package nerdle

import BlockColor.Green
import input.{GuessInput, ResultInput}
import strategy.ClusterStrategy

import scala.io.StdIn

object Nerdle extends App {
  val wordleNumber = if (args.length > 1) args(1) else "Unknown"

  val startEquation = "3+9-2=10"
  val equations = NerdleGuessableGenerator.generatateEquations()
  val processor = new InteractiveNerdleProcessor with ClusterStrategy
  val result = processor.process(equations, startEquation)

  if (result.isEmpty) println("Process Aborted By User")
  else printWordleBlock(result)

  def printWordleBlock(result: List[(String, ColorPattern)]): Unit = {
    println(List.fill(40)('*').mkString)
    println()
    println(s"Nerdle $wordleNumber ${if (result.size <= 6) result.size else "X"}/6*")
    println()
    result.foreach { case (_, colorPattern) => println(colorPattern.mkString) }
    println()
  }

  trait InteractiveNerdleProcessor extends XordleProcessor
    with GuessInput with ResultInput with NerdleGuessValidator with NerdleResultValidator {

    override def winningColorPattern: ColorPattern = List.fill(8)(Green)
    override def readLine(): String = StdIn.readLine()
    override def writeLine(s: String): Unit = Console.println(s)
    override def writeString(s: String): Unit = Console.print(s)
    override def retrieveGuess(suggestion: String): String = getGuessFromInput(suggestion)
    override def retrieveColorPattern(guess: String): ColorPattern = generatePattern()
  }
}
