package org.finra.datagenerator

import java.io.OutputStream
import java.io.ObjectOutputStream

import org.finra.datagenerator.consumer.DataPipe
import org.finra.datagenerator.writer.DataWriter

/**
 * Created by Brijesh on 6/12/2015.
 */

class myDataWriter extends DataWriter with java.io.Serializable {

  val template: Array[String] = new Array[String](10)
  
  def writeOutput(dataPipe: DataPipe): Unit = {

    // System out as a output Stream
    val os: OutputStream = System.out
    val objectOS = new ObjectOutputStream(os)

    objectOS.write(dataPipe.getPipeDelimited(template).getBytes)
    objectOS.write("\n".getBytes)
  }
}
