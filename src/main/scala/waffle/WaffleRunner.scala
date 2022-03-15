package com.skidis.wordle
package waffle

import scala.io.Source

object WaffleRunner extends App with WaffleHintProps with CharFreqMapGenerator with LetterFreqFilter
  with WaffleHintFilter with IntersectionBasedFilter with ValidWordGridGenerator with WordSetsFromGridsGenerator
  with WaffleWriter with ConsoleWriter {

  val words = WordReader.readWords(Source.fromResource("word-frequency-filtered.txt"))

  val initialGrid = WaffleDetailGrid(Seq(
    WaffleWordDetail(WaffleWord("GUAHM"), Seq(inPosHint, missHint, inWordHint, missHint, inPosHint), words),
    WaffleWordDetail(WaffleWord("NRTEA"), Seq(missHint, missHint, inPosHint, inPosHint, inWordHint), words),
    WaffleWordDetail(WaffleWord("TUEAM"), Seq(inPosHint, inWordHint, inWordHint, missHint, inPosHint), words),
    WaffleWordDetail(WaffleWord("GPNRT"), Seq(inPosHint, missHint, missHint, inWordHint, inPosHint), words),
    WaffleWordDetail(WaffleWord("ALTEE"), Seq(inWordHint, missHint, inPosHint, inPosHint, inWordHint), words),
    WaffleWordDetail(WaffleWord("MDAOM"), Seq(inPosHint, inWordHint, inWordHint, missHint, inPosHint), words)
  ))

  writeWordGrid(initialGrid.wordGrid)
  writeHintGrid(initialGrid.hintGrid)

  val gridCharFreq = generateCharFreqMap(initialGrid.wordGrid.gridLetters)
  val gridFilteredByCharFreq = filterByLetterFreq(initialGrid, gridCharFreq)
  val gridFilteredOnHints = filterOnHints(gridFilteredByCharFreq)
  val gridFilteredByIntersections = filterUponIntersection(gridFilteredOnHints)
  val validWordGrids = generateValidGrids(gridFilteredByIntersections, gridCharFreq)
  val finalFilterResult = genWordSetsFromGrids(gridFilteredByIntersections, validWordGrids)

  if(finalFilterResult.hasSolution) {
    writeWordGrid(finalFilterResult.convertToSolution().wordGrid)
  }
  else {
    finalFilterResult().foreach { ww =>
      println(ww.wordSet.map { w: XordlePhrase => w.phrase }.mkString(", "))
    }
  }
}

//WaffleWordDetail(WaffleWord("LDRAL"), Seq(inPosHint, missHint, missHint, inPosHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("AOREA"), Seq(inPosHint, inWordHint, inPosHint, missHint, missHint), words),
//WaffleWordDetail(WaffleWord("TSLOY"), Seq(inPosHint, inWordHint, missHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("LYATT"), Seq(inPosHint, missHint, inPosHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("RSRWL"), Seq(missHint, inWordHint, inPosHint, missHint, missHint), words),
//WaffleWordDetail(WaffleWord("LOAAY"), Seq(inPosHint, inPosHint, missHint, missHint, inPosHint), words)

//WaffleWordDetail(WaffleWord("MIIMC"), Seq(inPosHint, inPosHint, inWordHint, inWordHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("UEGRE"), Seq(missHint, inWordHint, inPosHint, inWordHint, missHint), words),
//WaffleWordDetail(WaffleWord("RAAWY"), Seq(inPosHint, inWordHint, missHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("MEURR"), Seq(inPosHint, inWordHint, missHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("ILGOA"), Seq(inWordHint, inWordHint, inPosHint, inWordHint, missHint), words),
//WaffleWordDetail(WaffleWord("COEAY"), Seq(inPosHint, missHint, missHint, inWordHint, inPosHint), words)

//WaffleWordDetail(WaffleWord("RANRH"), Seq(inPosHint, missHint, inWordHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("OOBAE"), Seq(missHint, missHint, inPosHint, inWordHint, inWordHint), words),
//WaffleWordDetail(WaffleWord("MLAMY"), Seq(inPosHint, missHint, inWordHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("RGORM"), Seq(inPosHint, missHint, missHint, missHint, inPosHint), words),
//WaffleWordDetail(WaffleWord("NRBEA"), Seq(inWordHint, inPosHint, inPosHint, missHint, inWordHint), words),
//WaffleWordDetail(WaffleWord("HUEEY"), Seq(inPosHint, missHint, inWordHint, missHint, inPosHint), words)
