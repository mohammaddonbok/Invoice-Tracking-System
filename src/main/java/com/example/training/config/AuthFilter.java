package com.example.training.config;

import com.example.training.service.Services;
import com.example.training.service.UserDetails;
import com.example.training.token.TokenUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter  implements Filter {
    @Value("${auth.header}")
    private String TOKEN_HEADER;

    public static String VALID_METHODS = "DELETE, HEAD, GET, OPTIONS, POST, PUT";
    @Value("${origin}")
    private String origin;

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserDetails userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // get token from header
        // make sure it is valid
        System.out.println("filter is invoked");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        final String header = req.getHeader(TOKEN_HEADER);
        final SecurityContext securityContext= SecurityContextHolder.getContext();
        String originValue;
        if (req.getHeader("Origin") != null) {
            originValue = req.getHeader("Origin");
        } else if (req.getHeader("origin") != null) {
            originValue = req.getHeader("origin");
        } else {
            originValue = origin;
        }
        if(originValue.contains(origin)) {
            res.setHeader("Access-Control-Allow-Origin", originValue);
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, PUT");
            res.setHeader("Access-Control-Max-Age", "3600");
            String headers = req.getHeader("Access-Control-Request-Headers");
            if (headers == null) {
                headers = "Accept,Content-Type,If-Modified-Since,Origin,X-Requested-With,X-CSRFToken,Authorization";
            }
            res.setHeader("Access-Control-Allow-Headers", headers);
        }
        // We can do further validation of our tokens here as well
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setHeader("Allow", VALID_METHODS);
            res.setStatus(200);
        }
//        if(header != null && securityContext.getAuthentication() == null){
//            String token = header.substring("Bearer ".length());
//            String username = tokenUtil.getUserNameFromToken(token);
//            if(username != null){
//                System.out.println("user not null");
//                org.springframework.security.core.userdetails.UserDetails userDetails = userService.loadUserByUsername(username);
//                System.out.println(userDetails.getUsername());
//                if(tokenUtil.isTokenValid(token, userDetails)){
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//            }
//
//        }
        else{
            filterChain.doFilter(req , res);
        }

    }

}
