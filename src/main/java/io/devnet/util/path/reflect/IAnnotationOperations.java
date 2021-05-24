package io.devnet.util.path.reflect;

import io.devnet.util.path.ICoreInterface;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Composable interface of the tool's annotation related operations.
 *
 * @author Joe Esposito <joe@devnet.io>
 */
public interface IAnnotationOperations<RootType, ReturnType> extends ICoreInterface<RootType, ReturnType> {

    /**
     * @param annotationClass   The desired annotation class
     * @return                  If the annotation is present at this location in the chain
     */
    default boolean hasAnnotation(Class<Annotation> annotationClass) {
        return !getAnnotations(Collections.singleton(annotationClass)).isEmpty();
    }

    /**
     *
     * @param annotationClass
     * @return
     */
    default Annotation getAnnotation(Class<Annotation> annotationClass) {
        return getAnnotations(Collections.singleton(annotationClass)).get(0);
    }

    /**
     *
     * @param annotationClasses
     * @return
     */
    default boolean hasAnnotations(Collection<Class<Annotation>> annotationClasses) {
        return getAnnotations(annotationClasses).size() == annotationClasses.size();
    }

    /**
     *
     * @return
     */
    default List<Annotation> getAnnotations() {
        return getAnnotations(null);
    }

    /**
     *
     * @param filter
     * @return
     */
    List<Annotation> getAnnotations(Collection<Class<Annotation>> filter);

}
