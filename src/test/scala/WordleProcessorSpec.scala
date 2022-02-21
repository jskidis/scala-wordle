package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.immutable.ListSet

class WordleProcessorSpec extends AnyFunSpec with Matchers {

  class TestWordleProcessor(colorPatterns: List[ColorPattern]) extends WordleProcessor {
    var cycles = 0

    // SolveStrategy
    override def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: ColorPattern): WordSet = wordSet.tail
    override def generateNextGuess(remainingWords: WordSet): (String, String) = (remainingWords.head.string, "")

    // GuessRetriever and ColorPatternRetriever
    override def retrieveGuess(suggestion: String): String = suggestion
    override def retrieveColorPattern(guess: String): ColorPattern = {
      cycles += 1
      colorPatterns(cycles -1)
    }

    // Writer
    override def writeLine(s: String): Unit = {}
    override def writeString(s: String): Unit = {}
  }

  val (word1, word2, word3, word4, word5, word6, word7, word8) = (
    SimpleWordleWord("abcde"), SimpleWordleWord("lmnop"), SimpleWordleWord("vwxyz"),
    SimpleWordleWord("edcba"), SimpleWordleWord("ponml"), SimpleWordleWord("zyxwx"),
    SimpleWordleWord("fffff"), SimpleWordleWord("jjjjj")
  )
  val words:WordSet = ListSet(word1, word2, word3, word4, word5, word6, word7, word8)

  val allGreen: ColorPattern = List.fill(5)(Green)
  val allYellow: ColorPattern = List.fill(5)(Yellow)
  val allBlack: ColorPattern = List.fill(5)(Black)
  val emptyPattern: ColorPattern = List()

  describe("Wordle Processor") {
    it("returns the the guess/color pattern list when the color pattern is all green") {
      val colorPatterns = List(allYellow, allGreen)

      val expectedResult = List( (word1.string, allYellow), (word2.string, allGreen) )

      val processor = new TestWordleProcessor(colorPatterns)
      val result = processor.process(words, word1.string)

      result mustBe expectedResult
    }

    it("returns an empty list if the color pattern returned is empty") {
      val colorPatterns: List[ColorPattern] = List(allYellow, emptyPattern)

      val processor = new TestWordleProcessor(colorPatterns)
      val result = processor.process(words, word1.string)

      result mustBe empty
    }

    it("if wordset is down to 1 word, it automatically selected that word as the winner") {
      val colorPatterns: List[ColorPattern] = Nil // it should never check these, so if it does this will fail

      val expectedResult = List( (word1.string, allGreen) )

      val processor = new TestWordleProcessor(colorPatterns)
      val result = processor.process(List(word1).toSet, word1.string)

      result mustBe expectedResult
    }

    it("if 6 guesses has passed, the cycle stops") {
      val colorPatterns: List[ColorPattern] = List(
        allBlack, allBlack, allBlack, allYellow, allYellow, allYellow
      )

      val expectedResult = List(
        (word1.string, allBlack), (word2.string, allBlack), (word3.string, allBlack),
        (word4.string, allYellow), (word5.string, allYellow), (word6.string, allYellow),
        ("", Nil)
      )

      val processor = new TestWordleProcessor(colorPatterns)
      val result = processor.process(words, words.head.string)

      result mustBe expectedResult
    }
  }
}
