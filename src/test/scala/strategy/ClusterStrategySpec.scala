package com.skidis.wordle
package strategy

import TestFixtures.{TWord, TestHintProps}
import hintgen.WordHintsGenerator

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class ClusterStrategySpec extends AnyFunSpec with Matchers with TestHintProps {
  val allGreenWordHints: WordHints = Seq.fill(5)(inPosHint)
  val allYelloWordHints: WordHints = Seq.fill(5)(inWordHint)
  val allBlackWordHints: WordHints = Seq.fill(5)(missHint)

  val wordHintsMap: Map[String, WordHints] = Map(
    "ALLGREEN1" -> allGreenWordHints,
    "ALLYELLO1" -> allYelloWordHints,
    "ALLYELLO2" -> allYelloWordHints,
    "ALLBLACK1" -> allBlackWordHints,
    "ALLBLACK2" -> allBlackWordHints,
    "ALLBLACK3" -> allBlackWordHints
  )

  trait WordHintsGeneratorFixture extends WordHintsGenerator {
    override def generateWordHints(answer: String, word: String): WordHints = {
      wordHintsMap(word)
    }
  }

  describe("Cluster Generator") {
    it("should return a single all miss word hint when only word in set was that pattern") {
      val wordSet = Set(TWord("ALLBLACK1"))
      val generator = new ClusterGenerator with WordHintsGeneratorFixture with TestHintProps

      val expectedResults = Set(allBlackWordHints)
      val result = generator.generateUniquePatterns(TWord("DONTCARE"), wordSet)
      result must contain theSameElementsAs expectedResults
    }

    it("should return a single all in-position word hint when both words in set return same pattern") {
      val wordSet = Set(TWord("ALLBLACK1"), TWord("ALLBLACK2"))
      val generator = new ClusterGenerator with WordHintsGeneratorFixture with TestHintProps

      val expectedResults = Set(allBlackWordHints)
      val result = generator.generateUniquePatterns(TWord("DONTCARE"), wordSet)
      result must contain theSameElementsAs expectedResults
    }

    it("should return each of the unique word hints") {
      val wordSet = wordHintsMap.keySet.map(TWord)
      val generator = new ClusterGenerator with WordHintsGeneratorFixture with TestHintProps

      val result = generator.generateUniquePatterns(TWord("DONTCARE"), wordSet)
      val expectedResults = Set(allGreenWordHints, allYelloWordHints, allBlackWordHints)
      result must contain theSameElementsAs expectedResults
    }
  }

  val uniqueClustersMap: Map[String, Set[WordHints]] = Map(
    "RETURNS3" -> Set(allGreenWordHints, allYelloWordHints, allBlackWordHints),
    "RETURNS2" -> Set(allYelloWordHints, allBlackWordHints),
    "RETURNS1G" -> Set(allGreenWordHints),
    "RETURNS1Y" -> Set(allGreenWordHints),
    "RETURNS1B" -> Set(allGreenWordHints)
  )

  trait ClusterGeneratorFixture extends ClusterGenerator {
    override def generateUniquePatterns(potentialAnswer: XrdleWord, wordSet: WordSet): Set[WordHints] = {
      uniqueClustersMap(potentialAnswer.text)
    }
  }

  describe("Cluster Strategy") {
    it("returns the top words in terms of number of unique clusters") {
      val wordSet = Random.shuffle(uniqueClustersMap.keySet.map(TWord))
      val strategy = new ClusterStrategy with ClusterGeneratorFixture with TestHintProps

      val expectedResults = Vector("RETURNS3", "RETURNS2")
      val results = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, numToReturn = 2)
      results mustBe expectedResults
    }
    // Note: Ties are not sorted in any deterministic manner because set.toVector.sortBy does not create a deterministic order
  }
}
