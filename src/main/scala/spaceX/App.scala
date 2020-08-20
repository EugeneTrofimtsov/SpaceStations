package spaceX

import spaceX.blocks._

object App {

  def defaultStation(): Unit = {
    SpaceStation(List(
      List(TerminalModule(3, 6, "cap")),
      List(SolarPanel(2, 12, "panel"), HumanModule(6, 8, "room"), SolarPanel(2, 12, "panel")),
      List(HumanModule(4, 4, "gateway")),
      List(SolarPanel(2, 12, "panel"), HumanModule(8, 12, "room"), SolarPanel(2, 12, "panel"))
    )).slowlyPrint(250)
  }

  val defaultStationArr: Array[String] = Array(
    "TM,3,6,cap",
    "SP,2,12,panel|HM,6,8,room|SP,2,12,panel",
    "HM,4,4,gateway",
    "SP,2,12,panel|HM,8,12,room|SP,2,12,panel"
  )

  def main(args: Array[String]): Unit = {
    defaultStation()
    SpaceStation(SpaceStation.parseStation(defaultStationArr)).slowlyPrint(250)
  }

}
