package com.skidis.wordle
package waffle

trait GridValidator {
  val intersectionCoordinates: Seq[((Int, Int),(Int, Int))] = Seq(
    ((0, 0), (3, 0)), ((0, 2), (4, 0)), ((0, 4), (5, 0)),
    ((1, 0), (3, 2)), ((1, 2), (4, 2)), ((1, 4), (5, 2)),
    ((2, 0), (3, 4)), ((2, 2), (4, 4)), ((2, 4), (5, 4)),
  )

  def isGridValid(grid: WaffleWordGrid): Boolean = {
    if (grid().size != 6) false
    else if (grid().exists{ row => row.phrase.length != 5 }) false
    else if (intersectionCoordinates.exists { case ((downWordIdx, downCharIdx), (acrossWordIdx, acrossCharIdx)) =>
      grid(downWordIdx).phrase(downCharIdx) != grid(acrossWordIdx).phrase(acrossCharIdx)
    }) false
    else true
  }
}

object GridValidator extends GridValidator
