package com.jose30a2.dscommerce.services;


import com.jose30a2.dscommerce.entities.Role;
import com.jose30a2.dscommerce.entities.User;
import com.jose30a2.dscommerce.projections.UserDetailsProjection;
import com.jose30a2.dscommerce.repositories.UserRepossitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepossitory repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);

        // Si lista vacia error no encontro ningun usuario
        if(result.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(),  projection.getAuthority()));
        }

        return user;


    }
}
