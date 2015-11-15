package com.sxdsf.deposit.exception;

public class FileNameTooLongException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4983916037511376772L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "The file name is too long.Please rename the file with less than 128 characters";
	}

}
