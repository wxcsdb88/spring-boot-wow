import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wxcsdb88 on 2017/8/13 14:29.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppApplicationTests.class})
public class AppApplicationTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void contextLoads() {
        logger.warn(logger.getName());
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.trace("This is a trace message");
    }

}
