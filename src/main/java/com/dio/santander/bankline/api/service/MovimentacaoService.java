package com.dio.santander.bankline.api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.santander.bankline.api.dto.NovaMovimentacao;
import com.dio.santander.bankline.api.model.Correntista;
import com.dio.santander.bankline.api.model.Movimentacao;
import com.dio.santander.bankline.api.model.MovimentacaoTipo;
import com.dio.santander.bankline.api.repository.CorrentistaRepository;
import com.dio.santander.bankline.api.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {

	@Autowired
	private CorrentistaRepository correntistaRepository;
	
@Autowired
private MovimentacaoRepository repository;
	public void save(NovaMovimentacao novamovimentacao) {
		Movimentacao movimentacao = new Movimentacao();
		Double valor = novamovimentacao.getTipo()==MovimentacaoTipo.RECEITA ? novamovimentacao.getValor() : novamovimentacao.getValor() * -1;
//		Double valor = novamovimentacao.getValor();
//		if(novamovimentacao.getTipo() == MovimentacaoTipo.DESPESA) {
//			valor = valor *-1;
//		}
		
		movimentacao.setDataHora(LocalDateTime.now());
		movimentacao.setDescricao(movimentacao.getDescricao());
		movimentacao.setId(movimentacao.getId());
		movimentacao.setTipo(movimentacao.getTipo());
		movimentacao.setValor(valor);
		
		Correntista correntista = correntistaRepository.findById(novamovimentacao.getIdConta()).orElse(null); 
		if(correntista != null) {
			correntista.getConta().setSaldo(correntista.getConta().getSaldo() + valor);
			correntistaRepository.save(correntista);
		}
		
		repository.save(movimentacao);
	}
}
