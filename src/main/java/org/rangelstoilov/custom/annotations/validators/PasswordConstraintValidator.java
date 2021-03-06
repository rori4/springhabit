package org.rangelstoilov.custom.annotations.validators;

import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;
import org.rangelstoilov.custom.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

   private DictionaryRule dictionaryRule;

   @Override
   public void initialize(ValidPassword constraintAnnotation) {
      try {
         String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
         dictionaryRule = new DictionaryRule(
                 new WordListDictionary(WordLists.createFromReader(
                         // Reader around the word list file
                         new FileReader[] {
                                 new FileReader(invalidPasswordList)
                         },
                         // True for case sensitivity, false otherwise
                         false,
                         // Dictionaries must be sorted
                         new ArraysSort()
                 )));
      } catch (IOException e) {
         throw new RuntimeException("could not load word list", e);
      }
   }

   @Override
   public boolean isValid(String password, ConstraintValidatorContext context) {
      PasswordValidator validator = new PasswordValidator(Arrays.asList(

              // at least 8 characters
              new LengthRule(8, 30),

              // at least one upper-case character
              new CharacterRule(EnglishCharacterData.UpperCase, 1),

              // at least one lower-case character
              new CharacterRule(EnglishCharacterData.LowerCase, 1),

              // at least one digit character
              new CharacterRule(EnglishCharacterData.Digit, 1),

              // no whitespace
              new WhitespaceRule(),

              // no common passwords
              dictionaryRule
      ));

      RuleResult result = validator.validate(new PasswordData(password));

      return result.isValid();
//      List all the messages
//      List<String> messages = validator.getMessages(result);
//      String messageTemplate = String.join(",", messages);
//      context.buildConstraintViolationWithTemplate(messageTemplate)
//              .addConstraintViolation()
//              .disableDefaultConstraintViolation();
   }
}
