package monopoly.objects;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogRecorder {
	public Logger logger;
	private InputStream setup = getClass().getResourceAsStream("/monopoly/files/loggerSetup.properties");
	
	public LogRecorder(Class<?> clazz) {
		logger = Logger.getLogger(clazz.getName());
		try (InputStream file = setup){
			LogManager.getLogManager().readConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
			 logger.log(Level.SEVERE, "Unable to read the file");
		}
	}
	
	public void log(Level level,String Message) {
		logger.log(level, logger.getName() + "   " + Message);
	}
}
