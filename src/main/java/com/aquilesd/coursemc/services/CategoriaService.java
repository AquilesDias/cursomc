package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Categoria;
import com.aquilesd.coursemc.dto.CategoriaDTO;
import com.aquilesd.coursemc.repositories.CategoriaRepository;
import com.aquilesd.coursemc.services.exceptions.DataIntegrityException;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /*
    *
    * Page = Pag 1, Pag 2 ... Padrão é a inicial 0.
    * LinesPerPage = Linhas por página.
    * OrderBy = Ordenar por Id ou nome, etc...
    * Direction =  ASC ou DESC.
    *
    * */
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
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
        Categoria newObj = find(obj.getId());
        updateData(newObj, obj);
        return categoriaRepository.save(newObj);
    }

    public void delete(Integer id ){
        find(id);

        try{
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
                throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
        }

    }

    public Categoria fromDTO(CategoriaDTO objDto){
        return new Categoria(objDto.getId(), objDto.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj){
        newObj.setNome(obj.getNome());
    }
}
