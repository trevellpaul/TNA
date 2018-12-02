import scala.io.Source

object Data{

  case class DataLine(
                        fileName: String,
                        origin: String,
                        metadata: String,
                        hash: String
                      )

  def importMethod(fileName: String):List[DataLine] = {
    val dataLines = Source.fromFile(fileName).getLines().drop(1)
    val data: List[DataLine] = dataLines.map { line =>
      val split = line.split(',')
      DataLine(split(0), split(1), split(2), split(3))
    }.toList
    data
  }

  def replaceData(data: List[DataLine], column: String, row: Int, newData: String): List[DataLine] = {
    val index: Int = row-1;
    val updatedData = column.toLowerCase match {
      case "filename" => data.updated(index, DataLine(" "+newData, data(index).origin, data(index).metadata, data(index).hash))
      case "origin" => data.updated(index, DataLine(data(index).fileName, " "+newData, data(index).metadata, data(index).hash))
      case "metadata" => data.updated(index, DataLine(data(index).fileName, data(index).origin, " "+newData, data(index).hash))
      case "hash" => data.updated(index, DataLine(data(index).fileName, data(index).origin, data(index).metadata, " "+newData))
    }
    updatedData
  }
}


object runnable extends App {
  //Import the data, need to specify where the csv is saved
  val data = Data.importMethod("")
  //Replace the incorrect data and then print the results to the terminal
  Data.replaceData(data, "origin", 3, "London").foreach(x => println(x))
}