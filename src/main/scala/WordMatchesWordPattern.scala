package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

import scala.annotation.tailrec

object WordMatchesWordPattern {

  def apply(word: String, wordPattern: List[(Char, BlockColor)]): Boolean = {
    word.length == wordPattern.length &&
      recursePatterns(word.toUpperCase, wordPattern.map { case (l, c) => (l.toUpper, c) })
  }

  @tailrec
  private def recursePatterns(word: String, wordPattern: List[(Char, BlockColor)], index: Int = 0): Boolean = {
    if (index >= word.length) true
    else wordPattern(index) match {
      case (letter, Green) if word(index) != letter => false
      case (letter, Yellow) if !word.contains(letter) || word(index) == letter => false
      case (letter, Black) if word.contains(letter) => false
      case _ => recursePatterns(word, wordPattern, index +1)
    }
  }
}
