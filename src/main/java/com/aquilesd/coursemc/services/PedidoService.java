package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Pedido;
import com.aquilesd.coursemc.repositories.PedidoRepository;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido buscar(Integer id){
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id" + id + ". Tipo: " +Pedido.class.getName()));
    }
}
