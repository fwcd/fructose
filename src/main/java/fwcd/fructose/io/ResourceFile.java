package fwcd.fructose.io;

import java.io.InputStream;

public class ResourceFile extends AbstractInputStreamable {
	private final String path;
	
	public ResourceFile(String resourcePath) {
		path = resourcePath;
	}

	@Override
	protected InputStream openStream() {
		return ResourceFile.class.getResourceAsStream(path);
	}
}
