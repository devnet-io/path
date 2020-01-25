package io.devnet.util.path.reflect;

import io.devnet.util.path.ICoreInterface;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface IFieldOperations<RootType, ReturnType> extends ICoreInterface<RootType, ReturnType>, IReflectOperations<RootType, ReturnType> {

    /**
     *
     * @return
     */
    @Override
    default String getName() {
        return getContext().lastField();
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
