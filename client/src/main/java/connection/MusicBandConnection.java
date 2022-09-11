package connection;

import collectionitems.MusicBand;

import java.io.*;
import java.net.Socket;

public class MusicBandConnection implements Closeable {
    private Socket socket;
    private String username;
    private String password;
    private volatile MusicBandResponse response;
    private volatile Updater updater;

    public MusicBandConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        response = null;
        updater = null;
        Thread readResponseThread = new Thread(() -> {
            while (true){
                try {
                    MusicBandResponse response = getResponse();
                    if(response.status != ResponseStatus.SUCCESS && response.status != ResponseStatus.FAIL){
                        if(updater != null){
                            updater.update(response);
                        }
                    }
                    else {
                        this.response = response;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        readResponseThread.setDaemon(true);
        readResponseThread.start();
    }

    public void setUpdater(Updater updater){
        this.updater = updater;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public MusicBandResponse sendCommand(String command) throws IOException, ClassNotFoundException{
        MusicBandRequest musicBandRequest = new MusicBandRequest();
        musicBandRequest.username = username;
        musicBandRequest.password = password;
        musicBandRequest.name = command;
        sendToServer(musicBandRequest);
        while (true){
            if(response != null){
                MusicBandResponse received = response;
                response = null;
                return received;
            }
        }
    }

    public MusicBandResponse sendCommand(String command, String arg) throws IOException, ClassNotFoundException {
        MusicBandRequest musicBandRequest = new MusicBandRequest();
        musicBandRequest.username = username;
        musicBandRequest.password = password;
        musicBandRequest.name = command;
        musicBandRequest.arg = arg;
        sendToServer(musicBandRequest);
        while (true){
            if(response != null){
                MusicBandResponse received = response;
                response = null;
                return received;
            }
        }
    }

    public MusicBandResponse sendCommand(String command, String arg, MusicBand band) throws IOException, ClassNotFoundException {
        MusicBandRequest musicBandRequest = new MusicBandRequest();
        musicBandRequest.username = username;
        musicBandRequest.password = password;
        musicBandRequest.name = command;
        musicBandRequest.arg = arg;
        musicBandRequest.band = band;
        sendToServer(musicBandRequest);
        while (true){
            if(response != null){
                MusicBandResponse received = response;
                response = null;
                return received;
            }
        }
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
