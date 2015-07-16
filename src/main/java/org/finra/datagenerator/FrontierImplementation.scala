package org.finra.datagenerator

import java.util
import java.util.concurrent.atomic.AtomicBoolean

import scala.collection.JavaConverters._
import scala.collection.immutable.Queue
import org.finra.datagenerator.engine.Frontier


/**
 * Created by Brijesh on 5/28/2015.
 */
class FrontierImplementation extends Frontier with java.io.Serializable {

  override def searchForScenarios(javaQueue:util.Queue[util.Map[String, String]], flag: AtomicBoolean): Unit = {

    for (i <- 1 to EngineImplementation.NumberInEachFrontier) {
      val rnd = scala.util.Random
      val randomNumberString = rnd.nextInt(100000).toString

      val key = scala.util.Random.nextInt(10).toString

      val scalaMap = scala.collection.mutable.Map[String, String]()
      scalaMap += (key -> randomNumberString)

      val javaMap = scalaMap.asJava

      javaQueue.add(javaMap)
    }
  }
}
