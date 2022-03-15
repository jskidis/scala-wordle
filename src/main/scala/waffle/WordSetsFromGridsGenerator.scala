package com.skidis.wordle
package waffle

trait WordSetsFromGridsGenerator {
  def genWordSetsFromGrids(originalGrid: WaffleDetailGrid, possbibleGrids: Seq[WaffleWordGrid]): WaffleDetailGrid = {
    WaffleDetailGrid(
      originalGrid().zipWithIndex.map { case (wwd: WaffleWordDetail, idx: Int) =>
        val newWordSet = possbibleGrids.map { possibleGrid => possibleGrid(idx) }.toSet
        wwd.replaceWordSet(newWordSet)
      }
    )
  }
}

object WordSetsFromGridsGenerator extends WordSetsFromGridsGenerator
