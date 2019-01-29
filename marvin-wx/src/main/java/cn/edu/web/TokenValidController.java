package cn.edu.web;

import cn.edu.utils.SHA1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-05 09:24
 **/
@RestController
public class TokenValidController {
    private String TOKEN="marhub";

    @GetMapping(value = "/token/valid")
    public String valid(@NotNull  String signature,@NotNull String timestamp,@NotNull String nonce){
        String localSignature = SHA1.gen(TOKEN, timestamp, nonce);
        return localSignature;
    }
}
