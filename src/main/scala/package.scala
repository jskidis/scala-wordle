package com.skidis

package object wordle {
  trait XordlePhrase extends Ordered[XordlePhrase] {
    def phrase: String
  }

  type WordHints = List[HintBlock]
  type WordSet = Set[_ <: XordlePhrase]

  trait ResultValidator {
    def validateResult(input: String): Option[String]
  }

  trait GuessValidator {
    def validateGuess(input: String): Option[String]
  }

  trait GuessRetriever {
    def retrieveGuess(suggestion: String): String
  }

  trait ColorPatternRetriever {
    def retrieveColorPattern(guess: String): WordHints
  }

  trait SolveStrategy {
    def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: WordHints): WordSet
    def generateNextGuess(remainingWords: WordSet, hintProps: HintProps): (String, String)
  }

  trait LineReader {
    def readLine(): String
  }

  trait Writer {
    def writeLine(s: String): Unit
    def writeString(s: String): Unit
  }
}
