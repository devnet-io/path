/*
package io.devnet.util.path;

package io.devnet.util.path;

import jodd.bean.BeanUtil;
import jodd.pathref.Pathref;
import jodd.proxetta.ProxettaException;

import java.lang.String;
import java.util.function.Function;

*/
/**
 *
 * @author Joe Esposito <joe@devnet.io>
 *
 *//*

public class Path<CurrentReturnType, RootClass> {
    private Pathref<CurrentReturnType> currentRef;
    private Class<CurrentReturnType> clazz;
    private String path;

    public String toDogsISayToDogs() {
        return "dogs";
    }

    private Path(Class<CurrentReturnType> clazz, String path) {
        this.path = path;
        this.clazz = clazz;

        if(clazz != null) {
            currentRef = Pathref.on(clazz);
        }
    }

    public <NextReturnType> Path<NextReturnType, RootClass> $(Function<CurrentReturnType, NextReturnType> method) {
        String lastpath = this.path;

        CurrentReturnType instance = currentRef.to();

        String newPath = currentRef.path(method.apply(instance));

        Class<NextReturnType> returnType = (Class<NextReturnType>) BeanUtil.forced.getPropertyType(instance, newPath);

        */
/*Class<N> returnType = null;

        try {
            returnType = (Class<N>) clazz.getMethod(newPath).getReturnType();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*//*


        String c = lastpath != null ? lastpath + "." + newPath : newPath;

        try {
            return new Path<>(returnType, c);
        } catch (ProxettaException e) {
            return new Path<>(null, c);
        }
    }

    public String resolve() {
        return path;
    }

    public CurrentReturnType evaluate(RootClass rootInstance) {
        return BeanUtil.forced.getProperty(rootInstance, path);
    }

    public static <T> Path<T, T> of(Class<T> in) {
        return new Path<>(in, null);
    }

}

*/
