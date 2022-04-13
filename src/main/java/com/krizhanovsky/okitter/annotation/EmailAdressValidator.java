package com.krizhanovsky.okitter.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailAdressValidator implements ConstraintValidator<EmailConstraint,String> {
    @Override
    public void initialize(EmailConstraint emailConstraint) {
        ConstraintValidator.super.initialize(emailConstraint);
    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext constraintValidatorContext) {
        return emailField != null && emailField.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }
}
