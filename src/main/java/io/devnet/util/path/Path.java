package io.devnet.util.path;

import io.devnet.util.path.reflect.IFieldOperations;
import io.devnet.util.path.reflect.IMethodOperations;
import io.devnet.util.path.IOperatorContexts.OperationContext;

/**
 * @author Joe Esposito <joe@devnet.io>
 */

public abstract class Path<RootType, ReturnType> extends CorePath<RootType, ReturnType> {

    protected Path(PathContext<RootType, ReturnType> context) {
        super(context);
    }

    public IMethodOperations<RootType, ReturnType> method = new IMethodOperations<RootType, ReturnType>(){

        @Override
        public String resolve() {
            return Path.this.resolve();
        }

        @Override
        public PathContext<RootType, ReturnType> getContext() {
            return Path.this.getContext();
        }

        public OperationContext getOperatorContext() {
            return IOperatorContexts.OperationContext.METHOD;
        }

    }; // only class available in root

    public IFieldOperations<RootType, ReturnType> field = new IFieldOperations<RootType, ReturnType>(){

        @Override
        public String resolve() {
            return Path.this.resolve();
        }

        @Override
        public PathContext<RootType, ReturnType> getContext() {
            return Path.this.getContext();
        }

        public OperationContext getOperatorContext() {
            return IOperatorContexts.OperationContext.FIELD;
        }

    }; // only class available in root

    public static <T> RootPath<T, T> of(Class<T> in) {
        PathContext<T, T> context = new PathContext<>(in);

        return new RootPath<T, T>(context) {
            @Override
            public OperationContext getOperatorContext() {
                return OperationContext.CLASS;
            }
        };
    }

    public static <T> RootPath<T, T> of(T instance) {
        PathContext<T, T> context = new PathContext<>(instance.getClass());
        context.setRootInstance(instance);

        return new RootPath<T, T>(context) {
            @Override
            public OperationContext getOperatorContext() {
                return OperationContext.CLASS;
            }
        };
    }
}

