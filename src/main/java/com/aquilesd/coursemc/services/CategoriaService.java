package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Categoria;
import com.aquilesd.coursemc.repositories.CategoriaRepository;
import com.aquilesd.coursemc.services.exceptions.DataIntegrityException;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria find(Integer id){
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id" + id + ". Tipo: " +Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null); //Garantir que estamos inserir algo novo.
        return categoriaRepository.save(obj);
    }

    public Categoria update(Categoria obj){
        find(obj.getId());
        return categoriaRepository.save(obj);
    }

    public void delete(Integer id ){
        find(id);

        try{
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
                throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
        }

    }
}
