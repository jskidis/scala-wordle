package com.skidis.wordle

import BlockColor.{Blank, Green, Yellow}
import wordle.SimpleWordleWord

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.immutable.ListSet

class WordleProcessorSpec extends AnyFunSpec with Matchers {

  class TestXordleProcessor(colorPatterns: List[ColorPattern]) extends XordleProcessor {
    var cycles = 0

    // SolveStrategy
    override def reduceWordSet(wordSet: WordSet, currentGuess: String, colorPattern: ColorPattern): WordSet = wordSet.tail
    override def generateNextGuess(remainingWords: WordSet): (String, String) = (remainingWords.head.phrase, "")

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
  val allBlack: ColorPattern = List.fill(5)(Blank)
  val emptyPattern: ColorPattern = List()

  describe("Wordle Processor") {
    it("returns the the guess/color pattern list when the color pattern is all green") {
      val colorPatterns = List(allYellow, allGreen)

      val expectedResult = List( (word1.phrase, allYellow), (word2.phrase, allGreen) )

      val processor = new TestXordleProcessor(colorPatterns)
      val result = processor.process(words, word1.phrase)

      result mustBe expectedResult
    }

    it("returns an empty list if the color pattern returned is empty") {
      val colorPatterns: List[ColorPattern] = List(allYellow, emptyPattern)

      val processor = new TestXordleProcessor(colorPatterns)
      val result = processor.process(words, word1.phrase)

      result mustBe empty
    }

    it("if wordset is down to 1 word, it automatically selected that word as the winner") {
      val colorPatterns: List[ColorPattern] = Nil // it should never check these, so if it does this will fail

      val expectedResult = List( (word1.phrase, allGreen) )

      val processor = new TestXordleProcessor(colorPatterns)
      val result = processor.process(List(word1).toSet, word1.phrase)

      result mustBe expectedResult
    }

    it("if 6 guesses has passed, the cycle stops") {
      val colorPatterns: List[ColorPattern] = List(
        allBlack, allBlack, allBlack, allYellow, allYellow, allYellow
      )

      val expectedResult = List(
        (word1.phrase, allBlack), (word2.phrase, allBlack), (word3.phrase, allBlack),
        (word4.phrase, allYellow), (word5.phrase, allYellow), (word6.phrase, allYellow),
        ("", Nil)
      )

      val processor = new TestXordleProcessor(colorPatterns)
      val result = processor.process(words, words.head.phrase)

      result mustBe expectedResult
    }
  }
}
