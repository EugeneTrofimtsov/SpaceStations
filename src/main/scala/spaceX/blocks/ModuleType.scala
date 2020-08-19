package spaceX.blocks {

  sealed trait ModuleType

  /** Solar panel */
  case object SP extends ModuleType

  /** Human module */
  case object HM extends ModuleType

  /** Terminal module */
  case object TM extends ModuleType

}
