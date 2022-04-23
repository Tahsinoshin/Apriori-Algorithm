package apriori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    public void readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/apriori/apriori.txt"));

        while (scanner.hasNextLine()){
            String string = scanner.nextLine();
            String[] splitString = string.split(",",0);
            int[] arguments = new int[splitString.length];
            for(int i=0; i< splitString.length; i++){
                arguments[i] = Integer.parseInt(splitString[i]);
            }
            Apriori.transactions.add(new Transaction(arguments));
        }
    }


}
