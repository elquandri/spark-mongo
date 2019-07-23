import java.io.BufferedOutputStream
import com.mongodb.spark._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.bson.Document
import resultcheck._

object sparkMongo extends App  with sparkConfiguration {

  val docs = """
      {"name": "Bilbo Baggins", "age": 50}
      {"name": "Gandalf", "age": 1000}
      {"name": "Thorin", "age": 195}
      {"name": "Balin", "age": 178}
      {"name": "Kíli", "age": 77}
      {"name": "Dwalin", "age": 169}
      {"name": "Óin", "age": 167}
      {"name": "Glóin", "age": 158}
      {"name": "Fíli", "age": 82}
      {"name": "Bombur"}""".trim.stripMargin.split("[\\r\\n]+").toSeq

    initifile()
    mongoWrite()
    checkresult()





  def mongoWrite(){

    spark.sparkContext.parallelize(docs.map(Document.parse)).saveToMongoDB()

  }



  def checkresult(): Unit = {

    import spark.implicits._
    var c=""

    val dfRead = MongoSpark.load(spark)


    if (dfRead.count()==docs.toDF().count())
    {
      c = generate("passed").toString
    }
    else if(dfRead.count()!=docs.toDF().count()) {
      c = generate("failed").toString
    }
    else {
      c = generate("bloqued").toString
    }

    saveResultFile(c)

  }

  def initifile(): Unit =
  {
    val c =generate("failed").toString
    saveResultFile(c)
  }

  def saveResultFile(content: String) = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", pathHdfs)

    val date = java.time.LocalDate.now.toString
    val filePath = pathResult + resultPrefix + "_" + date + ".json"

    val fs = FileSystem.get(conf)
    val output = fs.create(new Path(filePath), true)

    val writer = new BufferedOutputStream(output)

    try {
      writer.write(content.getBytes("UTF-8"))
    }
    finally {
      writer.close()
    }
  }

}
