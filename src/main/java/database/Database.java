package database;

import command.BeginCommand;
import command.Command;
import command.CommitCommand;
import command.CountCommand;
import command.DeleteCommand;
import command.EndCommand;
import command.GetCommand;
import command.RollbackCommand;
import command.SetCommand;
import command.UnknownCommand;

/**
 *
 */
public class Database {
    private Scope currentScope;

    public Database() {
        this.currentScope = new Scope();
    }

    public Result execute(Command command) {
        switch(command.getKind()) {
            case GET:
                return execute((GetCommand) command);
            case SET:
                return execute((SetCommand) command);
            case COUNT:
                return execute((CountCommand) command);
            case DELETE:
                return execute((DeleteCommand) command);
            case BEGIN:
                return execute((BeginCommand) command);
            case ROLLBACK:
                return execute((RollbackCommand) command);
            case COMMIT:
                return execute((CommitCommand) command);
            case END:
                return execute((EndCommand) command);
            case UNKNOWN:
                return execute((UnknownCommand) command);
            default:
                return execute(new UnknownCommand("Unknown command."));
        }
    }

    public Result execute(UnknownCommand command) {
        return Result.from(command.getMessage());
    }

    public Result execute(SetCommand command) {
        currentScope.insert(command.getKey(), command.getValue());
        return Result.fromVoid();
    }

    public Result execute(GetCommand command) {
        String value = currentScope.lookup(command.getKey());
        if (value == null) {
            return Result.from("NULL");
        } else {
            return Result.from(value);
        }
    }

    public Result execute(DeleteCommand command) {
        currentScope.delete(command.getKey());
        return Result.fromVoid();
    }

    public Result execute(CountCommand command) {
        int count = currentScope.count(command.getValue());
        return Result.from(count);
    }

    public Result execute(EndCommand command) {
        return Result.fromVoid();
    }


    public Result execute(BeginCommand command) {
        currentScope = currentScope.begin();
        return Result.fromVoid();
    }

    public Result execute(RollbackCommand command) {
        if (currentScope.hasPreviousScope()) {
            currentScope = currentScope.rollback();
            return Result.fromVoid();
        } else {
            return Result.from("TRANSACTION NOT FOUND");
        }
    }

    public Result execute(CommitCommand command) {
        currentScope = currentScope.commit();
        return Result.fromVoid();
    }
}
