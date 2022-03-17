package com.skidis.wordle
package waffle

import scala.io.Source

object WaffleRunner extends App with WaffleHintProps with CharFreqMapGenerator with LetterFreqFilter
  with WaffleHintFilter with IntersectionBasedFilter with ValidWordGridGenerator with WordSetsFromGridsGenerator
  with WaffleWriter with ConsoleWriter {

  val words = WordReader.readWords(Source.fromResource("guessable-words.txt"))

  val inputGrid = WaffleReader.readWaffle(
    Seq("iarac", "i h l", "cmete", "p e d", "ylsrs"),
    Seq("gbbgg", "b b b", "gygbg", "b b b", "gybbg"),
    words
  )

  if (inputGrid.isLeft)
    println(s"Error input: ${inputGrid.left.getOrElse("")}")
  else {
    val initialGrid = inputGrid.getOrElse(WaffleDetailGrid(Nil))

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
        println(ww.wordSet.map { w: XrdleWord => w.text }.mkString(", "))
      }
    }
  }
}
