package org.example.classloader.plugin.main;

import java.net.URL;
import java.net.URLClassLoader;

public class PostDelegationClassLoader2 extends URLClassLoader {
	public PostDelegationClassLoader2(URL[] urls) {
		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		// First check whether it's already been loaded, if so use it
		Class loadedClass = findLoadedClass(name);

		// Not loaded, try to load it
		if (loadedClass == null) {
			try {
				// Ignore parent delegation and just try to load locally
				loadedClass = findClass(name);
			} catch (ClassNotFoundException e) {
				// Swallow exception - does not exist locally
			}

			// If not found locally, use normal parent delegation in URLClassloader
			if (loadedClass == null) {
				// throws ClassNotFoundException if not found in delegation hierarchy at all
				loadedClass = super.loadClass(name);
			}
		}
		// will never return null (ClassNotFoundException will be thrown)
		return loadedClass;
	}
}
