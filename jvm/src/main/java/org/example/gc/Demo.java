package org.example.gc;

import java.util.*;
import java.util.stream.IntStream;

public class Demo {
	public static final Map<String, String> cache = new HashMap<>();

	/**
	 * -XX:+PrintGCDetails -Xloggc:gclog.log -Xms100M -Xmx500M -XX:+UseConcMarkSweepGC
	 * */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNext()) {
			final String next = scanner.next();
			switch (next) {
				case "fill":
					IntStream.range(0,1000_000).forEach(i->cache.put(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
					break;
				case "invalidate":
					cache.clear();
					break;
				case "gc":
					System.gc();
					break;
				case "exit":
					System.exit(0);
				default:
					System.out.println("unknown");
			}
		}
	}
}
