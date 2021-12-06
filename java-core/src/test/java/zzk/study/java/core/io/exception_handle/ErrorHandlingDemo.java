package zzk.study.java.core.io.exception_handle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ErrorHandlingDemo {
	public static void main(String[] args) throws MyException {
		String fileName = "";
		InputStream input = null;
		IOException processException = null;
		try {
			input = new FileInputStream(fileName);

			//...process input stream...
		} catch (IOException e) {
			processException = e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					if (processException != null) {
						throw new MyException(processException, e,
								"Error message..." +
										fileName);
					} else {
						throw new MyException(e,
								"Error closing InputStream for file " +
										fileName);
					}
				}
			}
			if (processException != null) {
				throw new MyException(processException,
						"Error processing InputStream for file " +
								fileName);
			}
		}
	}

	private static class MyException extends Throwable {
		public MyException(IOException processException, String s) {
		}

		public MyException(IOException processException, IOException e, String s) {
		}
	}
}
