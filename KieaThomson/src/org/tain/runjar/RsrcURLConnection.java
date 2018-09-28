package org.tain.runjar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import org.tain.utils.ClassUtils;

public final class RsrcURLConnection extends URLConnection {

	private static final boolean flag = true;

	private ClassLoader classLoader = null;

	///////////////////////////////////////////////////////////////////////////

	public RsrcURLConnection(URL url, ClassLoader classLoader) {
		super(url);

		this.classLoader = classLoader;
	}

	public InputStream getInputStream() throws IOException {

		String file = URLDecoder.decode(url.getFile(), "UTF-8");
		if (!flag) System.out.printf(">>>>> %s%n", ClassUtils.getClassInfo());

		InputStream is = this.classLoader.getResourceAsStream(file);
		if (is == null)
			throw new MalformedURLException("Could not open InputStream for URL '" + url + "'");

		return is;
	}
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void connect() throws IOException {
	}
}
