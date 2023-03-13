package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;


public class Main {
	static Random random = new Random();
    public static void main(String[] args) {
        processArgs(args);
        
   
        int[] array = new int[5000000];
        int index=0;
        for(int arraySize=15000; arraySize<=35000; arraySize+=10000){
            System.out.println("Array size ：" + arraySize);
            array = new int[arraySize];
           
        for(int threadCount = 5; threadCount < 50; threadCount= threadCount+5) {
    
            System.out.println("The count for the thread is :::::: " + threadCount);
            
           
            for (int j = 50; j < 100; j+=5) {
            
            	int cutoff = 200 * (j + 1);
            	ParSort.cutoff=cutoff;
           
            	for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
		            long time;
            	
            	time=sortArray(array);
	            array[index]=(int) time;
	            reportResults(cutoff,time);

	            index++;
	        }
        }
    }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 0;
            for (long i : array) {
                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long sortArray(int[] array) {
        long time;
        long st = System.currentTimeMillis();
        for (int t = 0; t < 25; t++) {
            for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            
            ParSort.sort(array, 0, array.length);
        }
	    long en = System.currentTimeMillis();
	    time = (en - st);
  
	    return time;
    }

    private static void reportResults(int cutoff, long time) {

        System.out.println("Cutoff: " + cutoff + ", Average Time: " + time + "ms");
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P"))
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
