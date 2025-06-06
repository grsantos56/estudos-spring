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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        System.out.println("Filtro interceptou: " + servletPath);

        if (servletPath.startsWith("/tasks")) {
            var authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(401, "Unauthorized");
                return;
            }

            var authEncoded = authorization.substring("Basic ".length()).trim();
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecoded);
            String[] credentials = authString.split(":");

            if (credentials.length != 2) {
                response.sendError(401, "Invalid credentials format");
                return;
            }

            String username = credentials[0];
            String password = credentials[1];

            var user = userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "User not found");
                return;
            }

            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (passwordVerify.verified) {
                request.setAttribute("userId", user.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "Invalid password");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
