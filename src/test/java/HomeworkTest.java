import command.CommandFactory;
import database.Database;
import database.Result;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class HomeworkTest {

    private Database database = new Database();

    public static final String VOID_RESULT = "";
    @Before
    public  void before() {
        database = new Database();
    }


    private void executeScript(String[] scripts) {
        for (int commandIndex = 0; commandIndex < scripts.length-1; commandIndex +=2) {
            String commandLine = scripts[commandIndex];
            String expectedResult = scripts[commandIndex+1];
            System.out.print(String.format("Executing '%s' expecting '%s'", commandLine, printExpectedResult(expectedResult)));
            Result result = database.execute(CommandFactory.parse(commandLine));
            System.out.println(String.format(" received '%s'", printActualResult(result)));
            if (VOID_RESULT.equals(expectedResult)) {
                assertTrue(result.isVoid());
            } else {
                final String message = String.format("Unexpected result executing command %s", commandLine);
                assertEquals(message, expectedResult, result.getResult());
            }
        }
    }

    private String printActualResult(Result result) {
        return result.isVoid()?"void" : result.getResult();
    }

    private String printExpectedResult(String expectedResult) {
        return expectedResult.equals(VOID_RESULT)?"void":expectedResult;
    }

    @Test
    public void example1() {
        String[] scripts = {
            "GET a",     "NULL",
            "SET a foo", VOID_RESULT,
            "SET b foo", VOID_RESULT,
            "COUNT foo", "2",
            "COUNT bar", "0",
            "DELETE a",  VOID_RESULT,
            "COUNT foo", "1",
            "SET b baz", VOID_RESULT,
            "COUNT foo", "0",
            "GET b",     "baz",
            "GET B",     "NULL",
            "END",       VOID_RESULT};

        executeScript(scripts);
    }

    @Test
    public void example2() {
        String[] scripts = {
            "SET a foo",     VOID_RESULT,
            "SET a foo",     VOID_RESULT,
            "COUNT foo",     "1",
            "GET a",         "foo",
            "DELETE a",      VOID_RESULT,
            "GET a",         "NULL",
            "SET b baz",     VOID_RESULT,
            "COUNT foo",     "0",
            "END",           VOID_RESULT};

        executeScript(scripts);
    }

    @Test
    public void example3() {
        String[] scripts = {
            "BEGIN",         VOID_RESULT,
            "SET a foo",     VOID_RESULT,
            "GET a",         "foo",
            "BEGIN",         VOID_RESULT,
            "SET a bar",     VOID_RESULT,
            "GET a",         "bar",
            "ROLLBACK",      VOID_RESULT,
            "GET a",         "foo",
            "ROLLBACK",      VOID_RESULT,
            "GET a",         "NULL",
            "END",       VOID_RESULT};

        executeScript(scripts);
    }

    @Test
    public void example4() {
        String[] scripts = {
            "SET a foo",     VOID_RESULT,
            "SET b baz",     VOID_RESULT,
            "BEGIN",         VOID_RESULT,
            "GET a",         "foo",
            "SET a bar",     VOID_RESULT,
            "COUNT bar",     "1",
            "BEGIN",         VOID_RESULT,
            "COUNT bar",     "1",
            "DELETE a",      VOID_RESULT,
            "GET a",         "NULL",
            "COUNT bar",     "0",
            "ROLLBACK",      VOID_RESULT,
            "GET a",         "bar",
            "COUNT bar",     "1",
            "COMMIT",        VOID_RESULT,
            "GET a",         "bar",
            "GET b",         "baz",
            "END",       VOID_RESULT};

        executeScript(scripts);
    }

}
