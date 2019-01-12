package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 */
public class Scope {

    // null value or missing key means not present in this scope
    private Map<String,String> data = new HashMap<>();
    private Map<String,Integer> values = new HashMap<>();

    // track deleted keys in this scope
    private List<String> deletedKeys = new ArrayList<>();

    // link back to previous scopes
    Scope previous = null;


    public void insert(String keyToSet, String valueToSet) {
        if (!isKeyDeleted(keyToSet)) {
            // lose the existing value if any
            decrementExistingValueCountIfAny(keyToSet);
        }

        // ok add the new key and increment the count in this scope
        data.put(keyToSet, valueToSet);
        values.put(valueToSet, count(valueToSet) + 1);
        removeDeletedKey(keyToSet);
    }

    private void decrementExistingValueCountIfAny(String key) {
        String existingValue = lookup(key);
        if (existingValue != null) {
            values.put(existingValue, count(existingValue) - 1);
        }
    }

    public String lookup(String key) {
        if (isKeyDeleted(key)) {
            return null; // deleted stops the search
        }
        String value = getByKey(key);
        if (value == null && hasPreviousScope()) {
            value = previous.lookup(key);
        }
        return value;
    }

    private String getByKey(String key) {
        return data.getOrDefault(key, null);
    }

    private boolean isKeyDeleted(String key) {
        return deletedKeys.contains(key);
    }

    private void removeDeletedKey(String key) {
         if (isKeyDeleted(key)) {
             deletedKeys.remove(key);
         }
    }

    public void delete(String key) {
        if (isKeyDeleted(key)) {
            return;
        }
        decrementExistingValueCountIfAny(key);
        data.put(key, null);
        deletedKeys.add(key);
    }

    public boolean hasPreviousScope() {
        return previous != null;
    }

    public int count(String value) {
        Integer currentCount = values.getOrDefault(value, null);
        if (currentCount == null && hasPreviousScope()) {
            currentCount = previous.count(value);
        }
        return currentCount == null? 0: currentCount;
    }

    public Scope begin() {
        Scope newScope = new Scope();
        newScope.setPrevious(this);
        return newScope;
    }

    public Scope rollback() {
        if (!hasPreviousScope()) {
            return null;
        }
        return previous;
    }

    public Scope commit() {
        Stack<Scope> scopes = new Stack<>();
        Scope result = this;
        while(result.hasPreviousScope()) {
            scopes.push(result);
            result = result.previous;
        }
        if (!scopes.empty()) {
            Scope current = scopes.pop();
            while(!scopes.empty()) {
                current = current.mergeInto(scopes.pop());
            }
            result = current;
        }
        return result;
    }

    private Scope mergeInto(Scope other) {
        // take all our visible key/value mappings that are not null
        // and insert them into the next scope, ensuring the key not marked deleted there too.
        this.data.entrySet().stream().
            filter(this::isEntryVisible)
            .forEachOrdered((e)->{
                if (!other.isEntryVisible(e)) {
                    other.data.put(e.getKey(), e.getValue());
                    other.removeDeletedKey(e.getKey());
                }
            });

        // force recalculation of value counts when asked after commit.
        other.values.clear();

        // forget about linking back to this scope - we've grabbed all the visible values we need.
        other.previous = null;
        return other;
    }

    private boolean isEntryVisible(Map.Entry<String, String> e) {
        return !isKeyDeleted(e.getKey()) && e.getValue()!=null;
    }

    public void setPrevious(Scope previous) {
        this.previous = previous;
    }
}
