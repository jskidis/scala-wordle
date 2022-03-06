package com.skidis.wordle
package strategy

trait ReverseClusterStrategy extends ClusterStrategy {
  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    !super.sortWordCluster(wc1, wc2)
  }
}

trait ReverseClusterStrategyCaching extends ReverseClusterStrategy with CachingWordHintsGenerator
