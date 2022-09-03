package console.input;

import collectionitems.WrongArgumentException;

import java.io.*;

/**
 * This interface is for reading strings
 */
public class InputSource {
    BufferedReader reader;
    boolean fromFile;

    public InputSource(){
        reader = new BufferedReader(new InputStreamReader(System.in));
        fromFile = false;
    }

    public void setFile(String file) throws WrongArgumentException {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new WrongArgumentException("Couldn't open file");
        }
    }


    public String readString(String description) throws EndOfInputException {
        if(!fromFile){
            try {
                System.out.print(description +": ");
                return reader.readLine();
            } catch (IOException e) {
                throw new EndOfInputException("Can't read from console");
            }
        }
        else{
            try {
                String line = reader.readLine();
                if(line != null){
                    return line;
                }
                reader = null;
            } catch (IOException e) {
                System.out.println("File not available");
            }
        }
        return readString(description);
    }
}
