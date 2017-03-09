package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {

	private String configFile;
	
	public BadConfigFormatException() {
		super("Config file format error");
	}
	
	public BadConfigFormatException(String file) {
		super(file + " Config file format error");
		this.configFile = file;
		try {
			PrintWriter out = new PrintWriter("logfile.txt");
			out.write(toString());
			out.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Cannot print log to choosen file");
		}
	}
	
	public String toString() {
		return configFile + " Config file format error";
	}
}
