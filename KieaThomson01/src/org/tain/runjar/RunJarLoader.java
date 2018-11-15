package org.tain.runjar;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.tain.utils.ClassUtils;

public final class RunJarLoader {

	private static final boolean flag;
	//private static final String className;
	//private static final String resourcePath;
	private static final ResourceBundle resourceBundle;
	
	static {
		flag = true;
		//className = new Object() {}.getClass().getEnclosingClass().getName();
		//resourcePath = className.replace('.', '/');
		resourceBundle = ResourceBundle.getBundle("resources");
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private static void run01(String[] args) throws Exception {
		if (!flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
		
		String metaLib = resourceBundle.getString("org.tain.runjar.path.lib");
		String[] jarFiles = null;
		List<URL> lstUrl = null;
		String runClassName = null;
		String[] runArgs = null;
		
		if (flag) {
			runClassName = resourceBundle.getString(String.format("org.tain.runjar.%s", args[0]));
			runArgs = Arrays.copyOfRange(args, 1, args.length);
		}
		
		if (flag) {
			// TODO KANG-20181026: reading the meaning of this sector.
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(metaLib);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				//url = new URL("file:/C:/hanwha/_TEMP/runjar/Runjar01.jar!/META-INF/lib/");
				if (flag) System.out.printf(">>>>> metaLib=%s, url=%s%n", metaLib, url);
				
				File libJars = new File(url.toURI());
				jarFiles = libJars.list();
			}
			
			if (flag) {
				// print jarFiles
				for (String jarFile : jarFiles) {
					System.out.printf(">>>>> %s%n", jarFile);
				}
			}
		}
		
		if (flag) {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL.setURLStreamHandlerFactory(new RsrcURLStreamHandlerFactory(classLoader));
			
			lstUrl = new ArrayList<URL>();
			lstUrl.add(new URL("rsrc:./"));

			for (int i=0; i < jarFiles.length; i++) {
				String rsrc = String.format("jar:rsrc:%s%s!/", metaLib, jarFiles[i]);
				if (!flag) System.out.printf(">>>>> %s%n", rsrc);

				lstUrl.add(new URL(rsrc));
			}
			
			if (!flag) System.out.println(">>>>> lstUrl: " + lstUrl);  // print lstUrl
		}
		
		if (flag) {
			ClassLoader jceClassLoader = new URLClassLoader(lstUrl.toArray(new URL[lstUrl.size()]), null);
			Thread.currentThread().setContextClassLoader(jceClassLoader);
			
			Class<?> cls = Class.forName(runClassName, true, jceClassLoader);
			Method main = cls.getMethod("main", new Class[] { runArgs.getClass() });
			main.invoke((Object) null, new Object[] { runArgs });
		}
		
		if (!flag) {
			// post execute
			if (flag) System.out.println(">>>>> POST EXIT..");
		}
	}
	
	private static void test02(String[] args) throws Exception {
		
		if (!flag) {
			File jarFile = new File(new Object() {}.getClass().getEnclosingClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	        String actualFile = jarFile.getParentFile().getAbsolutePath()+File.separator+"Name_Of_Jar_File.jar";
	        System.out.println("jarFile is : "+jarFile.getAbsolutePath());
	        System.out.println("actulaFilePath is : "+actualFile);
	        
	        actualFile = "C:/hanwha/_TEMP/runjar/RunJar01.jar";
	        final JarFile jar = new JarFile(actualFile);
	        final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
	        System.out.println("Reading entries in jar file ");
	        while(entries.hasMoreElements()) {
	            JarEntry jarEntry = entries.nextElement();
	            final String name = jarEntry.getName();
	            if (name.startsWith("META-INF/lib")) { //filter according to the path
	                System.out.println("file name is "+name);
	                System.out.println("is directory : "+jarEntry.isDirectory());
	                File scriptsFile  = new File(name);
	                System.out.println("file names are : "+scriptsFile.getAbsolutePath());

	            }
	        }
	        jar.close();
		}

		if (!flag) {
			//URL url = new URL("jar:file:/C:/hanwha/_TEMP/runjar/20181028/RunJar01.jar!/");

			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile("C:/hanwha/_TEMP/runjar/20181028/RunJar01.jar");
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (!entry.getName().startsWith("META-INF/lib"))
					continue;
				
				if (flag) System.out.println(">>>>> entry: " + entry.getName());
			}
		}
		
		if (!flag) {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("META-INF/lib");
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				//url = new URL("file:/C:/hanwha/_TEMP/runjar/Runjar01.jar!/META-INF/lib/");
				if (flag) System.out.printf(">>>>> url=%s%n", url.getPath());
				
				if (!url.getPath().contains("RunJar01.jar"))
					continue;
				
				System.out.println(">>>>> contains: " + url.getPath());
				URL url2 = new URL(url.getPath() + "/lib");
				System.out.println(">>>>> contains: " + url2.getPath());
				File libJars = new File(url2.toURI());
				String[] jarFiles = libJars.list();
				for (String file : jarFiles) {
					System.out.println(">>>>>>>>>> " + file);
				}
			}
		}
		
		if (flag) {
			// TODO-KANG-20181028: success!!!
			String jarFile = null;
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("META-INF/lib");
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				if (flag) System.out.printf(">>>>> url=[%s] %s%n", url.getProtocol(), url.getPath());
				
				if ("jar".equals(url.getProtocol())) {
					jarFile = url.getPath();
					jarFile = jarFile.substring(5, jarFile.lastIndexOf('!'));
					if (flag) System.out.printf(">>>>> jarFile:'%s'%n", jarFile);
				}
			}
			
			@SuppressWarnings("resource")
			JarFile jar = new JarFile(jarFile);
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (!entry.getName().startsWith("META-INF/lib"))
					continue;
				
				if (!entry.getName().endsWith("jar"))
					continue;
				
				if (flag) System.out.println(">>>>> entry: " + entry.getName());
			}
		}
    }
	
	private static void run03(String[] args) throws Exception {
		if (!flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
		
		String runClassName = null;
		String[] runArgs = null;

		String metaLib = resourceBundle.getString("org.tain.runjar.path.lib");
		String mainJarFile = null;
		List<String> lstSubJar = new ArrayList<String>();
		List<URL> lstUrl = null;
		
		if (flag) {
			runClassName = resourceBundle.getString(String.format("org.tain.runjar.%s", args[0]));
			runArgs = Arrays.copyOfRange(args, 1, args.length);
		}
		
		if (flag) {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(metaLib);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				if (!flag) System.out.printf(">>>>> url=[%s] %s%n", url.getProtocol(), url.getPath());
				
				if ("jar".equals(url.getProtocol())) {
					mainJarFile = url.getPath();
					mainJarFile = mainJarFile.substring(5, mainJarFile.lastIndexOf('!'));
					if (!flag) System.out.printf(">>>>> mainJarFile:'%s'%n", mainJarFile);
				}
			}
			
			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile(mainJarFile);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (!entry.getName().startsWith(metaLib) || !entry.getName().endsWith("jar"))
					continue;
				
				lstSubJar.add(entry.getName());
			}
			
			if (!flag) System.out.println(">>>>> lstSubJar: " + lstSubJar);
		}

		if (flag) {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL.setURLStreamHandlerFactory(new RsrcURLStreamHandlerFactory(classLoader));
			
			lstUrl = new ArrayList<URL>();
			lstUrl.add(new URL("rsrc:./"));

			for (String subJar : lstSubJar) {
				String rsrc = String.format("jar:rsrc:%s/%s!/", metaLib, subJar);
				if (!flag) System.out.printf(">>>>> %s%n", rsrc);
				
				lstUrl.add(new URL(rsrc));
			}
			
			if (!flag) System.out.println(">>>>> lstUrl: " + lstUrl);  // print lstUrl
		}
		
		if (flag) {
			ClassLoader jceClassLoader = new URLClassLoader(lstUrl.toArray(new URL[lstUrl.size()]), null);
			Thread.currentThread().setContextClassLoader(jceClassLoader);
			
			Class<?> cls = Class.forName(runClassName, true, jceClassLoader);
			Method main = cls.getMethod("main", new Class[] { runArgs.getClass() });
			main.invoke((Object) null, new Object[] { runArgs });
		}
		
		if (!flag) {
			// post execute
			if (flag) System.out.println(">>>>> POST EXIT..");
		}
	}
	
	public static void main(String[] args) throws Exception {
		if (!flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (!flag) {
			// test for arguments
			args = new String[] {};
			args = new String[] { "default", };
			args = new String[] { "default", "ARG-1", };
			args = new String[] { "test01", };
			args = new String[] { "test01", "ARG-1", };
			args = new String[] { "test02", };
			args = new String[] { "test03", };
		}
		
		if (flag) {
			if (args.length == 0) 
				args = new String[] { "default", };
		}
		
		if (!flag) run01(args);
		if (!flag) test02(args);
		if (flag) run03(args);
	}
}
