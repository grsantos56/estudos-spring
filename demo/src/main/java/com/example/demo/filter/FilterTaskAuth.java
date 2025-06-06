package com.example.demo.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.demo.users.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var autorization = request.getHeader("Authorization");
        var authEncoded = autorization.substring("Basic ".length()).trim();

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecoded);

        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        var user = this.userRepository.findByUsername(username);
        if(user == null){
            response.sendError(401);
        }else{
            var passowordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if(passowordVerify.verified) {
                filterChain.doFilter(request, response);
            }


        }

    }
}
