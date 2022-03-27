package com.skidis.wordle
package nerdle.runner

import runners.XrdleInteractiveRunner

trait NerdleInteractiveStandardRunner extends XrdleInteractiveRunner with NerdleStandardRunner
object NerdleInteractiveStandardRunner extends App with NerdleInteractiveStandardRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

trait NerdleInteractiveMiniRunner extends XrdleInteractiveRunner with NerdleMiniRunner
object NerdleInteractiveMiniRunner extends App with NerdleInteractiveMiniRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

trait NerdleInteractiveRandomRunner extends XrdleInteractiveRunner with NerdleRandomGuessRunner
object NerdleInteractiveRandomRunner extends App with NerdleInteractiveRandomRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}


