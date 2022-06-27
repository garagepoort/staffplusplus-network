package be.garagepoort.staffplusplusnetwork.network.common.database;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
