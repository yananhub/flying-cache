/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.yanand.flyingcache;

import java.util.concurrent.Callable;

/**
 * Wrap a callable task to perform cleanup cache data in the thread.
 *
 * @param <V> The result type of method {@link Callable#call()}.
 *
 * @author Richard Zhang
 */
public final class CallableWrapper<V> extends AbstractWrapper<Callable<V>> implements Callable<V> {

    /**
     * Constructor with a callable task.
     *
     * @param task The task to wrap.
     */
    public CallableWrapper(Callable<V> task) {
        super(task);
    }

    @Override
    public V call() throws Exception {
        beforeExecute();
        try {
            return task.call();
        } finally {
            afterExecute();
        }
    }
}
