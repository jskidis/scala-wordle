package com.skidis.wordle
package strategy

import TestFixtures.{SolveStrategyWithReduceWordSetFixture, TWord}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class RandomWordStrategySpec extends AnyFunSpec with Matchers {
  val wordSet: WordSet = (for {
    l1 <- 'A' to 'Z'
    l2 <- 'A' to 'Z'
    l3 <- 'A' to 'Z'
  } yield {
    TWord(s"$l1$l2$l3")
  }).toSet

  describe("Random Word Strategy") {
    it("should return different set of suggestions each time") {
      // Theoretically it could return the same set at random,
      // but tried to make the solution space sufficiently unlikely
      val strategy = new RandomGuessStrategy with SolveStrategyWithReduceWordSetFixture

      val result1 = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, number = 10)
      val result2 = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, number = 10)
      val result3 = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, number = 10)

      result1 must not be result2
      result1 must not be result3
      result2 must not be result3
    }
  }

}
