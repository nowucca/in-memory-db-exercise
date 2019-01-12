package command;

/**
 *
 */
public class UnknownCommand extends Command {
    private String message;

    public UnknownCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Kind getKind() {
        return Kind.UNKNOWN;
    }
}
