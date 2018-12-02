import scala.io.Source
import java.io._

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

  //not sure if the task specified I need to rewrite the file or just show that I can update that specific element
  def exportMethod(fileName: String, output: List[DataLine]) = {
    val file = ""
    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))
    try{
      output.foreach(x => writer.write(x.fileName + "," + x.origin + "," + x.metadata + "," + x.hash + "\n"))
    }
    catch {
      case e: java.io.IOException=> println("File not found")
    }
    finally{
      writer.close()
    }
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
    //specify where the data is saved and import it
    val data = Data.importMethod("C:\\Users\\treve\\Desktop\\NationalArchiveData.txt")
    //correct the data and print the updated results to the terminal
    Data.replaceData(data, "origin", 3, "London").foreach(x => println(x))
    //can reprint out the file if necessary by uncommenting below and specifying output path
    //Data.exportMethod("", Data.replaceData(data, "origin", 3, "London"))
}