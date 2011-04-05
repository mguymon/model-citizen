package com.slackworks.modelcitizen.field;

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
 * A {@link MappedList} annotated Field in the {@link Blueprint} that is mapped 
 * to a {@link List} comprised of Models with a registered {@Blueprint}. 
 */
public class MappedListField implements ModelField {

	private String name;
	private Object value;
	private Class fieldClass;
	private Class target;
	private int size;
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setValue( Object value ) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setTarget( Class target ) {
		this.target = target;
	}

	/**
	 * The target Class with registered {@link Blueprint} that List is comprised of.
	 * If not set, defaults to annotated field's Class.s
	 */
	public Class getTarget() {
		return target;
	}


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


	public Class getFieldClass() {
		return fieldClass;
	}


	public void setFieldClass(Class fieldClass) {
		this.fieldClass = fieldClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fieldClass == null) ? 0 : fieldClass.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + size;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappedListField other = (MappedListField) obj;
		if (fieldClass == null) {
			if (other.fieldClass != null)
				return false;
		} else if (!fieldClass.equals(other.fieldClass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
