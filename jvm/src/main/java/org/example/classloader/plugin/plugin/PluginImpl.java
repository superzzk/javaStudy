package org.example.classloader.plugin.plugin;

import org.example.classloader.plugin.main.Plugin;

public class PluginImpl implements Plugin {
	public String test() {
		return new CommonClass().toString();
	}
}
