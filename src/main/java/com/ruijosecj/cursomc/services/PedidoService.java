package com.ruijosecj.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Pedido;
import com.ruijosecj.cursomc.repositories.PedidoRepository;
import com.ruijosecj.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRepository;

	public Pedido find(Integer id) {
		 Optional<Pedido> obj = pedRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		} 


}
