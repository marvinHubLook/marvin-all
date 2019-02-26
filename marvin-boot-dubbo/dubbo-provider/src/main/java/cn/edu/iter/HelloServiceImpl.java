package cn.edu.iter;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-11 10:58
 **/
@Service(interfaceClass = IHelloService.class)
@Component
public class HelloServiceImpl implements IHelloService{
    @Override
    public String echo(String name) {
        return "echo : "+name;
    }
}
