package com.skidis.wordle
package strategy

import hintgen.CachingWordHintsGenerator

trait ReverseScoringStrategy extends WordScoringStrategy {
  override def sortWordScores(wordScores: Vector[WordScore]): Vector[WordScore] = wordScores.sortBy(_.score)
}

trait ReverseClusterStrategy extends ClusterStrategy with ReverseScoringStrategy

trait ReverseClusterStrategyCaching extends ReverseClusterStrategy with CachingWordHintsGenerator
