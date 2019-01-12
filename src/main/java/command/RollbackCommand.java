package command;

/**
 *
 */
public class RollbackCommand extends Command {
    @Override
    public Kind getKind() {
        return Kind.ROLLBACK;
    }
}
