package com.aquilesd.coursemc.services;

import com.aquilesd.coursemc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamento(PagamentoComBoleto pagtoBoleto, Date instanceDoPedido){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instanceDoPedido);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagtoBoleto.setDataPagamento(calendar.getTime());
    }
}
