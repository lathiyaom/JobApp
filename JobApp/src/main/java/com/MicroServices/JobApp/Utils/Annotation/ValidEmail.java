package com.MicroServices.JobApp.Utils.Annotation;

import com.MicroServices.JobApp.Utils.AnnotationLogic.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Invalid Email Format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
