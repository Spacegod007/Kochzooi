package mylogic.readFile;

import calculate.KochManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Niels Verheijen on 28/11/2017.
 */
public class ReadBufferedBinaryFileMapping {

    private static final int NUMBER_OF_BYTES = 10*1024*1024; //10 MB of data

    public ReadBufferedBinaryFileMapping(KochManager manager, int level){
        try(RandomAccessFile memoryMappedFile = new RandomAccessFile(String.format("%sMapEdges.bin", String.valueOf(level)), "r")) {

            //Mapping a file into memory
            FileChannel fc = memoryMappedFile.getChannel();
            MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_ONLY, 0, NUMBER_OF_BYTES);

            //reading 10 bytes from memory file in Java
            for (int i = 0; i < 10; i++) {
                System.out.print((char) out.get(i));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
