/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.oyzh.event;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventSubscriber {

    private final Method method;

    private volatile boolean initialized;

    // private final WeakReference<Method> method;

    private final WeakReference<Object> listener;

    public EventSubscriber(Method method, Object listener) {
        this.method = method;
        this.listener = new WeakReference<>(listener);
    }

    public void doInvoke(Object event) throws InvocationTargetException, IllegalAccessException {
        if (event != null && !this.isInvalid()) {
            Method method = this.method;
            Object listener = this.listener.get();
            if (!this.initialized) {
                method.setAccessible(true);
                this.initialized = true;
            }
            method.invoke(listener, event);
        }
    }

    public boolean isInvokeAble(Object event) {
        if (event != null && !this.isInvalid()) {
            Method method = this.method;
            Class<?> eventClass = method.getParameterTypes()[0];
            return eventClass == event.getClass() || event.getClass().isAssignableFrom(eventClass);
        }
        return false;
    }

    public boolean isInvalid() {
        return this.listener.get() == null;
    }

    public Object getListener() {
        return this.listener.get();
    }
}
