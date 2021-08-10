package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Cliente;
import com.aquilesd.coursemc.repositories.ClienteRepository;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    private Random rand = new Random();

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    EmailService emailService;

    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente==null){
            throw new ObjectNotFoundException("E-mail Not Found!");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
        
        clienteRepository.save(cliente);
        
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char [] vet = new char[10];
        for(int i=0; i<10; i++) {
            vet[i] = randonChar();
        }
        return new String(vet);
    }

    private char randonChar(){
        int opt = rand.nextInt(3);

        if(opt == 0) { // Gera um digito
            return (char) (rand.nextInt(10) + 48);
        }

        else if(opt == 1) { //Gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        }

        else { //Gera letra maiuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
