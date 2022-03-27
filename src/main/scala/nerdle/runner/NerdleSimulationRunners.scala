package com.skidis.wordle
package nerdle.runner

import runners.XrdleSimulationRunner

trait NerdleSimulationStandardRunner extends XrdleSimulationRunner with NerdleStandardRunner
object NerdleSimulationStandardRunner extends NerdleSimulationStandardRunner with App {
  printResults(runSimulation())
}

trait NerdleSimulationMiniRunner extends XrdleSimulationRunner with NerdleMiniRunner
object NerdleSimulationMiniRunner extends NerdleSimulationMiniRunner with App {
  printResults(runSimulation())
}

