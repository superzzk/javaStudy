package org.example.classloader.plugin.main;

import java.io.*;

public class PostDelegationClassLoader extends ClassLoader {
	@Override
	public Class findClass(String name) throws ClassNotFoundException {
		Class<?> loadedClass = findLoadedClass(name);
		if(loadedClass==null){
			// Ignore parent delegation and just try to load locally
			byte[] b = loadClassFromFile(name);
			loadedClass = defineClass(name, b, 0, b.length);

			if (loadedClass == null) {
				loadedClass = super.loadClass(name);
			}
		}
		if(loadedClass==null)
			throw new ClassNotFoundException();

		return loadedClass;
	}

	private byte[] loadClassFromFile(String fileName)  {
		if(fileName.equals("PluginImpl")){
			return getBytesFromFile("D:\\tmp\\PluginImpl.class");
		}

		// common approach
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
				fileName.replace('.', File.separatorChar) + ".class");
		byte[] buffer;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		int nextValue = 0;
		try {
			while ( (nextValue = inputStream.read()) != -1 ) {
				byteStream.write(nextValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer = byteStream.toByteArray();
		return buffer;
	}

	public static byte[] getBytesFromFile(String fileName) {
		File file = new File(fileName);
		try (InputStream is = new FileInputStream(file)) {
			// precondition

			long length = file.length();
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			is.close();
			return bytes;
		} catch (Exception e) {
			System.out.println("error occurs in _ClassTransformer!"
					+ e.getClass().getName());
			return null;
		}
	}
}
