package command;

/**
 *
 */
public abstract class Command {
    private Kind kind;

    public abstract Kind getKind();

    public enum Kind {
        BEGIN("BEGIN"), ROLLBACK("ROLLBACK"), COMMIT("COMMIT"), SET("SET"), GET("GET"), DELETE("DELETE"), COUNT("COUNT"), END(
            "END"), UNKNOWN("UNKNOWN");
        private String name;
        Kind(String s) {
            this.name = s;
        }
    }

}
