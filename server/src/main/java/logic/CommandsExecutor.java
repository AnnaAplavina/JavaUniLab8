package logic;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import connection.MusicBandRequest;
import connection.MusicBandResponse;
import connection.ResponseStatus;
import data.CollectionManager;
import logic.commands.*;

import java.io.IOException;

public class CommandsExecutor {
    private CollectionManager collectionManager;

    public CommandsExecutor(CollectionManager manager){
        collectionManager = manager;
    }

    public MusicBandResponse executeCommand(MusicBandRequest command) throws IOException{
        Command executableCommand = getCommandObject(command);
        MusicBandResponse response = new MusicBandResponse();
        try {
            String executionResult = executableCommand.execute();
            response.status = ResponseStatus.SUCCESS;
            response.response = executionResult;
            return response;
        } catch (WrongArgumentException e) {
            response.status = ResponseStatus.FAIL;
            response.response = e.getMessage();
            return response;
        }
    }


    private Command getCommandObject(MusicBandRequest command){
        if(command == null){
            return null;
        }
        String commandName = command.name;
        String arg = command.arg;
        MusicBand band = command.band;

        if(commandName.equals("show")){
            return new ShowCommand(collectionManager);
        }
        if(commandName.equals("update")){
            return new UpdateCommand(arg, collectionManager, band);
        }
        if(commandName.equals("info")){
            return new InfoCommand(collectionManager);
        }
        if(commandName.equals("add")){
            return new AddCommand(collectionManager, band);
        }
        if(commandName.equals("remove_by_id")){
            return new RemoveByIdCommand(collectionManager, arg);
        }
        if(commandName.equals("clear")){
            return new ClearCommand(collectionManager);
        }
        if(commandName.equals("save")){
            return new SaveCommand(collectionManager);
        }
        if(commandName.equals("exit")){
            return new ExitCommand(collectionManager);
        }
        if(commandName.equals("insert_at")){
            return new InsertAtCommand(collectionManager, arg, band);
        }
        if(commandName.equals("insert_if_max")){
            return new InsertIfMaxCommand(collectionManager, band);
        }
        if(commandName.equals("insert_if_min")){
            return new InsertIfMinCommand(collectionManager, band);
        }
        if(commandName.equals("count_less_than_genre")){
            return new CountLessThanGenreCommand(collectionManager, arg);
        }
        if(commandName.equals("filter_starts_with_description")){
            return new FilterStartsWithDescriptionCommand(collectionManager, arg);
        }
        if(commandName.equals("print_descending")){
            return new PrintDescendingCommand(collectionManager);
        }
        else{
            return null;
        }
    }
}
