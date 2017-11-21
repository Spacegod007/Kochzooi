package mylogic;

import calculate.Edge;
import calculate.KochFractal;
import mylogic.generatefile.GenerateBinaryFile;
import mylogic.generatefile.GenerateBufferedBinaryFile;
import mylogic.generatefile.GenerateBufferedTextFile;
import mylogic.generatefile.GenerateTextFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program {

    public static void main(String[] args)
    {
        new Program();
    }

    public Program()
    {
        KochFractal kochFractal = new KochFractal();

        String message = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            
            try
            {
                message = bufferedReader.readLine();
            }
            catch (IOException e)
            {
                continue;
            }

            if (message.equalsIgnoreCase("exit"))
            {
                break;
            }

            try
            {
                int level = Integer.parseInt(message);

                if (level > 0)
                {
                    kochFractal.setLevel(level);
                    System.out.println(String.format("Selected level: %s", message));
                    System.out.println(String.format("Number of edges with level: %s%n", kochFractal.getNrOfEdges()));

                    System.out.print("Generating");
                    for (int i = 0; i < 3; i++)
                    {
                        Thread.sleep(500);
                        System.out.print(".");
                    }

                    executorService.execute(new GenerateTextFile(level));
                    executorService.execute(new GenerateBufferedTextFile(level));
                    executorService.execute(new GenerateBinaryFile(level));
                    executorService.execute(new GenerateBufferedBinaryFile(level));
                }
                else
                {
                    System.out.println("Please enter a number higher than 0");
                }
            }
            catch (Exception e)
            {
                continue;
            }
        }
    }
}
