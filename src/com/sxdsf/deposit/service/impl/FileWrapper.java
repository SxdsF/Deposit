package com.sxdsf.deposit.service.impl;

import java.io.File;
import java.net.URI;

public class FileWrapper extends File{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2230823279328319036L;
	
	public FileWrapper(File dir, String name) {
		super(dir, name);
		// TODO Auto-generated constructor stub
	}

	public FileWrapper(String dirPath, String name) {
		super(dirPath, name);
		// TODO Auto-generated constructor stub
	}

	public FileWrapper(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	public FileWrapper(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}
	
	

}
