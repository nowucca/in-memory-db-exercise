package database;

import command.Command;
import command.CommandFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class Main {

    public static final String PROMPT = ">> ";
    private static Database database = new Database();

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            do {
                System.out.print(PROMPT);
                String line = reader.readLine();
                if (null == line) {
                    break; // input finished
                }
                Command command = CommandFactory.parse(line);
                if (command.getKind() == Command.Kind.END) {
                    break; // told to finish
                }

                Result result = database.execute(command);
                if (!result.isVoid()) {
                    System.out.println(result.getResult());
                }
            } while (true);
        } catch (IOException e) {
            System.out.println("An unexpected error occurred: "+e.getMessage());
        }
    }
}
