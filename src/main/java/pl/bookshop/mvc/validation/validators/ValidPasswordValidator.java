package pl.bookshop.mvc.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private String username;
	
	@Override
	public void initialize(ValidPassword constraintAnnotation) {
		username = SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        
        try {
        	this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException exception)	 {
        	return false;
        }
        return true;
	}
}