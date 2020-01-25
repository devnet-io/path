package io.devnet.util.path;

import io.devnet.util.path.IOperatorContexts.OperationContext;
import java.util.function.Function;

public class PathSegment<ReturnType, NextReturnType> {
    Function<ReturnType, NextReturnType> reference;
    OperationContext operationContext; // type
    boolean processed;
}
