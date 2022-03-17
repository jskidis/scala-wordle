package com.skidis.wordle
package waffle

trait IntersectionBasedFilter {
  def filterUponIntersection(grid: WaffleDetailGrid): WaffleDetailGrid = {
    // If I was going to expand the waffle concept further, I would check to make sure grid is 6 words, maybe return an option if it's not
    filterDownWords(filterDownWords(grid).rotateGrid()).rotateGrid()
  }

  private def filterDownWords(grid: WaffleDetailGrid): WaffleDetailGrid = {
    val (pos0X0, pos0X2, pos0X4) = uniqueCharsAtIntersects(grid(0))
    val (pos1X0, pos1X2, pos1X4) = uniqueCharsAtIntersects(grid(1))
    val (pos2X0, pos2X2, pos2X4) = uniqueCharsAtIntersects(grid(2))

    val wordSet3 = grid(3).wordSet.filter { w: XrdleWord =>
      pos0X0.contains(w.text(0)) && pos1X0.contains(w.text(2)) && pos2X0.contains(w.text(4))
    }
    val wordSet4 = grid(4).wordSet.filter { w: XrdleWord =>
      pos0X2.contains(w.text(0)) && pos1X2.contains(w.text(2)) && pos2X2.contains(w.text(4))
    }
    val wordSet5 = grid(5).wordSet.filter { w: XrdleWord =>
      pos0X4.contains(w.text(0)) && pos1X4.contains(w.text(2)) && pos2X4.contains(w.text(4))
    }

    WaffleDetailGrid(Seq(
      grid(0), grid(1), grid(2),
      grid(3).replaceWordSet(wordSet3), grid(4).replaceWordSet(wordSet4), grid(5).replaceWordSet(wordSet5)
    ))
  }

  private def uniqueCharsAtIntersects(ww: WaffleWordDetail): (Set[Char], Set[Char], Set[Char]) ={
    ww.wordSet.map { w: XrdleWord =>
      (w.text(0), w.text(2), w.text(4))
    }.unzip3
  }
}

object IntersectionBasedFilter extends IntersectionBasedFilter
