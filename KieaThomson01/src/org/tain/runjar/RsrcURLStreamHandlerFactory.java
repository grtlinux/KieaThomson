package org.tain.runjar;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.tain.utils.ClassUtils;

public final class RsrcURLStreamHandlerFactory implements URLStreamHandlerFactory {

	private static final boolean flag = true;

	private ClassLoader classLoader = null;
	private URLStreamHandlerFactory factory = null;

	///////////////////////////////////////////////////////////////////////////

	public RsrcURLStreamHandlerFactory(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void setURLStreamHandlerFactory(URLStreamHandlerFactory factory) {
		this.factory = factory;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {

		if (!flag) System.out.printf(">>>>> %s%n", ClassUtils.getClassInfo());

		if ("rsrc".equals(protocol))
			return new RsrcURLStreamHandler(classLoader);

		if (factory != null)
			return factory.createURLStreamHandler(protocol);

		return null;
	}
}
