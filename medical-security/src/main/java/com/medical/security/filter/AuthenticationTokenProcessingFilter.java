package com.medical.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.medical.entity.Authorities;
import com.medical.service.impl.ReadonlyAuthoritiesServiceImpl;
import com.medical.utils.CommonUtils;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean{
	
	@Autowired
	private ReadonlyAuthoritiesServiceImpl readonlyAuthoritiesService;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Map<String, String[]> parms = request.getParameterMap();
		if (parms.containsKey("token")) {
            String strToken = parms.get("token")[0]; // grab the first "token" parameter
            System.out.println("Token: " + strToken);
            
            if (CommonUtils.tokenMap.containsKey(strToken)) {
            	String username=CommonUtils.tokenMap.get(strToken);
                List<GrantedAuthority> authoritiess = new ArrayList<GrantedAuthority>();
                
                List<Authorities> list=readonlyAuthoritiesService.getAuthoritiesByUsername(username);
                
                for(Authorities authorities : list){  
                    System.out.println("UserProfile : "+authorities.getAuthority());  
                    authoritiess.add(new SimpleGrantedAuthority(authorities.getAuthority()));  
                }

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, "test");
                token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, "test", authoritiess); //this.authenticationProvider.authenticate(token);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                System.out.println("invalid token");
            }
        } else {
            System.out.println("no token found");
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
	}
	
}
