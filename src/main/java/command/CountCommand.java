package command;

/**
 *
 */
public class CountCommand extends Command {
    private String value;
    public CountCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Kind getKind() {
        return Kind.COUNT;
    }
}
