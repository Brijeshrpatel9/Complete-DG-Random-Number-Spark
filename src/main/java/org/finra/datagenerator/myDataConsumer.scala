package org.finra.datagenerator

import org.finra.datagenerator.consumer.{DataPipe, DataConsumer}
import java.util
import org.finra.datagenerator.writer.DataWriter

import scala.collection.JavaConversions._

/**
 * Created by Brijesh on 6/11/2015.
 */
class myDataConsumer extends DataConsumer with java.io.Serializable {

  val dataWriter = new myDataWriter
  val dataPipe = new myDataPipe

  override def consume(initialVars: util.Map[String, String]): Int = {

    for (ent <- initialVars.entrySet) {
      dataPipe.getDataMap.put(ent.getKey, ent.getValue)
    }
    
    // Writing Output
    dataWriter.writeOutput(dataPipe)
    
    1   // return Integer
  }
}
