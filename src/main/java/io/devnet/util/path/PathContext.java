package io.devnet.util.path;

import io.devnet.util.path.IOperatorContexts.OperationContext;
import jodd.methref.Methref;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class PathContext<RootType, ReturnType> {
    private Stack<Methref<?>> methodStack = new Stack<>();
    private List<String> fieldNames = new ArrayList<>();
    private OperationContext resolveMode;
    private RootType rootInstance;
    public Class<ReturnType> clazz; // temp

    public <N> PathContext(Class<RootType> input) {
        this.clazz = (Class<RootType>) input;

        try {
            rootInstance = (RootType) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void pushField(String fieldName) {
        this.fieldNames.add(fieldName);
    }

    public static <R, Current, Next> PathContext<R, Next> pushClass(PathContext<R, Current> current, Class<Next> clazz) {
        PathContext<R, Next> next = (PathContext<R, Next>) current;
        next.clazz = clazz;
        return next;
    }

    public String lastField() {
        return this.fieldNames.get(fieldNames.size());
    }

    public List<String> getFieldNames() {
        return this.fieldNames;
    }

    public RootType getRootInstance() {
        return this.rootInstance;
    }

    public void setRootInstance(RootType rootInstance) {
        this.rootInstance = rootInstance;
    }

    private Set<PathSegment> segments;

    //public <R> process(Reducer<PathSegment> reducer...) {}
}
