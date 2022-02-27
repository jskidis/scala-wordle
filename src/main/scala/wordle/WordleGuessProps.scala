package com.skidis.wordle
package wordle

trait WordleGuessProps extends GuessProps {
  override def guessWordLength: Int = 5
  override def validGuessChars: Set[Char] = (('a' to 'z') ++ ('A' to 'Z')).toSet
  override def invalidGuessCharError: String = "Input may only contain letters"
}
