package uz.telegraph.service.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    public void authenticate(
            Claims claims,
            HttpServletRequest request
    ) {
        String userId = claims.getSubject();
        List<String> roles =  (List<String>) claims.get("roles");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userId, null, getAuthorities(roles));
        authenticationToken.setDetails(

                new WebAuthenticationDetailsSource().buildDetails(request));


        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }



    public List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }



//    public void authenticate(Claims claims, HttpServletRequest request) {
//        String userId = claims.getSubject();
////        List<String> roles = (List<String>) claims.get("roles");
//
//        Object rolesObj = claims.get("roles");
//        List<String> roles = new ArrayList<>();
//        if (rolesObj instanceof List<?>) {
//            roles = (List<String>) rolesObj;
//            // roles ni ishlatish
//        } else {
//            System.out.println("nomalum");
//        }
//        Collection<? extends GrantedAuthority> authorities = getAuthorities(roles);
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userId, null, authorities);
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
//        return roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }


}
