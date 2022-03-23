package com.skidis.wordle
package strategy

trait ReverseStrategy extends WordScoringStrategy {
  override def sortWordScores(wordScores: Vector[WordScore]): Vector[WordScore] = wordScores.sortBy(_.score)
}

trait ReverseClusterStrategy extends ClusterStrategy with ReverseStrategy

trait ReverseClusterStrategyCaching extends ReverseClusterStrategy with CachingWordHintsGenerator
