package com.medical.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medical.entity.Authorities;
import com.medical.entity.Users;
import com.medical.service.impl.ReadonlyAuthoritiesServiceImpl;
import com.medical.service.impl.ReadonlyUsersServiceimpl;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private ReadonlyUsersServiceimpl readonlyUsersService;
	@Autowired
	private ReadonlyAuthoritiesServiceImpl readonlyAuthoritiesService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users=readonlyUsersService.getUsersByUsername(username);
		if(users==null){
			System.out.println("User not found");  
            throw new UsernameNotFoundException("Username not found"); 
		}
		return new org.springframework.security.core.userdetails.User(username, users.getPassword(),   
                users.getEnabled(), true, true, true, getGrantedAuthorities(users));
	}
	

    private List<GrantedAuthority> getGrantedAuthorities(Users users){
    	List<GrantedAuthority> authoritiess = new ArrayList<GrantedAuthority>();
    	
    	List<Authorities> list=readonlyAuthoritiesService.getAuthoritiesByUsername(users.getUsername());
        
        for(Authorities authorities : list){  
            System.out.println("UserProfile : "+authorities.getAuthority());  
            authoritiess.add(new SimpleGrantedAuthority(authorities.getAuthority()));  
        }  
        System.out.print("authoritiess :"+authoritiess);  
        return authoritiess;  
    }

}
