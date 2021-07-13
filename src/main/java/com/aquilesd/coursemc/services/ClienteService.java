package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Cidade;
import com.aquilesd.coursemc.domain.Cliente;
import com.aquilesd.coursemc.domain.Endereco;
import com.aquilesd.coursemc.domain.enums.TipoCliente;
import com.aquilesd.coursemc.dto.ClienteDTO;
import com.aquilesd.coursemc.dto.ClienteNewDTO;
import com.aquilesd.coursemc.repositories.ClienteRepository;
import com.aquilesd.coursemc.repositories.EnderecoRepository;
import com.aquilesd.coursemc.services.exceptions.DataIntegrityException;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

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

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
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

    public Cliente fromDTO(ClienteNewDTO objDto){

        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());

        if(objDto.getTelefone2() != null){
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if(objDto.getTelefone3() != null){
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }

    public void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
