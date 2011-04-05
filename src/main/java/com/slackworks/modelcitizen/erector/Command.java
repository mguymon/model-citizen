package com.slackworks.modelcitizen.erector;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Status of {@link AbstractPolicy#process(ModelFactory, Blueprint, ModelField, Object)}
 * 
 */
public abstract class Command {

	protected final String command;
	
	/**
	 * Skip all injection
	 */
	public final static Command SKIP_INJECTION = new Command( "skip-injection" ) {};
	
	/**
	 * Skip reference injection
	 */
	public final static Command SKIP_REFERENCE_INJECTION = new Command( "skip-reference-injection" ) {};
	
	/**
	 * Skip blueprint injection
	 */
	public final static Command SKIP_BLUEPRINT_INJECTION = new Command( "skip-blueprint-injection" ) {};
	
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		return true;
	}
	
	public String toString() {
		return new ToStringBuilder(this).
	       append("command", command).
	       toString();
	}
	
}
