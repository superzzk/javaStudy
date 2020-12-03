package org.example.virtualmachine;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.util.Optional;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) throws IOException, AttachNotSupportedException {
		String applicationName = "App";
		Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
				.stream()
				.filter(jvm -> {
					System.out.println("jvm:"+jvm.displayName());
					return jvm.displayName().contains(applicationName);
				})
				.findFirst().get().id());

		if(jvmProcessOpt.isPresent()){
			String jvmPid = jvmProcessOpt.get();
			System.out.println(jvmPid);
			VirtualMachine jvm = VirtualMachine.attach(jvmPid);
			jvm.detach();
		}
	}
}
