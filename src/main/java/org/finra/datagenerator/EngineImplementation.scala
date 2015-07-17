package org.finra.datagenerator

import java.io.InputStream
import java.util

import org.apache.spark.SparkContext

import scala.collection.mutable
import scala.io

import org.finra.datagenerator.distributor.SearchDistributor
import org.finra.datagenerator.engine.{Engine, Frontier}

/**
 * Created by Brijesh on 5/26/2015.
 */

class EngineImplementation extends Engine {

  var TotalCount: Int = 0
  var frontierList = new util.LinkedList[Frontier]()

  def process(distributor: SearchDistributor): Unit  = {

    EngineImplementation.NumberInEachFrontier = TotalCount/EngineImplementation.NumSplit

    for(i <- 0 to EngineImplementation.NumSplit) {
      val fImp = new FrontierImplementation
      frontierList.add(fImp)
    }
    println(" ")

    distributor.distribute(frontierList)
  }

  def setModelByInputFileStream(inputFileStream : InputStream) : Unit = {

    val fileLines = io.Source.fromInputStream(inputFileStream).getLines()
    TotalCount = fileLines.next().toInt
    EngineImplementation.NumSplit = fileLines.next().toInt

  }

  def setModelByText(model: String) : Unit = {
    println()
  }

  def setBootstrapMin(min: Int) : Engine = {
    this
  }
}

object EngineImplementation {

  var NumberInEachFrontier: Int = 0
  var NumSplit: Int = 0

}
