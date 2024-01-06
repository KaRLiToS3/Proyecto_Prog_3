package monopoly.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogRecorder{
	public Logger logger;
	private Path setupPath = Paths.get("data/loggerSetup.properties").toAbsolutePath();
	private Path loggerLocation = Paths.get("data/record.log").toAbsolutePath();
	
	public LogRecorder(Class<?> clazz) {
		logger = Logger.getLogger(clazz.getName());
		try (InputStream file = Files.newInputStream(setupPath)){
			LogManager.getLogManager().readConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
			 logger.log(Level.SEVERE, "Unable to read the file");
		}
	}
	
	public void log(Level level,String Message) {
		logger.log(level, logger.getName() + "   " + Message);
	}
	
	public void readReadLogger() {
		System.out.println(loggerLocation.toString());
		try(Scanner file = new Scanner(loggerLocation.toFile())) {
			while(file.hasNextLine()) {
				System.out.println(file.nextLine());
			}
		} catch(SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Failed to load the logger, beacuse " + loggerLocation.toString() + " does not exist");
			e.printStackTrace();
		}
	}
}
