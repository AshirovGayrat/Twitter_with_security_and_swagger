package uz.pdp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.config.security.CustomUserDetailsService;

import java.io.IOException;



public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if ( authorization == null || authorization.isBlank() ) {
            filterChain.doFilter( request, response );
            return;
        }

        String token = authorization.substring(7);

        if ( !jwtTokenUtil.isValid(token) ) {
            filterChain.doFilter( request, response );
            return;
        }

        String username = jwtTokenUtil.getUsername(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( username, null, userDetails.getAuthorities() );
        authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter( request, response );
    }


}
