public class ClientMain {
    public static void main(String[] args) {
        MusicBandClient client = new MusicBandClient("127.0.0.1", 4321);
        client.launch();
    }
}
