package org.example.classloader.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.jar.JarFile;

public class Demo {
	interface Plugin
	{
		default void initialize()
		{
			System.out.println("Initialized "+this.getClass().getName());
		}
		default String name(){return getClass().getSimpleName();}
	}

	static HashSet<Plugin> plugins=new HashSet<>();

	public void demo() throws IOException {
		File pluginDirectory=new File("plugins"); //arbitrary directory
		if(!pluginDirectory.exists())pluginDirectory.mkdir();
		File[] files=pluginDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
		if(files!=null && files.length>0)
		{
			ArrayList<String> classes=new ArrayList<>();
			ArrayList<URL> urls=new ArrayList<>(files.length);
			for(File file:files)
			{
				JarFile jar=new JarFile(file);
				jar.stream().forEach(jarEntry -> {
					if(jarEntry.getName().endsWith(".class"))
					{
						classes.add(jarEntry.getName());
					}
				});
				URL url=file.toURI().toURL();
				urls.add(url);
			}

			URLClassLoader urlClassLoader=new URLClassLoader(urls.toArray(new URL[urls.size()]));

			classes.forEach(className->{
				try
				{
					Class cls=urlClassLoader.loadClass(className.replaceAll("/",".").replace(".class","")); //transforming to binary name
					Class[] interfaces=cls.getInterfaces();
					for(Class intface:interfaces)
					{
						if(intface.equals(Plugin.class)) //checking presence of Plugin interface
						{
							Plugin plugin=(Plugin) cls.newInstance(); //instantiating the Plugin
							plugins.add(plugin);
							break;
						}
					}
				}
				catch (Exception e){e.printStackTrace();}
			});
		}

		if(!plugins.isEmpty())
			System.out.println("loaded plugins:");
		plugins.forEach(plugin -> {
			plugin.initialize();
			System.out.println(plugin.name());
		});

	}

}
