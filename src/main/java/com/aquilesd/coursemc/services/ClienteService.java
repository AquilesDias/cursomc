package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Cliente;
import com.aquilesd.coursemc.dto.ClienteDTO;
import com.aquilesd.coursemc.repositories.ClienteRepository;
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
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente find(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id" + id + ". Tipo: " +Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    /*
     *
     * Page = Pag 1, Pag 2 ... Padrão é a inicial 0.
     * LinesPerPage = Linhas por página.
     * OrderBy = Ordenar por Id ou nome, etc...
     * Direction =  ASC ou DESC.
     *
     * */
    public Page<Cliente> findPage(Integer page, Integer linePerPage, String orderBy, String direction ){
        PageRequest pageRequest = PageRequest.of(page, linePerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return clienteRepository.save(newObj);
    }

    public void delete(Integer id){
        find(id);

        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir cliente");
        }

    }

    public Cliente fromDTO(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    public void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
