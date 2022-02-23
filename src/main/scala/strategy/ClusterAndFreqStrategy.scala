package com.skidis.wordle
package strategy

import wordle.WordleWordFrequencies

trait ClusterAndFreqStrategy extends ClusterStrategy {
  override def sortWordCluster(wc1: WordClusterCount, wc2: WordClusterCount): Boolean = (wc1.word, wc2.word) match {
    case (wwf1:WordleWordFrequencies, wwf2:WordleWordFrequencies) =>
      wc1.clusterCount + Math.log10(wwf1.frequency)*2 > wc2.clusterCount + Math.log10(wwf2.frequency)*2
    case (ww1: XordlePhrase, ww2: XordlePhrase) =>
      if(wc1.clusterCount != wc2.clusterCount) wc1.clusterCount > wc2.clusterCount else ww1 > ww2
  }
}
