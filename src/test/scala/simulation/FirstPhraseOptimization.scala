package com.skidis.wordle
package simulation

import nerdle.NerdleGuessableGenerator
import strategy.ClusterStrategy

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object FirstPhraseOptimization extends App with ClusterStrategy {
  val wordSet = NerdleGuessableGenerator.generatateEquations()

  val startTimestamp = System.currentTimeMillis()
  println(generateNextGuess(wordSet, TestHintProps))

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

  override def generateClusters(remainingWords: WordSet, hintProps: HintProps): List[WordClusterCount] = {
    val batches = remainingWords.grouped(2500)
    val batchFutures = batches.map { batch => Future(super.generateClusters(batch, TestHintProps)) }
    Await.result(Future.sequence(batchFutures), 1.hour).flatten.toList
  }

  override def generateNextGuess(remainingWords: WordSet, hintProps: HintProps): (String, String) = {
    val results = super.generateNextGuess(remainingWords, TestHintProps)
    results
  }
}
