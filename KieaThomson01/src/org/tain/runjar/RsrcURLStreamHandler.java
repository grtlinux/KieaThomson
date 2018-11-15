package org.tain.runjar;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.tain.utils.ClassUtils;

public final class RsrcURLStreamHandler extends URLStreamHandler {

	private static final boolean flag = true;

	private ClassLoader classLoader = null;

	///////////////////////////////////////////////////////////////////////////

	public RsrcURLStreamHandler(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	protected void parsrURL(URL url, String spec, int start, int limit) {

		if (!flag) System.out.printf(">>>>> %s%n", ClassUtils.getClassInfo());

		String file = null;

		if (spec.startsWith("rsrc:"))
			file = spec.substring(5);
		else if (url.getFile().equals("./"))
			file = spec;
		else if (url.getFile().endsWith("/"))
			file = url.getFile() + spec;
		else
			file = spec;

		setURL(url, "rsrc", "", -1, null, null, file, null, null);

		if (!flag) System.out.printf(">>>>> %s%n", ClassUtils.getClassInfo());
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		return new RsrcURLConnection(url, this.classLoader);
	}
}
