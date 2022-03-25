package com.skidis.wordle
package strategy

import TestFixtures.{TWord, TestHintProps}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class FixedGuessesStrategySpec extends AnyFunSpec with Matchers with TestHintProps {
  val baseClassGuess = "FROMBASECLASS"

  val wordSet: WordSet = Set(TWord("WORD0"), TWord("WORD1"),
    TWord("WORD2"), TWord("WORD3"), TWord("WORD4"))

  trait BaseClassStrategy extends SolveStrategyWithReduceWordSetFixture {
    override def generateNextGuesses(remainingWords: WordSet,
      previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
    : Seq[String] = Seq(baseClassGuess)
  }

  describe("Fixed Guesses Strategy") {
    it("uses the superclass nextGuess if no fixed guesses are provided") {
      val strategy = new BaseClassStrategy with FixedGuessesStrategy {
        override def fixedGuesses: Seq[String] = Nil
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, numToReturn = 1)
      result must not be empty
      result.head mustBe baseClassGuess
    }

    it("uses the provided guess for the first guess (previous guess is empty) ") {
      val fixedGuess = "FIXED"

      val strategy = new BaseClassStrategy with FixedGuessesStrategy {
        override def fixedGuesses: Seq[String] = Seq(fixedGuess)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, numToReturn = 1)
      result must not be empty
      result.head mustBe fixedGuess
    }

    it("uses the 2nd provided guess if there is 1 previous guess") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", "FIXED2")
      val previousGuesses = Seq(("GUESS1", Seq.fill(6)(missHint)))

      val strategy = new BaseClassStrategy with FixedGuessesStrategy {
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe fixedGuess2
    }

    it("uses the superclass nextGuess when the number of provided guesses is that than the number of previous guesses") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", "FIXED2")
      val previousGuesses = Seq(
        ("GUESS1", Seq.fill(6)(missHint)),
        ("GUESS2", Seq.fill(6)(missHint)),
      )

      val strategy = new BaseClassStrategy with FixedGuessesStrategy {
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe baseClassGuess
    }
  }

  describe("Fixed Guesses Hard Mode Strategy") {
    it("does use provided fixed guess if fixed guess is in word set") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", wordSet.head.text)
      val previousGuesses = Seq(("GUESS1", Seq.fill(6)(inWordHint)))

      val strategy = new BaseClassStrategy with FixedGuessHardModeStrategy {
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe fixedGuess2
    }

    it("doesn't use provided fixed guess if fixed guess is not in word set") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", "NOTINWORDSET")
      val previousGuesses = Seq(("GUESS1", Seq.fill(6)(inWordHint)))

      val strategy = new BaseClassStrategy with FixedGuessHardModeStrategy {
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe baseClassGuess
    }
  }

  describe("Fixed Guesses With Threshold Strategy") {
    it("does use provided fixed guess if fixed guess is in word set") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", wordSet.head.text)
      val previousGuesses = Seq(("GUESS1", Seq.fill(6)(inWordHint)))

      // Threshold is greater than size of word set, so it should only use provided guess if it's in word set
      val strategy = new BaseClassStrategy with FixedGuessWithThresholdStrategy {
        override def numRemainingWordsThreshold: Int = wordSet.size +1 //
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe fixedGuess2
    }

    it("doesn't use provided fixed guess if fixed guess is not in word set and word set size if greater than threshold") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", "NOTINWORDSET")
      val previousGuesses = Seq(("GUESS1", Seq.fill(6)(inWordHint)))

      // Threshold is greater than size of word set, so it should only use provided guess if it's in word set
      val strategy = new BaseClassStrategy with FixedGuessWithThresholdStrategy {
        override def numRemainingWordsThreshold: Int = wordSet.size +1
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe baseClassGuess
    }

    it("does use provided fixed guess if fixed guess even if not in word set if size word set is greater than threshold") {
      val (fixedGuess1, fixedGuess2) = ("FIXED1", "NOTINWORDSET")
      val previousGuesses = Seq(("GUESS1", Seq.fill(6)(inWordHint)))

      // Threshold is less than size of word set, so it should use provided guess even if it's in word set
      val strategy = new BaseClassStrategy with FixedGuessWithThresholdStrategy {
        override def numRemainingWordsThreshold: Int = wordSet.size -1
        override def fixedGuesses: Seq[String] = Seq(fixedGuess1, fixedGuess2)
      }

      val result = strategy.generateNextGuesses(wordSet, previousGuesses, numToReturn = 1)
      result must not be empty
      result.head mustBe fixedGuess2
    }
  }
}