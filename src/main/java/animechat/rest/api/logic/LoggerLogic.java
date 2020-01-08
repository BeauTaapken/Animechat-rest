package animechat.rest.api.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerLogic {
    private static final Logger logger = LoggerFactory.getLogger(LoggerLogic.class);

    public void errorLogging(String error){
        logger.error(error);
    }
}