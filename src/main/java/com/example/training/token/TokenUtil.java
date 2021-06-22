package com.example.training.token;

import com.example.training.models.Role;
import com.example.training.models.User;
import com.example.training.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenUtil {
    private final UserRepository userRepository;
    private final String CLAIMS_SUBJECT ="sub";
    private final String CLAIMS_CREATED ="created";
    private final String CLAIMS_ROLE ="role";
    private final String CLAIMS_FirstName ="name";

    @Value("${auth.expiration}")
    private  Long TOKEN_VALIDITY = 604800L;

    @Value("${auth.secret}")
    private String TOKEN_SECRET;

    public TokenUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String getUserNameFromToken(String token){
        try {

            Claims claims = getClaims(token);
            return  claims.getSubject();
        }catch (Exception ex){
            return null;
        }
    }
    public String generateToken(UserDetails userDetails){

        Map<String , Object> claims = new HashMap<>();
        claims.put(CLAIMS_SUBJECT , userDetails.getUsername());
        claims.put(CLAIMS_CREATED,new Date());
        claims.put(CLAIMS_ROLE ,getRole(userDetails).getName());
        claims.put(CLAIMS_FirstName , getFirstName(userDetails));
        System.out.println(getRole(userDetails).getName());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExperationDate())
                .signWith(SignatureAlgorithm.HS256 , TOKEN_SECRET)
                .compact();

    }

    private Date generateExperationDate() {
        return  new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);

        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration =getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token){
        Claims claims;
            try{
                 claims = Jwts.parser().setSigningKey(TOKEN_SECRET)
                        .parseClaimsJws(token)
                        .getBody();
            }catch (Exception ex){
                claims = null;
            }
            return claims;
    }
    public Role getRole(UserDetails userDetails){
       User user = userRepository.findByEmail(userDetails.getUsername());

       return user.getRoles().get(0);
    }
    public String getFirstName(UserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getUsername());

        return user.getFirstName();
    }
}
