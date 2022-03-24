package com.skidis.wordle
package strategy

import TestFixtures.{TWord, TestHintProps}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class HardModeWordElimStrategySpec extends AnyFunSpec with Matchers with TestHintProps {
  val wordsThatMatch = Set("MATCH", "GOODY", "EXACT")
  val wordsThatDontMatch = Set("NOMAT", "BADDY", "DIFER")
  val wordSet: WordSet = wordsThatMatch.map(TWord) ++ wordsThatDontMatch.map(TWord)

  trait WordPatternMatcherFixture extends WordPatternMatcher {
    override def doesWordMatch(word: String, wordPattern: Seq[(Char, HintBlock)]): Boolean = {
      wordsThatMatch.contains(word)
    }
  }

  trait NextGuessFixture {
    def generateNextGuesses(remainingWords: WordSet,
      previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
    : Seq[String] = remainingWords.take(numToReturn).map(_.text).toSeq
  }

  describe("Hard Mode Word Elimination Strategy") {
    it("reduces word set to only include words that match the pattern of the current guess") {
      val strategy = new HardModeWordElimStrategy with WordPatternMatcherFixture with NextGuessFixture
      val result = strategy.reduceWordSet(wordSet, "ZZZZZ", Seq.fill(5)(missHint))
      result must contain theSameElementsAs wordsThatMatch.map(TWord)
    }
  }
}
