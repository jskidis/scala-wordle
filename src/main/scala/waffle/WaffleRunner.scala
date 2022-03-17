package com.skidis.wordle
package waffle

import scala.io.Source

object WaffleRunner extends App with WaffleHintProps with CharFreqMapGenerator with LetterFreqFilter
  with WaffleHintFilter with IntersectionBasedFilter with ValidWordGridGenerator with WordSetsFromGridsGenerator
  with WaffleWriter with ConsoleWriter {

  val words = WordReader.readWords(Source.fromResource("word-frequency-filtered.txt"))

  val inputGrid = WaffleReader.readWaffle(
    Seq("apltt", "d e e", "eeodd", "s g e", "rrsny"),
    Seq("gybbg", "b y g", "ybgbb", "b b b", "gbbbg"),
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
        println(ww.wordSet.map { w: XordlePhrase => w.phrase }.mkString(", "))
      }
    }
  }
}

//Seq("ldral", "y s o", "aorea", "t w a", "tsloy"),
//Seq("gbbgg", "b y g", "gygbb", "b b b", "gybbg"),

//Seq("miimc", "e l o", "uegre", "r o a", "raawy"),
//Seq("ggyyg", "y y b", "bygyb", "b y y", "gybbg"),

//Seq("ranrh", "g r u", "oobae", "r e e", "mlamy"),
//Seq("gbybg", "b g b", "bbgyy", "b b b", "gbybg"),

//Seq("guahm", "p l d", "nrtea", "r e o", "tueam"),
//Seq("gbybg", "b b y", "bbggy", "y g b", "gyybg"),

//Seq("apltt", "d e e", "eeodd", "s g e", "rrsny"),
//Seq("gybbg", "b y g", "ybgbb", "b b b", "gbbbg"),

