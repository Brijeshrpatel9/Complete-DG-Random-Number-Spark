package org.finra.datagenerator

import java._
import java.util._

import org.finra.datagenerator.consumer
import org.finra.datagenerator.consumer.{DataPipe, DataConsumer}
import org.finra.datagenerator.writer.{DefaultWriter, DataWriter}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.Queue

import org.apache.spark.{SparkConf, SparkContext}
import org.finra.datagenerator.distributor.SearchDistributor
import org.finra.datagenerator.engine.Frontier

/**
 * Created by Brijesh on 6/2/2015.
 */

class SparkDistributor extends SearchDistributor with java.io.Serializable {

  // Create Scala Queue and convert it into java list
  val scalaQueue = new mutable.Queue[util.Map[String, String]]()
  val javaQueue = new java.util.LinkedList(scalaQueue.asJava)

  // Sparl Context
  var mySparkContext: SparkContext = null

  var dataConsumer: DataConsumer = null

  var defaultDataConsumer: myDataConsumer = null

  dataConsumer = defaultDataConsumer

  def setSparkContext(spark: SparkContext): SparkContext = {
    mySparkContext = spark
    spark
  }

  // Set Data Consumer to consume output
  def setDataConsumer(dataConsumer: DataConsumer): SearchDistributor = {
    this.dataConsumer = dataConsumer
    this
  }

  // Distribiute the output to multiple instaces
  def distribute(frontierList: util.List[Frontier]): Unit = {

    println("Frontier list size = " + (frontierList.size() - 1))

    val conf: SparkConf = new SparkConf().setMaster("local[5]").setAppName("DG Spark Example")

    val mySparkContext: SparkContext = new SparkContext(conf)
    
    // Calling each frontier from frontier list
    for(frontier <- frontierList.asScala) {
      
      // Parallelize the output using SparkContext object
      mySparkContext.parallelize(1 to EngineImplementation.NumberInEachFrontier).map {
        i => searchWorker(frontier,javaQueue,true)
          produceOutput()

          val row = javaQueue.peek()
          val values = row.values().toString
          values

      }.reduce(_ + _)
    }
  }

  // It calls consume method
  def produceOutput(): Unit = {
    var lines: Int = 0

    for(rows <- javaQueue.asScala) {
      lines += dataConsumer.consume(rows)
      println()
    }
  }

  def searchWorker(frontier: Frontier, javaQueue: util.Queue[util.Map[String, String]], flag: Boolean): Unit = {

    frontier.searchForScenarios(javaQueue, null)
  }
}
