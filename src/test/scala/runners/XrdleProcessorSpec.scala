package com.skidis.wordle
package runners

import TestFixtures._

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.immutable.ListSet

class XrdleProcessorSpec extends AnyFunSpec with Matchers {

  class TestXrdleProcessor(wordHints: Seq[WordHints]) extends XrdleProcessor
    with TestHintProps with TestGuessProps with NullWriter {
    var cycles = 0
    override def maxGuesses = 6

    // SolveStrategy
    override def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet = wordSet.tail
    override def generateNextGuesses(suggestions: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
    : Seq[String] = suggestions.map{w: XrdleWord => w.text}.take(numToReturn).toVector

    // GuessRetriever and WordHintsRetriever
    override def retrieveGuess(suggestions: Seq[String]): String = suggestions.headOption.getOrElse("")
    override def retrieveWordHints(guess: String, answer: Option[String]): WordHints = {
      cycles += 1
      wordHints(cycles -1)
    }
  }

  val (word1, word2, word3, word4, word5, word6, word7, word8) = (
    TWord("abcde"), TWord("lmnop"), TWord("vwxyz"),
    TWord("edcba"), TWord("ponml"), TWord("zyxwx"),
    TWord("fffff"), TWord("jjjjj")
  )
  val words:WordSet = ListSet(word1, word2, word3, word4, word5, word6, word7, word8)

  val allInPos: WordHints = Seq.fill(TestGuessProps.guessWordLength)(TInPosHint)
  val allInWord: WordHints = Seq.fill(TestGuessProps.guessWordLength)(TInWordHint)
  val allMiss: WordHints = Seq.fill(TestGuessProps.guessWordLength)(TInWordHint)
  val emptyHints: WordHints = Seq()

  describe("Wordle Processor") {
    it("returns the the guess/color pattern list when the color pattern is all green") {
      val wordHints = Seq(allInWord, allInPos)

      val expectedResult = Seq( (word1.text, allInWord), (word2.text, allInPos) )

      val processor = new TestXrdleProcessor(wordHints)
      val result = processor.process(words)

      result.isRight mustBe true
      result.map {_ mustBe expectedResult }
    }

    it("returns an error (left side value) if the color pattern returned is empty") {
      val wordHints: Seq[WordHints] = Seq(allMiss, allInPos)

      val processor = new TestXrdleProcessor(wordHints) {
        override def generateNextGuesses(suggestions: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
        : Seq[String] = Vector()
      }
      val result = processor.process(words)

      result.isLeft mustBe true
    }

    it("returns an error (left side value) if there are no guesses returned") {
      val wordHints: Seq[WordHints] = Seq(allInWord, emptyHints)

      val processor = new TestXrdleProcessor(wordHints)
      val result = processor.process(words)

      result.isLeft mustBe true
    }

    it("if wordset is down to 1 word, it automatically selected that word as the winner") {
      val wordHints: Seq[WordHints] = Nil // it should never check these, so if it does this will fail

      val expectedResult = Seq( (word1.text, allInPos) )

      val processor = new TestXrdleProcessor(wordHints)
      val result = processor.process(Seq(word1).toSet)

      result.isRight mustBe true
      result.map {_  mustBe expectedResult }
    }

    it("if 6 guesses has passed, the cycle stops") {
      val wordHints: Seq[WordHints] = Seq(
        allMiss, allMiss, allMiss, allInWord, allInWord, allInWord
      )

      val expectedResult = Seq(
        (word1.text, allMiss), (word2.text, allMiss), (word3.text, allMiss),
        (word4.text, allInWord), (word5.text, allInWord), (word6.text, allInWord),
        ("", Nil)
      )

      val processor = new TestXrdleProcessor(wordHints)
      val result = processor.process(words)

      result.isRight mustBe true
      result.map {_  mustBe expectedResult }
    }
  }
}
