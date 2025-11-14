package com.todoteam.todolist.filter;

import com.todoteam.todolist.config.SecurityConfig;
import com.todoteam.todolist.model.User;
import com.todoteam.todolist.service.TokenService;
import com.todoteam.todolist.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter
{
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected boolean shouldNotFilterErrorDispatch()
    {
        return false;
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch()
    {
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String path = request.getServletPath();
        if(path.startsWith("/api/v1/auth"))
        {
            System.out.println("Authentication: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if(header == null || !header.regionMatches(true, 0, "Bearer ", 0, 7))
        {
            System.out.println("Header is wrong: " + header);
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();
        if(token.isEmpty())
        {
            System.out.println("Token: " + token + " is not exist");
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = tokenService.getUserIdByToken(token);
        if(userId == null)
        {
            System.out.println("User with id" + userId + "does not exist");
            filterChain.doFilter(request, response);
            return;
        }

        User user = userService.findUserById(userId);
        if(user != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            System.out.println("Everything is ok!");

            List<SimpleGrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
                    null, auths);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        //TODO возможно стоит добавить позже request.setAttribute("userId", userId); потом
        filterChain.doFilter(request, response);
    }
}
