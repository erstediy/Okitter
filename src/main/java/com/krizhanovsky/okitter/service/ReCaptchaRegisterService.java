package com.krizhanovsky.okitter.service;

import com.krizhanovsky.okitter.entity.dto.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaRegisterService{

    private final String secretKey = "6LeDxAgfAAAAAO1a_jLHfKLJtuwyq58T7Ecbiwrk";

    private final RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(ReCaptchaRegisterService.class);

    @Autowired
    public ReCaptchaRegisterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ReCaptchaResponse verify(String response) {
        String url = String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",secretKey,response);
        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(url,ReCaptchaResponse.class);

        logger.info(">>>>>>>>>>>>>>>> response after verifying : {}",reCaptchaResponse);

        if(reCaptchaResponse!=null)
            if(reCaptchaResponse.isSuccess() && (reCaptchaResponse.getScore() < 0.7 || !reCaptchaResponse.getAction().equals("register"))){
                reCaptchaResponse.setSuccess(false);
            }

        return reCaptchaResponse;
    }
}
