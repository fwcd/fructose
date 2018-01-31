package com.fwcd.fructose;

import java.io.InputStream;

public class ResourceFile {
	private final String path;
	
	public ResourceFile(String resourcePath) {
		path = resourcePath;
	}
	
	public InputStream getAsStream() {
		return ResourceFile.class.getResourceAsStream(path);
	}
}
