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
 * A {@link MappedList} annotated Field in the {@link Blueprint} that is mapped 
 * to a {@link List} comprised of Models with a registered {@Blueprint}. 
 */
public class MappedListField extends ModelField {

	private int size;
	private Class targetList;
	
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

	public Class getTargetList() {
		return targetList;
	}

	public void setTargetList(Class targetList) {
		this.targetList = targetList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + size;
		if ( targetList != null ) {
			result = prime * result + targetList.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappedListField other = (MappedListField) obj;
		if (size != other.size)
			return false;
		
		if ( targetList != null ) {
			if ( !targetList.equals( other.getTargetList() ) ) {
				return false;
			}
		} else if ( other.getTargetList() != null ) {
			return false;
		}
		
		return true;
	}

}
