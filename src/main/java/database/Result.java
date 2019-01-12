package database;

/**
 *
 */
public class Result {
    private String result;

    private Result() {
    }

    public static Result from(String s) {
        return new Result(s);
    }
    public static Result from(int count) {
        return new Result(String.valueOf(count));
    }

    public static Result fromVoid() {
        return new Result();
    }

    private Result(String result) {
        this.result = result;
    }


    public boolean isVoid() {
        return result == null;
    }

    public String getResult() {
        return result;
    }
}
