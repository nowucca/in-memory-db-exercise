# In Memory Database - Homework Exercise

This is a Java project that simulates a simple in-memory database.

To use the database, clone this repository and run

```bash
$ (TERM=dumb && gradle -q runApp)
>> 
```

You will see the “\>\>” prompt.

The tests can be run using
```bash
$ gradle clean test
```

Then on MacOS-X the test results can be seen using:
```bash
$ open build/reports/test/index.html
```

The in memory database has the following commands:

SET \[name\] \[value\]
* sets the name in the database to the given value

(documentation tbd)


## Example usage
```bash
$ (TERM=dumb && gradle -q runApp)
>> ROLLBACK
TRANSACTION NOT FOUND
>> GET a
NULL
>> SET a foo
>> BEGIN
>> SET a bar
>> COUNT foo
0
>> COUNT bar
1
>> GET a
bar
>> COMMIT
>> GET a
bar
>> END
```
