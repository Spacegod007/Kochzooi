package mylogic;

import calculate.Edge;
import calculate.KochFractal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private void generateTextFile()
    {
        KochFractal kochFractal = new KochFractal();
    }

    private void generateBufferedTextFile()
    {
        KochFractal kochFractal = new KochFractal();
    }

    private void generateBinaryFile()
    {
        KochFractal kochFractal = new KochFractal();
    }

    private void generateBufferedBinaryFile()
    {
        KochFractal kochFractal = new KochFractal();
    }
}
