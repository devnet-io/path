package io.devnet.util.path;

import jodd.methref.Methref;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PathContext {
    private Stack<Methref<?>> methodStack = new Stack<>();
    List<String> fieldNames = new ArrayList<>();

    public <T> void push(Methref<T> ref) {
        this.methodStack.add(ref);
    }

    public void pushField(String fieldName) {
        this.fieldNames.add(fieldName);
    }
}
