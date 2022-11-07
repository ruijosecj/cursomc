package com.ruijosecj.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Cliente;
import com.ruijosecj.cursomc.repositories.ClienteRepository;
import com.ruijosecj.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepository;

	public Cliente find(Integer id) {
		 Optional<Cliente> obj = cliRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		} 


}
