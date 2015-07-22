

package org.finra.datagenerator;

import java.io.*;

/**
 * Created by Brijesh on 6/1/2015.
 */

public class MainJava implements Serializable {

    public static void main(String argv[]) throws FileNotFoundException  {

        //You can define your own file "file.txt" in your directory with first line 1000000 and second line 100
        //Read imput data from file using ClassLoader and InputStream
        ClassLoader classLoader = MainJava.class.getClassLoader();

        InputStream is = new FileInputStream(classLoader.getResource("file/input.txt").getFile());

        EngineImplementation myEngineImplementation = new EngineImplementation();

        // Create object of SparkDistributor
        SparkDistributor mySparkDistributor = new SparkDistributor();

        myDataConsumer DataConsumer = new myDataConsumer();

        mySparkDistributor.setDataConsumer(DataConsumer);


        myEngineImplementation.setModelByInputFileStream(is);

        myEngineImplementation.process(mySparkDistributor);

    }
}
