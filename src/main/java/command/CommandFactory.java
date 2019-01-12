package command;

/**
 *
 */
public class CommandFactory {

    public static Command parse(String line) {

        if (line == null || line.length() == 0) {
            return new UnknownCommand("Unknown command.");
        }
        Command result = null;

        // Parse command and arguments (if any)
        if (line.startsWith("SET")) {
            result = new SetCommand(getArg1(line), getArg2(line));
        } else if (line.startsWith("GET")) {
            result = new GetCommand(getArg1(line));
        } else if (line.startsWith("DELETE")) {
            result = new DeleteCommand(getArg1(line));
        } else if (line.startsWith("COUNT")) {
            result = new CountCommand(getArg1(line));
        } else if (line.startsWith("END")) {
            result = new EndCommand();
        } else if (line.startsWith("BEGIN")) {
            result = new BeginCommand();
        } else if (line.startsWith("ROLLBACK")) {
            result = new RollbackCommand();
        } else if (line.startsWith("COMMIT")) {
            result = new CommitCommand();
        } else {
            result = new UnknownCommand("Unrecognized command.");
        }
        return result;
    }

    // This parsing fails to detect extra parameters and is brittle.
    // Ideally I'd make this an ANTLR grammar and properly parse these objects if
    // effort warranted.
    private static String getArg1(String line) {
        // Grab what is between the first space and next space or end of line.
        String result = "";
        int separatorIndex = line.indexOf(' ');
        int nextSeparatorIndex = line.indexOf(' ', separatorIndex + 1);
        if (nextSeparatorIndex == -1) {
            result = line.substring(separatorIndex+1);
        } else {
            result = line.substring(separatorIndex+1, nextSeparatorIndex);
        }
        return result;
    }

    private static String getArg2(String line) {
        // Grab what is between the second space and next space or end of line.
        String result = "";
        int separatorIndex = line.indexOf(' ');
        int nextSeparatorIndex = line.indexOf(' ', separatorIndex + 1);
        int secondSeparatorIndex = line.indexOf(' ', nextSeparatorIndex + 1);

        if (secondSeparatorIndex == -1) {
            result = line.substring(nextSeparatorIndex+1);
        } else {
            result = line.substring(nextSeparatorIndex+1, secondSeparatorIndex);
        }
        return result;
    }
}
