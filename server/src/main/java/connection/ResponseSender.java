package connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ResponseSender {
    private static final Logger logger = Logger.getLogger(ResponseSender.class.getName());

    public static void sendResponse(MusicBandResponse response, SocketChannel channel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        logger.info("Sent response to " + channel);
    }
}
