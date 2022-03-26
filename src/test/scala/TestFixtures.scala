package com.skidis.wordle

import org.scalatest.Tag

import scala.collection.mutable.ListBuffer

object TestFixtures {
  val inPosChar: Char = TInPosHint.inputChar
  val inWordChar: Char = TInWordHint.inputChar
  val missChar: Char = TMissHint.inputChar

  object TInPosHint extends InPosHint {
    override def inputChar: Char = 'G'
    override def colorBlock: String = "\uD83D\uDFE9"
  }

  object TInWordHint extends InWordHint {
    override def inputChar: Char = 'Y'
    override def colorBlock: String = "\uD83D\uDFE8"
  }

  object TMissHint extends MissHint {
    override def inputChar: Char = 'B'
    override def colorBlock: String = "\u2B1C"
  }

  trait TestHintProps extends HintProps {
    override def inPosHint: InPosHint = TInPosHint
    override def inWordHint: InWordHint = TInWordHint
    override def missHint: MissHint = TMissHint
  }
  object TestHintProps extends TestHintProps

  trait TestGuessProps extends GuessProps {
    override def guessWordLength: Int = 5
    override def maxGuesses: Int = 6
    override def validGuessChars: Set[Char] = (('a' to 'z') ++ ('A' to 'Z')).toSet
    override def invalidGuessCharError: String = "Invalid Characters"
  }
  object TestGuessProps extends TestGuessProps

  case class TWord(text: String) extends XrdleWord
}

trait WriterToBuffer extends Writer {
  var linesWritten:ListBuffer[String] = new ListBuffer[String]()
  override def writeLine(s: String): Unit = linesWritten.addOne(s)
  override def writeString(s: String): Unit = linesWritten.addOne(s)
}

object IntegrationTest extends Tag("IntegrationTest")
