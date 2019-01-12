package command;

/**
 *
 */
public class CommitCommand extends Command {
    @Override
    public Kind getKind() {
        return Kind.COMMIT;
    }
}
