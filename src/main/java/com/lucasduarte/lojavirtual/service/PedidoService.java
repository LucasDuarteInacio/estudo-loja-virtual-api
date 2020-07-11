package com.lucasduarte.lojavirtual.service;

import com.lucasduarte.lojavirtual.domain.Categoria;
import com.lucasduarte.lojavirtual.domain.ItemPedido;
import com.lucasduarte.lojavirtual.domain.PagamentoComBoleto;
import com.lucasduarte.lojavirtual.domain.Pedido;
import com.lucasduarte.lojavirtual.domain.enuns.EstadoPagamento;
import com.lucasduarte.lojavirtual.repository.ItemPedidoRepository;
import com.lucasduarte.lojavirtual.repository.PagamentoRepository;
import com.lucasduarte.lojavirtual.repository.PedidoRepository;
import com.lucasduarte.lojavirtual.repository.ProdutoRepository;
import com.lucasduarte.lojavirtual.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoservice;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow( () ->  new ObjectNotFoundException(
                "Objeto não encontrado! " + id + ", Tipo: "+ Pedido.class.getName()));
    }
    @Transactional
    public Pedido insert(Pedido obj){
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoservice.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }

}
