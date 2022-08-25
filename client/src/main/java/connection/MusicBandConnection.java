package connection;

import collectionitems.MusicBand;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MusicBandConnection implements Closeable {
    private Socket socket;

    public MusicBandConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public String sendCommand(String command) throws IOException, ClassNotFoundException, InterruptedException {
        MusicBandCommand musicBandCommand = new MusicBandCommand();
        musicBandCommand.name = command;
        sendToServer(musicBandCommand);
        return getResponseString(getResponse());
    }

    public String sendCommand(String command, String arg) throws IOException, ClassNotFoundException {
        MusicBandCommand musicBandCommand = new MusicBandCommand();
        musicBandCommand.name = command;
        musicBandCommand.arg = arg;
        sendToServer(musicBandCommand);
        return getResponseString(getResponse());
    }

    public String sendCommand(String command, String arg, MusicBand band) throws IOException, ClassNotFoundException {
        MusicBandCommand musicBandCommand = new MusicBandCommand();
        musicBandCommand.name = command;
        musicBandCommand.arg = arg;
        musicBandCommand.band = band;
        sendToServer(musicBandCommand);
        return getResponseString(getResponse());
    }

    @Override
    public void close() throws IOException {

    }

    private void sendToServer(MusicBandCommand musicBandCommand) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(byteArrayOutputStream);
        ous.writeObject(musicBandCommand);
        ous.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.write(byteArray);
    }

    private MusicBandResponse getResponse() throws IOException, ClassNotFoundException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(dataInputStream);
        return (MusicBandResponse) objectInputStream.readObject();
    }

    private String getResponseString(MusicBandResponse response){
        if(response.status == ResponseStatus.SUCCESS){
            return "Command executed successfully!\n" + response.response;
        }
        return "Failed to execute command!\n" + response;
    }
}
