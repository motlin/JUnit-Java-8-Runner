/*
 * Copyright 2013 Craig Motlin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.motlin.junit.runners.model;

import org.junit.internal.MethodSorter;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Java8TestClass extends TestClass
{
    /**
     * Creates a {@code TestClass} wrapping {@code klass}. Each time this
     * constructor executes, the class is scanned for annotations, which can be
     * an expensive process (we hope in future JDK's it will not be.) Therefore,
     * try to share instances of {@code TestClass} where possible.
     *
     * @param klass
     */
    public Java8TestClass(Class<?> klass)
    {
        super(klass);
    }

    @Override
    protected void scanAnnotatedMembers(Map<Class<? extends Annotation>, List<FrameworkMethod>> methodsForAnnotations, Map<Class<? extends Annotation>, List<FrameworkField>> fieldsForAnnotations)
    {
        super.scanAnnotatedMembers(methodsForAnnotations, fieldsForAnnotations);

        getInterfaceMethodsForAnnotations(methodsForAnnotations, this.getJavaClass());
    }

    private void getInterfaceMethodsForAnnotations(Map<Class<? extends Annotation>, List<FrameworkMethod>> methodsForAnnotations, Class<?> clazz)
    {
        List<Class<?>> interfaces = getInterfaces(this.getJavaClass());
        for (Class<?> eachInterface : interfaces)
        {
            for (Method eachMethod : MethodSorter.getDeclaredMethods(eachInterface))
            {
                if (!Modifier.isAbstract(eachMethod.getModifiers()))
                {
                    addToAnnotationLists(new FrameworkMethod(eachMethod), methodsForAnnotations);
                }
            }
        }
    }

    private static List<Class<?>> getInterfaces(Class<?> testClass)
    {
        LinkedList<Class<?>> queue = new LinkedList<Class<?>>();
        queue.add(testClass);

        Set<Class<?>> visited = new HashSet<Class<?>>();
        visited.add(testClass);

        List<Class<?>> results = new ArrayList<Class<?>>();
        while (!queue.isEmpty())
        {
            Class<?> anInterface = queue.poll();
            results.add(anInterface);

            Class<?>[] parentInterfaces = anInterface.getInterfaces();
            for (Class<?> parentInterface : parentInterfaces)
            {
                if (!visited.contains(parentInterface))
                {
                    visited.add(parentInterface);
                    queue.add(parentInterface);
                }
            }
        }
        return results;
    }
}
