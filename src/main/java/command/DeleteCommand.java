package command;

/**
 *
 */
public class DeleteCommand extends Command {
    private String key;
    public DeleteCommand(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public Kind getKind() {
        return Kind.DELETE;
    }
}
