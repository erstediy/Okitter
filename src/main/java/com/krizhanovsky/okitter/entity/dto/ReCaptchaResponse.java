package com.krizhanovsky.okitter.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Set;

public class ReCaptchaResponse {
    private boolean success;
    private float score;
    private String action;
    @JsonAlias("challenge_ts")
    private String challengeTs;
    private String hostname;
    @JsonAlias("error-codes")
    Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        return "ReCaptchaResponse{" +
                "success=" + success +
                ", score=" + score +
                ", action='" + action + '\'' +
                ", challengeTs='" + challengeTs + '\'' +
                ", hostname='" + hostname + '\'' +
                ", errorCodes=" + errorCodes +
                '}';
    }
}
