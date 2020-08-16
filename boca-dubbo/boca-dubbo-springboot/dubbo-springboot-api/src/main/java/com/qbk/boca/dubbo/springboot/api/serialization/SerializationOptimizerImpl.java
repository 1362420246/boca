package com.qbk.boca.dubbo.springboot.api.serialization;

import com.qbk.boca.dubbo.springboot.api.QRespon;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 要让Kryo和FST完全发挥出高性能，最好将那些需要被序列化的类注册到dubbo系统中，例如，我们可以实现如下回调接口：
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    @Override
    public Collection<Class<?>> getSerializableClasses() {
        //这里可以把所有需要进行序列化的类进行添加
        List<Class<?>> classes = new LinkedList<>();
        classes.add(QRespon.class);
        return classes;
    }
}