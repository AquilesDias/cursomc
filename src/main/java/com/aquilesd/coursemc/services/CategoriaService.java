package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Categoria;
import com.aquilesd.coursemc.repositories.CategoriaRepository;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria buscar(Integer id){
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id" + id + ". Tipo: " +Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null); //Garantir que estamos inserir algo novo.
        return categoriaRepository.save(obj);
    }
}
