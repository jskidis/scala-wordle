package com.skidis.wordle
package strategy

trait ClusterAndFreqStrategy extends ClusterStrategy {

  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = {
    if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else wc1.word > wc2.word
  }
}
