package io.devnet.util.path;

import java.util.function.Function;

public interface ICoreInterface<RootType, ReturnType> {

    <NextReturnType> Path<RootType, NextReturnType> push(Function<ReturnType, NextReturnType> method);

    String resolve();

    PathContext getContext();

}
