package io.devnet.util.path;

import jodd.bean.BeanUtil;
import jodd.methref.Methref;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface IReflectOperations<T>  {

    T getFieldType();

    Method getMethod();

}
