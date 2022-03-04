package com.skidis.wordle

import TestFixtures.{TInPosHint, TInWordHint, TestGuessProps, TestHintProps}
import wordle.SimpleWordleWord

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.immutable.ListSet

class XordleProcessorSpec extends AnyFunSpec with Matchers {

  class TestXordleProcessor(wordHints: Seq[WordHints]) extends XordleProcessor with TestHintProps with TestGuessProps {
    var cycles = 0
    override def maxGuesses = 6

    // SolveStrategy
    override def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet = wordSet.tail
    override def generateNextGuesses(suggestions: WordSet, number: Int): Vector[XordlePhrase] = suggestions.take(number).toVector

    // GuessRetriever and WordHintsRetriever
    override def retrieveGuess(suggestions: Vector[String]): String = suggestions.headOption.getOrElse("")
    override def retrieveWordHints(guess: String, answer: Option[String]): WordHints = {
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

  val allInPos: WordHints = Seq.fill(TestGuessProps.guessWordLength)(TInPosHint)
  val allInWord: WordHints = Seq.fill(TestGuessProps.guessWordLength)(TInWordHint)
  val allMiss: WordHints = Seq.fill(TestGuessProps.guessWordLength)(TInWordHint)
  val emptyHints: WordHints = Seq()

  describe("Wordle Processor") {
    it("returns the the guess/color pattern list when the color pattern is all green") {
      val wordHints = Seq(allInWord, allInPos)

      val expectedResult = Seq( (word1.phrase, allInWord), (word2.phrase, allInPos) )

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(words, word1.phrase)

      result.isRight mustBe true
      result.map {_ mustBe expectedResult }
    }

    it("returns an error (left side value) if the color pattern returned is empty") {
      val wordHints: Seq[WordHints] = Seq(allMiss, allInPos)

      val processor = new TestXordleProcessor(wordHints) {
        override def generateNextGuesses(suggestions: WordSet, number: Int): Vector[XordlePhrase] = Vector()
      }
      val result = processor.process(words, word1.phrase)

      result.isLeft mustBe true
    }

    it("returns an error (left side value) if there are no guesses returned") {
      val wordHints: Seq[WordHints] = Seq(allInWord, emptyHints)

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(words, word1.phrase)

      result.isLeft mustBe true
    }

    it("if wordset is down to 1 word, it automatically selected that word as the winner") {
      val wordHints: Seq[WordHints] = Nil // it should never check these, so if it does this will fail

      val expectedResult = Seq( (word1.phrase, allInPos) )

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(Seq(word1).toSet, word1.phrase)

      result.isRight mustBe true
      result.map {_  mustBe expectedResult }
    }

    it("if 6 guesses has passed, the cycle stops") {
      val wordHints: Seq[WordHints] = Seq(
        allMiss, allMiss, allMiss, allInWord, allInWord, allInWord
      )

      val expectedResult = Seq(
        (word1.phrase, allMiss), (word2.phrase, allMiss), (word3.phrase, allMiss),
        (word4.phrase, allInWord), (word5.phrase, allInWord), (word6.phrase, allInWord),
        ("", Nil)
      )

      val processor = new TestXordleProcessor(wordHints)
      val result = processor.process(words, words.head.phrase)

      result.isRight mustBe true
      result.map {_  mustBe expectedResult }
    }
  }
}
