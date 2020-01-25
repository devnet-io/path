package io.devnet.util.path.reflect;

import io.devnet.util.path.ICoreInterface;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface IClassOperations<RootType, ReturnType> extends ICoreInterface<RootType, ReturnType>, IReflectOperations<RootType, ReturnType> {

    /**
     *
     * @return
     */
    @Override
    default String getName() {
        return getContext().clazz.getName();
    }

    /**
     *
     * @return
     */
    default Class<ReturnType> getType() {
        return getContext().clazz;
    }

    /**
     *
     * @return
     */
    default List<IMethodOperations> getMethods() {
        return null;
    }

    /**
     * class always exists
     * @return
     */
    @Override
    default boolean exists() {
        return true;
    }

    /**
     * class always exists
     * @return
     */
    @Override
    default boolean exists(RootType rootInstance) {
        return true;
    }

    /**
     *
     * @param filter
     * @return
     */
    default List<Annotation> getAnnotations(Collection<Class<Annotation>> filter) {

        Annotation[] annotations = getType().getAnnotations();

        if (annotations != null && filter != null && !filter.isEmpty()) {
            return Arrays.stream(annotations)
                    .filter(a -> filter.stream().anyMatch(allowed -> a.getClass().isAssignableFrom(allowed)))
                    .collect(Collectors.toList());
        } else if (annotations != null) {
            return Arrays.asList(annotations);
        }

        return Collections.emptyList();
    }

}
