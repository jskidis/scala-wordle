package com.skidis.wordle

import BlockColor.{BlockColor, Green, Yellow}

import scala.annotation.tailrec

object GenerateMatchingDetail {
  def apply(word: String, colorPattern: List[BlockColor]): MatchingDetail = recurse(word, colorPattern)

  @tailrec
  private def recurse(word: String, colorPattern: List[BlockColor],
      acc: MatchingDetail = MatchingDetail(Nil, Set())): MatchingDetail =
  {
    if (word.isEmpty || colorPattern.isEmpty) acc
    else {
      val newAcc = colorPattern.head match {
        case Green => MatchingDetail(acc.positions :+ Option(word.head.toUpper), acc.otherLetters)
        case Yellow => MatchingDetail(acc.positions :+ None, acc.otherLetters + word.head.toUpper)
        case _ => MatchingDetail(acc.positions :+ None, acc.otherLetters)
      }
      recurse(word.tail, colorPattern.tail, newAcc)
    }
  }
}
