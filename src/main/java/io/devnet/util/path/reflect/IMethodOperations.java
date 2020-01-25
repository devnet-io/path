package io.devnet.util.path.reflect;

import io.devnet.util.path.ICoreInterface;
import io.devnet.util.path.IOperatorContexts.OperationContext;
import io.devnet.util.path.PathContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface IMethodOperations<RootType, ReturnType> extends ICoreInterface<RootType, ReturnType>, IReflectOperations<RootType, ReturnType> {

    /**
     *
     * @return
     */
    default Method get() {
        return null;
    }

    /**
     *
     * @return
     */
    default String getName() {
        return null;
    }

    default Class<ReturnType> getReturnType() {
        return getContext().clazz;
    }

    default List<Annotation> getAnnotations(Collection<Class<Annotation>> filter) {

        Annotation[] annotations = get().getAnnotations();

        if(annotations != null && filter != null && !filter.isEmpty()) {
            return Arrays.stream(annotations)
                    .filter( a -> filter.stream().anyMatch(allowed -> a.getClass().isAssignableFrom(allowed)))
                    .collect(Collectors.toList());
        } else if (annotations != null) {
            return Arrays.asList(annotations);
        }

        return Collections.emptyList();
    }
}
