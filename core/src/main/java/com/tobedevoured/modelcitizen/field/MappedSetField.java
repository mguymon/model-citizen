package com.tobedevoured.modelcitizen.field;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A MappedList annotated Field in the Blueprint that is mapped
 * to a List comprised of Models with a registered Blueprint.
 *
 * @author Michael Guymon
 */
public class MappedSetField extends ModelField {

    private int size;
    private Class targetSet;
    private boolean ignoreEmpty;

    /**
     * Size of List to create
     *
     * @return int
     */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Class getTargetSet() {
        return targetSet;
    }

    public void setTargetSet(Class targetSet) {
        this.targetSet = targetSet;
    }


    public boolean isIgnoreEmpty() {
        return ignoreEmpty;
    }

    public void setIgnoreEmpty(boolean ignoreEmpty) {
        this.ignoreEmpty = ignoreEmpty;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MappedSetField that = (MappedSetField) o;

        if (ignoreEmpty != that.ignoreEmpty) return false;
        if (size != that.size) return false;
        if (!targetSet.equals(that.targetSet)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + size;
        result = 31 * result + targetSet.hashCode();
        result = 31 * result + (ignoreEmpty ? 1 : 0);
        return result;
    }
}
