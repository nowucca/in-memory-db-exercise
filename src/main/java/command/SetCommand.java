package command;

/**
 *
 */
public class SetCommand extends Command {
    private String key;
    private String value;

    public SetCommand(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Kind getKind() {
        return Kind.SET;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
