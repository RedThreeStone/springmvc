package it.lei.boot.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonAgeValidator implements ConstraintValidator<PersonAge,Integer> {
    PersonAge personAge;
    @Override
    public void initialize(PersonAge constraintAnnotation) {
        personAge=constraintAnnotation;
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
        if(personAge.minAge()>age || personAge.maxAge()<age){
            return  false;
        }else {
            return true;
        }
    }
}
