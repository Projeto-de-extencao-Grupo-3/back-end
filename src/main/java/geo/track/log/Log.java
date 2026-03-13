package geo.track.log;

public interface Log {
    void info(String mensagem, Object... args);
    void warn(String mensagem, Object... args);
    void error(String mensagem, Object... args);
}
