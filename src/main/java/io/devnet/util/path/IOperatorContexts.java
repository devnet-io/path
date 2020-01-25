package io.devnet.util.path;

import io.devnet.util.path.reflect.IReflectOperations;

public class IOperatorContexts<RootType, ReturnType> {
    public enum OperationContext { FIELD, METHOD, CLASS, ERROR }

    public IReflectOperations<RootType, ReturnType> field;

    public IReflectOperations<RootType, ReturnType> method;

}
