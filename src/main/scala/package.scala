package com.skidis

import com.skidis.wordle.BlockColor.BlockColor

package object wordle {
  type ColorPattern = List[BlockColor]
  type WordSet = Set[_ <: WordleWord]

  object BlockColor extends Enumeration {
    type BlockColor = String
    val Green = "\uD83D\uDFE9"
    val Yellow = "\uD83D\uDFE8"
    val Blank = "\u2B1C"
  }

  val (greenChar, yellowChar, blankChar) = ('G', 'Y', 'B')
  val validBlockChars = List(greenChar, yellowChar, blankChar)

  trait GuessRetriever {
    def retrieveGuess(suggestion: String): String
  }

  trait ColorPatternRetriever {
    def retrieveColorPattern(guess: String): ColorPattern
  }

  trait SolveStrategy {
    def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: ColorPattern): WordSet
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
