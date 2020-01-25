package io.devnet.util.path;

import java.util.function.Function;
import io.devnet.util.path.IOperatorContexts.OperationContext;

public interface ICoreInterface<RootType, ReturnType> {

    /*<NextReturnType> Path<RootType, NextReturnType> push(Function<ReturnType, NextReturnType> method);*/

    String resolve();

    PathContext<RootType, ReturnType> getContext();

    OperationContext getOperatorContext();

}
