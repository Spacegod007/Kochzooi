package mylogic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Observable;
import java.util.Observer;

public class FractalBinaryFileMappingObserver implements Observer
{
    private static final int NUMBER_OF_BYTES = 10*1024*1024; //10 MB of data

    private int row;

    private final FileChannel fileChannel;

    public FractalBinaryFileMappingObserver(FileChannel channel) throws IOException
    {
        this.row = 1;
        this.fileChannel = channel;
        //Mapping a file into memory

    }

    @Override
    public void update(Observable o, Object arg)
    {
        try
        {
            MappedByteBuffer out = fileChannel.map(FileChannel.MapMode.READ_WRITE, row, NUMBER_OF_BYTES);

            //Writing into Memory Mapped File
            for (int i = 0; i < NUMBER_OF_BYTES; i++)
            {
                out.put(serialize(arg));
            }
            row++;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
}
