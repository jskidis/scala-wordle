package com.skidis

import scala.io.StdIn

package object wordle {
  type WordHints = Seq[HintBlock]
  type WordSet = Set[_ <: XordlePhrase]

  trait XordlePhrase extends Ordered[XordlePhrase] {
    def phrase: String
    override def compare(that: XordlePhrase): Int = phrase.compare(that.phrase)
  }

  trait XordlePhaseFreq extends XordlePhrase {
    def frequency: Double
    override def compare(that: XordlePhrase): Int = that match {
      case w2: XordlePhaseFreq => frequency.compare(w2.frequency)
      case _ => super.compare(that)
    }
  }

  trait HintBlock {
    def inputChar: Char
    def colorBlock: String
    override def toString: String = colorBlock
  }

  trait InPosHint extends HintBlock
  trait InWordHint extends HintBlock
  trait MissHint extends HintBlock

  trait HintProps {
    def inPosHint: InPosHint
    def inWordHint: InWordHint
    def missHint: MissHint

    def validHintChars: Set[Char] = Set(
      inPosHint.inputChar.toLower, inWordHint.inputChar.toLower, missHint.inputChar.toLower,
      inPosHint.inputChar.toUpper, inWordHint.inputChar.toUpper, missHint.inputChar.toUpper
    )
  }

  trait GuessProps {
    def guessWordLength: Int
    def maxGuesses: Int
    def validGuessChars: Set[Char]
    def invalidGuessCharError: String
  }

  trait ResultValidator {
    def validateResult(input: String): Option[String]
  }

  trait GuessValidator {
    def validateGuess(input: String): Option[String]
  }

  trait WordHintsRetriever  {
    def retrieveWordHints(guess: String, answer: Option[String]): WordHints
  }

  trait GuessRetriever {
    def retrieveGuess(suggestions: Seq[String]): String
  }

  trait SolveStrategy {
    def numSuggestions: Int = 6
    def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet
    def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[String], numToReturn: Int): Seq[String]
    def generateNextGuess(remainingWords: WordSet, previousGuesses: Seq[String]): Option[String] = {
      generateNextGuesses(remainingWords, previousGuesses, 1).headOption
    }
  }

  trait LineReader {
    def readLine(): String
  }

  trait StdInLineReader extends LineReader {
    override def readLine(): String = StdIn.readLine()
  }

  trait Writer {
    def writeLine(s: String): Unit
    def writeString(s: String): Unit
  }

  trait ConsoleWriter extends Writer {
    override def writeLine(s: String): Unit = Console.println(s)
    override def writeString(s: String): Unit = Console.print(s)
  }

  trait NullWriter extends Writer {
    override def writeLine(line: String): Unit = {}
    override def writeString(s: String): Unit = {}
  }
}
