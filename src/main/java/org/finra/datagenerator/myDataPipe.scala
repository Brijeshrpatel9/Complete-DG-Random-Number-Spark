package org.finra.datagenerator

import java.util

import org.finra.datagenerator.consumer.DataPipe

/**
 * Created by Brijesh on 6/15/2015.
 */

class myDataPipe extends DataPipe with  java.io.Serializable {

  var dataMap = new util.HashMap[String,String]()

  override def getDataMap: util.Map[String,String] = {
    dataMap
  }

  override def getPipeDelimited(outTemplate: Array[String]): String = {
    val sb = new StringBuilder(1024)
    for(s <- outTemplate) {
      if(sb.nonEmpty) {
        sb.append('|')
      }
      sb.append(getDataMap().get(s))
    }
    sb.toString()
  }
}
