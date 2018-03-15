package com.fwcd.fructose.io;

import java.io.InputStream;

public class ResourceFile extends AbstractReadable {
	private final String path;
	
	public ResourceFile(String resourcePath) {
		path = resourcePath;
	}

	@Override
	protected InputStream openStream() {
		return ResourceFile.class.getResourceAsStream(path);
	}
}
