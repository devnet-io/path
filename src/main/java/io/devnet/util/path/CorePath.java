package io.devnet.util.path;

import io.devnet.util.path.reflect.IClassOperations;
import io.devnet.util.path.IOperatorContexts.OperationContext;
import jodd.bean.BeanUtil;
import jodd.methref.Methref;
import jodd.pathref.Pathref;
import jodd.proxetta.ProxettaException;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class CorePath<RootType, ReturnType> implements ICoreInterface<RootType, ReturnType> {
    private enum EvalType { FIELD, METHOD }
    private PathContext<RootType, ReturnType> context;

    protected CorePath(PathContext<RootType, ReturnType> context) {
        this.context = context;
    }

    public <NextReturnType, Arg1> Path<RootType, NextReturnType> $(BiFunction<ReturnType, Arg1, NextReturnType> method, Arg1 arg1) {
        return $((m) -> method.apply(m, arg1));
    }

    public boolean assrt(RootType rootType, Object object) {
        ReturnType result = evaluateField(rootType);

        if (object.getClass().isAssignableFrom(result.getClass())) {
            return result.equals(object);
        }

        return false;
    }

    public <NextReturnTypeLeft, NextReturnTypeRight> Path<RootType, NextReturnTypeLeft> branch(Function<ReturnType, Boolean> expression, Function<ReturnType, NextReturnTypeLeft> left, Function<ReturnType, NextReturnTypeLeft> right) {
        if (true) {
            return $(left);
        }
        return $(right);
    }

    public <NextReturnType> Path<RootType, NextReturnType> $(Function<ReturnType, NextReturnType> method) {
        return push(method);
    }

    public <NextReturnType> Path<RootType, NextReturnType> push(Function<ReturnType, NextReturnType> method) {

        Pathref<ReturnType> pathRef = null;
        Methref<ReturnType> methref = null;

        try {
            try {
                pathRef = Pathref.on(context.clazz);
            } catch (ProxettaException e) {
                methref = Methref.on(context.clazz);
            }

            ReturnType pathInstance = null;
            String newPath = null;

            if(pathRef != null) {
                pathInstance = pathRef.to();
                newPath = pathRef.path(method.apply(pathInstance));
            }

            if(newPath == null && methref != null) {
                pathInstance = methref.to();
                newPath = methref.ref(method.apply(pathInstance));
            }

            Class<NextReturnType> returnType = (Class<NextReturnType>) BeanUtil.forced.getPropertyType(pathInstance, newPath);

            // Class<N> returnType = null;

            /* try {
                returnType = (Class<N>) clazz.getMethod(newPath).getReturnType();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }*/

            PathContext p = PathContext.pushClass(this.context, returnType);

            this.context.pushField(newPath);

            return new Path<RootType, NextReturnType>(p) {

                @Override
                public OperationContext getOperatorContext() {
                    return null; // TODO determine if this is a method or a field. the juicy stuff so far...
                }

            };

        } catch (ProxettaException e) {
            return (Path<RootType, NextReturnType>) new Path<RootType, ReturnType>(CorePath.this.context) {
                @Override
                public OperationContext getOperatorContext() {
                    return OperationContext.ERROR;
                }
            };
        }
    }

    public String resolve() {
        return String.join(".", this.context.getFieldNames());
    }

    @Override
    public PathContext<RootType, ReturnType> getContext() {
        return this.context;
    }

    public ReturnType evaluateField(RootType rootInstance) {
        return evaluateField(rootInstance, null);
    }

    public ReturnType evaluateField(RootType rootInstance, ReturnType defaultValue) {
        return evaluate(EvalType.FIELD, rootInstance, null);
    }

    public ReturnType evaluateMethod(RootType rootInstance) {
        return evaluateMethod(rootInstance, null);
    }

    public ReturnType evaluateMethod(RootType rootInstance, ReturnType defaultValue) {
        return evaluate(EvalType.METHOD, rootInstance, null);
    }

    private ReturnType evaluate(EvalType type, RootType rootInstance, ReturnType defaultValue) {
        ReturnType result = BeanUtil.forced.getProperty(rootInstance, resolve());

        if (result != null) {
            return result;
        }

        return defaultValue;
    }

    @Override
    public abstract IOperatorContexts.OperationContext getOperatorContext();
}
