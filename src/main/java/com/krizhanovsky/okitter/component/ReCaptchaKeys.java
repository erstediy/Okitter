package com.krizhanovsky.okitter.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReCaptchaKeys {

    @Value("${google.recaptcha.key.site}")
    private String site;

    @Value("${google.recaptcha.key.secret}")
    private String secret;

    @Value("${google.recaptcha.threshold}")
    private float threshold;

    public String getSite() {
        return site;
    }

    public String getSecret() {
        return secret;
    }

    public float getThreshold() {
        return threshold;
    }
}
