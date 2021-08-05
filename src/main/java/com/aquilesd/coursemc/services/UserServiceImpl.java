package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Cliente;
import com.aquilesd.coursemc.repositories.ClienteRepository;
import com.aquilesd.coursemc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cli1 = clienteRepository.findByEmail(email);
        if(cli1 == null){
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(cli1.getId(), cli1.getEmail(), cli1.getSenha(), cli1.getPerfis());
    }
}
