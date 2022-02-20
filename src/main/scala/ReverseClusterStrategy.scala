package com.skidis.wordle

trait ReverseClusterStrategy extends ClusterStrategy {
  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = !super.sortWordCluster(wc1, wc2)
}

object ReverseClusterStrategy extends ReverseClusterStrategy