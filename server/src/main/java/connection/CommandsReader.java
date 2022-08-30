package connection;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class CommandsReader {
    private static final Logger logger = Logger.getLogger(CommandsReader.class.getName());

    private final ByteBuffer buffer;

    public CommandsReader(){
        buffer = ByteBuffer.allocate(1024);
    }

    public MusicBandRequest readCommand(SocketChannel channel) throws IOException, ClassNotFoundException {
        channel.read(buffer);
        buffer.flip();
        byte[] array = new byte[buffer.remaining()];
        buffer.get(array);
        buffer.clear();
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        DataInputStream dataInputStream = new DataInputStream(bis);
        ObjectInputStream ois = new ObjectInputStream(dataInputStream);
        Object readObject = ois.readObject();
        MusicBandRequest command = (MusicBandRequest) readObject;
        logger.info("Received: " + command.name + " From client: " + channel);
        return command;
    }
}
