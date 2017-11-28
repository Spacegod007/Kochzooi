package mylogic.generatefile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Niels Verheijen on 28/11/2017.
 */
public class GenerateBufferedBinaryFileMapping {
    private static final int NUMBER_OF_BYTES = 10*1024*1024; //10 MB of data

    public GenerateBufferedBinaryFileMapping(int level)
    {
        try(RandomAccessFile memoryMappedFile = new RandomAccessFile(String.format("%sMapEdges.bin", String.valueOf(level)), "rw"))
        {

            //Mapping a file into memory
            FileChannel fc = memoryMappedFile.getChannel();
            MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, NUMBER_OF_BYTES);
                        
            //Writing into Memory Mapped File
            for (int i = 0; i < NUMBER_OF_BYTES; i++)
            {
                out.put((byte) 'X');
            }
            System.out.println("Writing to Memory Mapped File is completed");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
