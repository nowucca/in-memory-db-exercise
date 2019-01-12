package command;

/**
 *
 */
public class GetCommand extends Command {
    private String key;
    public GetCommand(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public Kind getKind() {
        return Kind.GET;
    }
}
