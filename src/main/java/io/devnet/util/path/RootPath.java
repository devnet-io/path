package io.devnet.util.path;

import io.devnet.util.path.reflect.IClassOperations;

public abstract class RootPath<RootType, ReturnType> extends CorePath<RootType, ReturnType> {

    protected RootPath(PathContext<RootType, ReturnType> context) {
        super(context);
    }

    public IClassOperations<RootType, ReturnType> clazz() {
        return new IClassOperations<RootType, ReturnType>(){

            @Override
            public String resolve() {
                return RootPath.this.resolve();
            }

            @Override
            public PathContext<RootType, ReturnType> getContext() {
                return RootPath.this.getContext();
            }

            public IOperatorContexts.OperationContext getOperatorContext() {
                return IOperatorContexts.OperationContext.CLASS;
            }
        };
    } // only class available in root

}
