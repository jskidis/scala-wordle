package com.skidis.wordle
package strategy

trait ReverseScoringStrategy extends WordScoringStrategy {
  override def sortWordScores(wordScores: Vector[WordScore]): Vector[WordScore] = wordScores.sortBy(_.score)
}
