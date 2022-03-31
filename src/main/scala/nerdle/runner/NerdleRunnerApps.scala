package com.skidis.wordle
package nerdle.runner

import runners.{FirstGuessRunner, XrdleInteractiveRunner, XrdleSimulationRunner}

/* Interactive Runner Apps */
object NerdleInteractiveStandardRunner extends App with XrdleInteractiveRunner with NerdleStandardRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

object NerdleInteractiveMiniRunner extends App with XrdleInteractiveRunner with NerdleMiniRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

object NerdleInteractiveRandomRunner extends App with XrdleInteractiveRunner with NerdleRandomGuessRunner {
  printWordleBlock(runInteractive(), maxGuesses)
}

/* Simulation Runner Apps */
object NerdleSimulationStandardRunner extends App with XrdleSimulationRunner with NerdleStandardRunner {
  printResults(runSimulation())
}

object NerdleSimulationMiniRunner extends App with XrdleSimulationRunner with NerdleMiniRunner {
  printResults(runSimulation())
}

object NerdleSimulationReverseRunner extends App with XrdleSimulationRunner with NerdleReverseRunner {
  printResults(runSimulation())
}

/* First Guess(es) Optimizer Apps */
object FirstGuessSuggestion { val number = 12 }

object NerdleFirstGuessOptStandardRunner extends App with FirstGuessRunner with NerdleStandardRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object NerdleFirstGuessOptMiniRunner extends App with FirstGuessRunner with NerdleMiniRunner {
  runOptimizer(FirstGuessSuggestion.number)
}

object NerdleFirstGuessOptReverseRunner extends App with FirstGuessRunner with NerdleReverseRunner {
  runOptimizer(FirstGuessSuggestion.number)
}



