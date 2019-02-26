package cn.edu.iter;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Component;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-11 11:08
 **/
@Component
public class HelloConsumer {
    @Reference
    private IHelloService helloService;


}

