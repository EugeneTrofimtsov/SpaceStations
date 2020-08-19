package spaceX.blocks

class SolarPanel(override val height: Int, override val width: Int, override val code: String) extends Block {

  val moduleType: ModuleType = SP

  def create: String = {
    val panel = new StringBuilder(height * width)
    for (_ <- 0 until height * width) panel += '\u2591'
    panel.toString
  }

}

object SolarPanel {
  def apply(height: Int, width: Int, code: String): SolarPanel = new SolarPanel(height, width, code)
}
