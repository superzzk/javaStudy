package org.example.classloader.plugin.main;

import java.net.URL;
import java.net.URLClassLoader;

// The application
public class Application {
	public static void main(String arg[]) throws Exception {
		// Use the initial version of CommonClass in the main application
		System.out.println("Main says: " + new CommonClass().toString());
//		PostDelegationClassLoader classLoader = new PostDelegationClassLoader();
//		Plugin plugin = (Plugin) classLoader.loadClass("org.example.classloader.plugin.plugin.PluginImpl").newInstance();
//

		// Instantiate the PostDelegationClassLoader with a classpath pointing to a plugin
		URL[] classpath = new URL[] { new URL("file:///D:\\tmp\\") };
		URLClassLoader urlClassLoader = new URLClassLoader(classpath);
		PostDelegationClassLoader2 classLoader = new PostDelegationClassLoader2(classpath);


		// Use the classloader to instantiate the plugin in the classloader.  Since this is done
		// by reflection in a classloader, there is no need for the main application to have
		// the PluginImpl class at compile time, although it must have the Plugin interface.
		Plugin plugin = (Plugin) classLoader.loadClass("org.example.classloader.plugin.plugin.PluginImpl").newInstance();

		// Use the plugin, which will use a different version of CommonClass
		System.out.println("Plugin says: " + plugin.test());
	}
}