package io.devnet.util.path.reflect;

import io.devnet.util.path.ICoreInterface;
import jodd.bean.BeanUtil;

/**
 * Composable interface of the the tool's generic reflection related operations.
 *
 * @author Joe Esposito <joe@devnet.io>
 */
public interface IReflectOperations<RootType, ReturnType> extends
        ICoreInterface<RootType, ReturnType>,
        IAnnotationOperations<RootType, ReturnType> {

    String getName();

    default boolean exists() {
        return BeanUtil.forcedSilent.hasProperty(getContext().getRootInstance(), this.resolve());
    }

    default boolean exists(RootType rootInstance) {
        String path = this.resolve();
        return BeanUtil.forcedSilent.hasProperty(rootInstance, path);
    }
}
