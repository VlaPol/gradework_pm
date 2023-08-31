package by.tms.gradework_pm.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUtil {

    public static User getCurrentUser(){
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principle instanceof User){
            return (User) principle;
        }
        return null;
    }

    public static String getRole(){
        User user = getCurrentUser();
        if(user != null) {
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                return authority.getAuthority();
            }
        }
        return null;
    }
}
