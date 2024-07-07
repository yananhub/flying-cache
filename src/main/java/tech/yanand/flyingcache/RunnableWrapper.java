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

/**
 * Wrap a runnable task to perform cleanup cache data in the thread.
 *
 * @author Richard Zhang
 */
public final class RunnableWrapper extends AbstractWrapper<Runnable> implements Runnable {

    /**
     * Constructor with a runnable task.
     *
     * @param task The task to wrap.
     */
    public RunnableWrapper(Runnable task) {
        super(task);
    }

    @Override
    public void run() {
        beforeExecute();
        try {
            task.run();
        } finally {
            afterExecute();
        }
    }
}
