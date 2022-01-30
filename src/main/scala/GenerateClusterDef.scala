package com.skidis.wordle

import BlockColors.{BlockColor, Green, Yellow}

import scala.annotation.tailrec

object GenerateClusterDef {
  def apply(word: String, colorPattern: List[BlockColor]): ClusterDef = recurse(word, colorPattern)

  @tailrec
  private def recurse(word: String, colorPattern: List[BlockColor],
      acc: ClusterDef = ClusterDef(Nil, Set())): ClusterDef =
  {
    if (word.isEmpty || colorPattern.isEmpty) acc
    else {
      val newAcc = colorPattern.head match {
        case Green => ClusterDef(acc.positions :+ Option(word.head), acc.otherLetters)
        case Yellow => ClusterDef(acc.positions :+ None, acc.otherLetters + word.head)
        case _ => ClusterDef(acc.positions :+ None, acc.otherLetters)
      }
      recurse(word.tail, colorPattern.tail, newAcc)
    }
  }
}
