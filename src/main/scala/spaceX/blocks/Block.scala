package spaceX.blocks

trait Block {
  val code: String
  val moduleType: ModuleType
  val height: Int
  val width: Int
  def create: String
}
