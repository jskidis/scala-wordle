package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.operators

trait NerdleGuessProps extends GuessProps {
  override def guessWordLength: Int = 8
  override def maxGuesses: Int = 6
  override def validGuessChars: Set[Char] = (('0' to '9') ++ operators :+ '=').toSet
  override def invalidGuessCharError: String = "Input may only contain numbers or operators"
}

trait MiniNerdleGuessProps extends NerdleGuessProps {
  override def guessWordLength: Int = 6
}
