package console.util.scriptexecution;

import collectionitems.WrongArgumentException;
import connection.MusicBandConnection;
import console.input.BandCreator;
import console.input.EndOfInputException;
import console.input.InputSource;

import java.io.*;

public class ScriptExecutor {
    public static void executeScript(MusicBandConnection connection, File scriptFile)
            throws FileNotFoundException, ReadingScriptFileException, EndOfInputException,
            ClassNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        InputSource inputSource = new InputSource();
        try {
            inputSource.setFile(scriptFile.getAbsolutePath());
        }
        catch (WrongArgumentException ex){
            throw new ReadingScriptFileException();
        }
        BandCreator bandCreator = new BandCreator(inputSource);
        while (true){
            try {
                String command = reader.readLine();
                if(command != null){
                    String argument = null;
                    String[] commandArray = command.trim().split(" ", 2);
                    if(commandArray.length == 2){
                        argument = commandArray[1];
                        command = commandArray[0];
                    }
                    command = command.trim();

                    if(command.equals("help")){
                        System.out.println("help : вывести справку по доступным командам\n" +
                                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                                "add {element} : добавить новый элемент в коллекцию\n" +
                                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                                "clear : очистить коллекцию\n" +
                                "save : сохранить коллекцию в файл\n" +
                                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                                "exit : завершить программу (без сохранения в файл)\n" +
                                "insert_at index {element} : добавить новый элемент в заданную позицию\n" +
                                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                                "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного\n" +
                                "filter_starts_with_description description : вывести элементы, значение поля description которых начинается с заданной подстроки\n" +
                                "print_descending : вывести элементы коллекции в порядке убывания");
                    }
                    else if(command.equals("exit")){
                        break;
                    }
                    else if(command.equals("info") || command.equals("show") ||
                            command.equals("clear")|| command.equals("print_descending")){
                        System.out.println(connection.sendCommand(command).response);
                    }
                    else if(command.equals("remove_by_id") || command.equals("count_less_than_genre")
                            || command.equals("filter_starts_with_description")){
                        if(argument == null){
                            System.out.println("This command needs an argument");
                            continue;
                        }
                        System.out.println(connection.sendCommand(command, argument).response);
                    }
                    else if(command.equals("add") || command.equals("add_if_max") || command.equals("add_if_min")){
                        System.out.println(connection.sendCommand(command, null, bandCreator.createBand()).response);
                    }
                    else if(command.equals("update") || command.equals("insert_at")){
                        if(argument == null){
                            System.out.println("This command needs an argument");
                            continue;
                        }
                        System.out.println(connection.sendCommand(command, argument, bandCreator.createBand()).response);
                    }
                    else{
                        System.out.println("Unknown command");
                    }
                }
                else {
                    break;
                }
            }
            catch (IOException ex){
                throw new ReadingScriptFileException();
            }
        }
    }
}
