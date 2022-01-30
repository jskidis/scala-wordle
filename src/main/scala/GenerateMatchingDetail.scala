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
        case Green => MatchingDetail(acc.positionMatchedLetters :+ Option(word.head.toUpper), acc.otherMatchedLetters)
        case Yellow => MatchingDetail(acc.positionMatchedLetters :+ None, acc.otherMatchedLetters + word.head.toUpper)
        case _ => MatchingDetail(acc.positionMatchedLetters :+ None, acc.otherMatchedLetters)
      }
      recurse(word.tail, colorPattern.tail, newAcc)
    }
  }
}
