package cn.edu.config;

import cn.edu.system.service.TestServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/11 18:19
 **/
@Component
public class ErrorInfoApiFallback implements TestServiceApi {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public String testHello() {
        logger.error("remote error");
        return "remote error";
    }
}
