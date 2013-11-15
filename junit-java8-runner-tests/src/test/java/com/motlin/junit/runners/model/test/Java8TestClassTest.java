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

package com.motlin.junit.runners.model.test;

import com.motlin.junit.runners.Java8Runner;
import com.motlin.junit.runners.model.Java8TestClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8TestClassTest
{

    public interface Java8SuperInterface
    {
        @Test
        default void superTest()
        {
            Assert.assertTrue(true);
        }
    }

    public interface Java8SubInterface extends Java8SuperInterface
    {
        @Test
        default void subTest()
        {
            Assert.assertTrue(true);
        }
    }

    @RunWith(Java8Runner.class)
    public static class Java8Class implements Java8SubInterface
    {
    }

    @Test
    public void findDefaultMethodTests()
    {
        Java8TestClass testClass = new Java8TestClass(Java8Class.class);
        List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Test.class);
        assertThat(methods.size(), is(2));
        assertThat(methods.get(0).getName(), is("subTest"));
        assertThat(methods.get(1).getName(), is("superTest"));
    }
}
