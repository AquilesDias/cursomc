package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.Cliente;
import com.aquilesd.coursemc.domain.ItemPedido;
import com.aquilesd.coursemc.domain.PagamentoComBoleto;
import com.aquilesd.coursemc.domain.Pedido;
import com.aquilesd.coursemc.domain.enums.EstadoPagamento;
import com.aquilesd.coursemc.repositories.ItemPedidoRepository;
import com.aquilesd.coursemc.repositories.PagamentoRepository;
import com.aquilesd.coursemc.repositories.PedidoRepository;
import com.aquilesd.coursemc.repositories.ProdutoRepository;
import com.aquilesd.coursemc.security.UserSS;
import com.aquilesd.coursemc.services.exceptions.AuthorizationException;
import com.aquilesd.coursemc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    BoletoService boletoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    EmailService emailService;

    public Pedido find(Integer id){
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id" + id + ". Tipo: " +Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.setInstante(new Date());
        obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if(obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagtoBoleto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamento(pagtoBoleto, obj.getInstante());
        }

          obj = pedidoRepository.save(obj);
          pagamentoRepository.save(obj.getPagamento());

          for(ItemPedido ip : obj.getItens()){
              ip.setDesconto(0.0);
              ip.setProduto(produtoService.find(ip.getProduto().getId()));
              ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
              ip.setPedido(obj);
          }

            itemPedidoRepository.saveAll(obj.getItens());
            emailService.sendOrderConfirmationHtmlEmail(obj);
            return obj;

    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){

        UserSS user = UserService.authenticated();
        if(user==null){
            throw new AuthorizationException("Acesso Negado!");
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.find(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }
}
