package com.skidis.wordle
package nerdle
/*
object Nerdle extends App {
  val wordleNumber = if (args.length > 1) args(1) else "Unknown"

  val startEquation = "58-42=16"
  val equations = NerdleGuessableGenerator.generatateEquations()
  val processor = new InteractiveNerdleProcessor with ClusterStrategy with NerdleInputValidator
  val result = processor.process(equations, startEquation)

  if (result.isEmpty) println("Process Aborted By User")
  else printWordleBlock(result)

  def printWordleBlock(result: Seq[(String, WordHints)]): Unit = {
    println(Seq.fill(40)('*').mkString)
    println()
    println(s"nerdlegame $wordleNumber ${if (result.size <= 6) result.size else "X"}/6")
    println()
    result.foreach { case (_, wordHints) => println(wordHints.mkString) }
    println()
  }

  trait InteractiveNerdleProcessor extends XordleProcessor with NerdleInputValidator
    with GuessInput with ResultInput with NerdleGuessProps with NerdleHintProps {

    override def readLine(): String = StdIn.readLine()
    override def writeLine(s: String): Unit = Console.println(s)
    override def writeString(s: String): Unit = Console.print(s)
    override def retrieveGuess(suggestion: String): String = getGuessFromInput(suggestion)
    override def retrieveWordHints(guess: String): WordHints = generatePattern()
  }
}
 */
