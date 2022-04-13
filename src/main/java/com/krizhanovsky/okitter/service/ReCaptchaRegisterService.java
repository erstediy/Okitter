package com.krizhanovsky.okitter.service;

import com.krizhanovsky.okitter.component.ReCaptchaKeys;
import com.krizhanovsky.okitter.entity.dto.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaRegisterService{

    private final RestTemplate restTemplate;
    private final ReCaptchaKeys reCaptchaKeys;

    private static final Logger logger = LoggerFactory.getLogger(ReCaptchaRegisterService.class);

    @Autowired
    public ReCaptchaRegisterService(RestTemplate restTemplate, ReCaptchaKeys reCaptchaKeys) {
        this.restTemplate = restTemplate;
        this.reCaptchaKeys = reCaptchaKeys;
    }

    public ReCaptchaResponse verify(String response) {
        String url = String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",reCaptchaKeys.getSecret(),response);
        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(url,ReCaptchaResponse.class);

        logger.info(">>>>>>>>>>>>>>>> response after verifying : {}",reCaptchaResponse);

        if(reCaptchaResponse!=null)
            if(reCaptchaResponse.isSuccess() && (reCaptchaResponse.getScore() < reCaptchaKeys.getThreshold() || !reCaptchaResponse.getAction().equals("register"))){
                reCaptchaResponse.setSuccess(false);
            }

        return reCaptchaResponse;
    }
}
