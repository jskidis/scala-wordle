package com.skidis

package object wordle {
  type WordHints = Seq[HintBlock]
  type WordSet = Set[_ <: XordlePhrase]

  trait XordlePhrase extends Ordered[XordlePhrase] {
    def phrase: String
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
    def retrieveWordHints(guess: String): WordHints
  }

  trait GuessRetriever {
    def retrieveGuess(suggestion: String): String
  }

  trait SolveStrategy {
    def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet
    def generateNextGuess(remainingWords: WordSet): (String, String)
  }

  trait LineReader {
    def readLine(): String
  }

  trait Writer {
    def writeLine(s: String): Unit
    def writeString(s: String): Unit
  }
}
