package org.compbox.jaywalker.gui;


public class NoFileChosenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2020956654844565870L;

	/**
	 * 
	 */
	public NoFileChosenException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public NoFileChosenException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NoFileChosenException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public NoFileChosenException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public NoFileChosenException(Throwable arg0) {
		super(arg0);
	}

}
