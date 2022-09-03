import console.MusicBandConsoleClient;

public class ConsoleClientMain {
    public static void main(String[] args) {
        MusicBandConsoleClient client = new MusicBandConsoleClient("127.0.0.1", 4321);
        client.launch();
    }
}
