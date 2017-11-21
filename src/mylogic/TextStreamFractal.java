package mylogic;

import calculate.Edge;
import timeutil.TimeStamp;

import java.io.*;
import java.util.List;

public class TextStreamFractal {

    public static List<Edge> getEdgesFromTextWithBufferedStream(String path)
    {
        TimeStamp beginTime;
        TimeStamp endTime;

        File file = new File(path);

        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream))
        {

        }
        catch (IOException ignored)
        {

        }


        return null;
    }

    public static List<Edge> getEdgesFromTextWithoutBufferedStream(String path)
    {
        TimeStamp beginTime;
        TimeStamp endTime;
        return null;
    }

    public static void writeEdgesAsText(int level, Edge edge)
    {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.format("%sedges.txt", String.valueOf(level)), true);
             PrintStream printStream = new PrintStream(fileOutputStream))
        {
            printStream.println(edge.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeBufferedEdgesAsText(int level, Edge edge)
    {

    }
}
