package infrastructure.logger;

public class AppLogger {
    public static void info(String tag, String message) {
        System.out.println(String.format("[INFO] [%s] %s", tag, message));
    }

    public static void error(String tag, String message, Throwable e) {
        System.err.println(String.format("[ERROR] [%s] %s - Exception: %s", tag, message, e.getMessage()));
    }
}
