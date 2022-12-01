package com.ruijosecj.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Cliente;
import com.ruijosecj.cursomc.dto.ClienteDTO;
import com.ruijosecj.cursomc.repositories.ClienteRepository;
import com.ruijosecj.cursomc.services.exception.DataIntegrityException;
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
	public Cliente update(Cliente cliente) {
		Cliente newCliente= find(cliente.getId());
		updateData(newCliente, cliente);
		return cliRepository.save(newCliente);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
	public void delete(Integer id) {
		find(id);
		try {
			cliRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque existem entidades relacionadas.");
		}
	}
	
	public List<Cliente> findAll(){
		return cliRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy );
		return cliRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}

}
