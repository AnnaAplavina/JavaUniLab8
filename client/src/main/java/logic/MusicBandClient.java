package logic;

import collectionitems.BandCreator;
import collectionitems.WrongArgumentException;
import connection.MusicBandConnection;
import input.EndOfInputException;
import input.InputSource;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class MusicBandClient {
    private String ip;
    private int port;
    private InputSource inputSource;
    private BandCreator bandCreator;

    public MusicBandClient(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.inputSource = new InputSource();
        this.bandCreator = new BandCreator(inputSource);
    }

    public void launch(){
            try(MusicBandConnection connection = new MusicBandConnection(ip, port)) {
                //authorizing
                String username;
                String password;
                while (true){
                    String loginOrRegister = inputSource.readString("Please type in login to login" +
                            " or register to register a new user");
                    if(loginOrRegister.equals("login")){
                        username = inputSource.readString("Username");
                        password = inputSource.readString("Password");
                        connection.setUsername(username);
                        connection.setPassword(password);
                        String response = connection.sendCommand("login");
                        if(response.contains("Command executed successfully!")){
                            System.out.println(response);
                            break;
                        }
                        else {
                            System.out.println(response);
                            continue;
                        }
                    }
                    if(loginOrRegister.equals("register")){
                        username = inputSource.readString("Username");
                        String pass1 = inputSource.readString("Password");
                        String pass2 = inputSource.readString("Repeat password");
                        if(pass1.equals(pass2)){
                            password = pass1;
                            connection.setUsername(username);
                            connection.setPassword(password);
                            String response = connection.sendCommand("register");
                            if(response.contains("Command executed successfully!")){
                                System.out.println(response);
                                break;
                            }
                            else {
                                System.out.println(response);
                            }
                        }
                    }
                }

                //reading and sending commands
                while(true){
                String command = inputSource.readString("New command");
                command = command.trim();
                String argument = null;
                String[] commandArray = command.trim().split(" ", 2);
                if(commandArray.length == 2){
                    argument = commandArray[1];
                    command = commandArray[0];
                }
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
                else if(command.equals("execute_script")){
                    if(argument == null){
                        System.out.println("Please, specify the script file as an argument");
                    }
                    else{
                        try {
                            inputSource.setFile(argument);
                        } catch (WrongArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                else if(command.equals("exit")){
                    System.out.println("Exiting program...");
                    break;
                }
                else if(command.equals("info") || command.equals("show") ||
                        command.equals("clear")|| command.equals("print_descending")){
                    System.out.println(connection.sendCommand(command));
                }
                else if(command.equals("remove_by_id") || command.equals("count_less_than_genre")
                || command.equals("filter_starts_with_description")){
                    if(argument == null){
                        System.out.println("This command needs an argument");
                        continue;
                    }
                    System.out.println(connection.sendCommand(command, argument));
                }
                else if(command.equals("add") || command.equals("add_if_max") || command.equals("add_if_min")){
                    System.out.println(connection.sendCommand(command, null, bandCreator.createBand()));
                }
                else if(command.equals("update") || command.equals("insert_at")){
                    if(argument == null){
                        System.out.println("This command needs an argument");
                        continue;
                    }
                    System.out.println(connection.sendCommand(command, argument, bandCreator.createBand()));
                }
                else{
                    System.out.println("Unknown command");
                }
            }
            } catch (EndOfInputException e) {
                System.out.println(e.getMessage());
            }
            catch (SocketTimeoutException ex){
                System.out.println("Connection is lost");
            }
            catch (IOException ex){
                System.out.println("Can't connect to server");
            }
            catch (ClassNotFoundException | InterruptedException ex){
                ex.printStackTrace();
            }
    }
}

