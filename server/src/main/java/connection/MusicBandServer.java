package connection;

import data.database.QueryExecutionException;
import logic.CommandsExecutor;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class MusicBandServer {
    private static final Logger logger = Logger.getLogger(MusicBandServer.class.getName());

    private final int port;
    private final CommandsReader commandsReader;
    private final CommandsExecutor commandsExecutor;
    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);

    public MusicBandServer(int port, CommandsExecutor commandsExecutor) {
        this.port = port;
        this.commandsReader = new CommandsReader();
        this.commandsExecutor = commandsExecutor;
    }

    public void launch() throws IOException, NoSuchAlgorithmException {
        logger.info("Launching the server ");

        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            int select = selector.select();
            if(select == 0){
                continue;
            }
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()){
                SelectionKey k = selectedKeys.next();
                    if(k.channel() == ssc){
                        selectedKeys.remove();
                        SocketChannel channel = ssc.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        logger.info("Client connected " + channel);
                    }
                    else{
                        synchronized (k){
                            Future<MusicBandRequest> commandFuture = cachedThreadPool.submit(
                                    () -> {
                                        try {
                                            return commandsReader.readCommand((SocketChannel) k.channel());
                                        }
                                        catch (IOException ex){
                                            throw new IOException(ex.getMessage());
                                        }
                                    });
                            Future<AbstractMap.SimpleEntry<MusicBandResponse, MusicBandResponse>> responseFuture = fixedThreadPool.submit(()-> {
                                try {
                                    MusicBandRequest request = commandFuture.get();
                                    return commandsExecutor.executeCommand(request);
                                } catch (InterruptedException | QueryExecutionException | NoSuchAlgorithmException ex) {
                                    ex.printStackTrace();
                                    logger.info(ex.getMessage());
                                    MusicBandResponse response = new MusicBandResponse();
                                    response.status = ResponseStatus.FAIL;
                                    response.response = "Error on server";
                                    return new AbstractMap.SimpleEntry<>(response, null);
                                }
                                catch (ExecutionException ex){
                                    throw new IOException(ex.getMessage());
                                }
                            });
                            fixedThreadPool.submit(()->{
                                try{
                                    MusicBandResponse response = responseFuture.get().getKey();
                                    MusicBandResponse updateResponse = responseFuture.get().getValue();
                                    if(response != null){
                                        if(updateResponse != null){
                                            for(SelectionKey key: selector.keys()){
                                                if(key.channel() != ssc){
                                                    System.out.println("SENDING UPDATE");
                                                    ResponseSender.sendResponse(updateResponse, (SocketChannel) key.channel());
                                                }
                                            }
                                        }
                                        ResponseSender.sendResponse(response, (SocketChannel) k.channel());
                                        selectedKeys.remove();
                                    }
                                }
                                catch (InterruptedException ex) {
                                    logger.info(ex.getMessage());
                                } catch (ExecutionException|IOException ex){
                                    ex.printStackTrace();
                                    logger.info("Client disconnected " + k.channel());
                                    k.cancel();
                                }
                            });
                        }
                }
            }
        }
    }
}
