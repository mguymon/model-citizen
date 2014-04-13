package com.tobedevoured.modelcitizen.erector;

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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Command used to control injection of values into the model
 */
public abstract class Command {

	private final String command;
	
	/**
	 * Skip all injection
	 */
	public static final Command SKIP_INJECTION = new Command( "skip-injection" ) {};
	
	/**
	 * Skip reference injection
	 */
	public static final Command SKIP_REFERENCE_INJECTION = new Command( "skip-reference-injection" ) {};
	
	/**
	 * Skip blueprint injection
	 */
	public static final Command SKIP_BLUEPRINT_INJECTION = new Command( "skip-blueprint-injection" ) {};
	
	public String getCommand() {
		return command;
	}
	
	protected Command( String command ) {
		this.command = command;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Command other = (Command) obj;
		if (command == null) {
			if (other.command != null) {
				return false;
			}
		} else if (!command.equals(other.command)) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return new ToStringBuilder(this).
	       append("command", command).
	       toString();
	}
	
}
