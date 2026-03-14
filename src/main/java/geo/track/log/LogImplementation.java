package geo.track.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogImplementation implements Log{
    private static final Logger log = LoggerFactory.getLogger(Log.class);

    @Override
    public void info(String mensagem, Object... args) {
        log.info(mensagem, args);

    }

    @Override
    public void warn(String mensagem, Object... args) {
        log.warn(mensagem, args);

    }

    @Override
    public void error(String mensagem, Object... args) {
        log.error(mensagem, args);

    }
}
