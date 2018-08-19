package org.rangelstoilov.custom.validators;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.rangelstoilov.custom.annotations.EmailUnique;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistsConstraintValidator implements ConstraintValidator<EmailUnique, String> {
   private Log log = LogFactory.getLog(org.rangelstoilov.custom.validators.EmailExistsConstraintValidator.class);

   @Autowired
   private UserService userService;

   @Override
   public void initialize(EmailUnique constraintAnnotation) {

   }

   @Override
   public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
      try {
         Object user = userService.findUserEntityByEmail(email);
         if (user != null) {
            return false;
         }
      } catch (Exception e) {
         log.error(e);
      }
      return true;
   }

   public void setUserService(UserService userService) {
      this.userService = userService;
   }
}
