package com.skidis.wordle
package input

trait BasicResultAndGuessValidator extends ResultValidator with GuessValidator with HintProps with GuessProps {
  val lengthMsg: String = s"Input must be $guessWordLength characters"
  val invalidHintCharMsg: String = s"Input may only contain ${validHintChars.mkString(", ")}"
  val wasResultMsg: String = "That was a word, not a result"

  override def validateResult(input: String): Option[String]  = {
    if (input.length != guessWordLength) Option(lengthMsg)
    else if (input.filter { ch => validHintChars.contains(ch) } != input) Option(invalidHintCharMsg)
    else None
  }

  override def validateGuess(input: String): Option[String] = {
    if (input.length != guessWordLength) Option(lengthMsg)
    else if (input.filter { ch => validGuessChars.contains(ch) } != input) Option(invalidGuessCharError)
    else if (input.forall(ch => validHintChars.contains(ch))) Some(wasResultMsg)
    else None
  }
}
