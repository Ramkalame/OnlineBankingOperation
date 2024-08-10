package com.onlineBankingOperations.config;

import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.repository.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepo clientRepo;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client existingClient = clientRepo.findByEmail(username);
        return new CustomUserDetails(existingClient);
    }
}
