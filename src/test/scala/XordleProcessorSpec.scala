package com.skidis.wordle

import wordle.SimpleWordleWord

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.immutable.ListSet

class XordleProcessorSpec extends AnyFunSpec with Matchers {

  class TestXordleProcessor(wordHints: List[WordHints]) extends XordleProcessor {
    var cycles = 0

    val hintProps: HintProps = TestHintProps

    // SolveStrategy
    override def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet = wordSet.tail
    override def generateNextGuess(remainingWords: WordSet, hintProps: HintProps): (String, String) = (remainingWords.head.phrase, "")

    // GuessRetriever and WordHintsRetriever
    override def retrieveGuess(suggestion: String): String = suggestion
    override def retrieveWordHints(guess: String): WordHints = {
      cycles += 1
      wordHints(cycles -1)
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

  val allInPos: WordHints = List.fill(5)(AInPosHint)
  val allInWord: WordHints = List.fill(5)(AInWordHint)
  val allMiss: WordHints = List.fill(5)(AInWordHint)
  val emptyHints: WordHints = List()

  describe("Wordle Processor") {
    it("returns the the guess/color pattern list when the color pattern is all green") {
      val wordHints = List(allInWord, allInPos)

      val expectedResult = List( (word1.phrase, allInWord), (word2.phrase, allInPos) )

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(words, word1.phrase)

      result mustBe expectedResult
    }

    it("returns an empty list if the color pattern returned is empty") {
      val wordHints: List[WordHints] = List(allInWord, emptyHints)

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(words, word1.phrase)

      result mustBe empty
    }

    it("if wordset is down to 1 word, it automatically selected that word as the winner") {
      val wordHints: List[WordHints] = Nil // it should never check these, so if it does this will fail

      val expectedResult = List( (word1.phrase, allInPos) )

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(List(word1).toSet, word1.phrase)

      result mustBe expectedResult
    }

    it("if 6 guesses has passed, the cycle stops") {
      val wordHints: List[WordHints] = List(
        allMiss, allMiss, allMiss, allInWord, allInWord, allInWord
      )

      val expectedResult = List(
        (word1.phrase, allMiss), (word2.phrase, allMiss), (word3.phrase, allMiss),
        (word4.phrase, allInWord), (word5.phrase, allInWord), (word6.phrase, allInWord),
        ("", Nil)
      )

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(words, words.head.phrase)

      result mustBe expectedResult
    }
  }
}
