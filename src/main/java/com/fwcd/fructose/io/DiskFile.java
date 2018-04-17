package com.fwcd.fructose.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a file on your disk.
 * 
 * @author Fredrik
 * 
 */
public class DiskFile extends AbstractReadable {
	private final Path path;
	
	public DiskFile(URI uri) {
		this(new File(uri));
	}
	
	public DiskFile(String path, String... sub) {
		this(Paths.get(path, sub));
	}
	
	public DiskFile(File file) {
		path = file.toPath();
	}
	
	public DiskFile(Path path) {
		this.path = path;
	}
	
	@Override
	protected InputStream openStream() {
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
