package connection;

import collectionitems.MusicBand;

import java.io.*;
import java.net.Socket;

public class MusicBandConnection implements Closeable {
    private Socket socket;
    private String username;
    private String password;

    public MusicBandConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public MusicBandResponse sendCommand(String command) throws IOException, ClassNotFoundException, InterruptedException {
        MusicBandRequest musicBandRequest = new MusicBandRequest();
        musicBandRequest.username = username;
        musicBandRequest.password = password;
        musicBandRequest.name = command;
        sendToServer(musicBandRequest);
        return getResponse();
    }

    public MusicBandResponse sendCommand(String command, String arg) throws IOException, ClassNotFoundException {
        MusicBandRequest musicBandRequest = new MusicBandRequest();
        musicBandRequest.username = username;
        musicBandRequest.password = password;
        musicBandRequest.name = command;
        musicBandRequest.arg = arg;
        sendToServer(musicBandRequest);
        return getResponse();
    }

    public MusicBandResponse sendCommand(String command, String arg, MusicBand band) throws IOException, ClassNotFoundException {
        MusicBandRequest musicBandRequest = new MusicBandRequest();
        musicBandRequest.username = username;
        musicBandRequest.password = password;
        musicBandRequest.name = command;
        musicBandRequest.arg = arg;
        musicBandRequest.band = band;
        sendToServer(musicBandRequest);
        return getResponse();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private void sendToServer(MusicBandRequest musicBandRequest) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(byteArrayOutputStream);
        ous.writeObject(musicBandRequest);
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
}
