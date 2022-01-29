package com.skidis.worlde

trait ResultChars {
  val matchInPos = 'g'
  val matchNotInPos = 'y'
  val noMatch = 'b'

  val validChars = List(
    matchInPos, matchNotInPos, noMatch,
    matchInPos.toUpper, matchNotInPos.toUpper, noMatch.toUpper
  )
}
