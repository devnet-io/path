package io.devnet.util.path;

import jodd.bean.BeanUtil;
import jodd.pathref.Pathref;
import jodd.proxetta.ProxettaException;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Joe Esposito <joe@devnet.io>
 */

public class Path<RootType, ReturnType> {
    private enum EvalType { FIELD, METHOD };

    private Pathref<ReturnType> currentRef;
    private Class<ReturnType> clazz;
    private PathContext context;

    private Path(Class<ReturnType> clazz, PathContext context) {
        this.clazz = clazz;
        this.context = context;

        if (clazz != null) {
            currentRef = Pathref.on(clazz);
        }
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
        ReturnType instance = currentRef.to();

        String newPath = currentRef.path(method.apply(instance));

        Class<NextReturnType> returnType = (Class<NextReturnType>) BeanUtil.forced.getPropertyType(instance, newPath);

        // Class<N> returnType = null;

       /* try {
            returnType = (Class<N>) clazz.getMethod(newPath).getReturnType();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/

        this.context.pushField(newPath);

        try {
            return new Path<>(returnType, this.context);
        } catch (ProxettaException e) {
            return new Path<>(null, this.context);
        }
    }

    public String resolve() {
        return String.join(".", this.context.fieldNames);
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

    public static <T> Path<T, T> of(Class<T> in) {
        return new Path<>(in, new PathContext());
    }

    public static <T> Path<T, T> of(T instance) {
        return new Path<T, T>((Class<T>) instance.getClass(), new PathContext());
    }

}

