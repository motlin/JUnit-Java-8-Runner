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

package com.motlin.junit.runners;

import com.motlin.junit.runners.model.Java8TestClass;
import org.junit.runners.model.InitializationError;

public class Java8Runner extends BlockJUnit4ClassRunner
{
    /**
     * Creates a Java8Runner to run {@code klass}
     *
     * @throws org.junit.runners.model.InitializationError if the test class is malformed.
     */
    public Java8Runner(Class<?> klass) throws InitializationError
    {
        super(klass);
    }

    @Override
    protected Java8TestClass createTestClass(Class<?> testClass) {
        return new Java8TestClass(testClass);
    }
}