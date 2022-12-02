package com.ruijosecj.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Cidade;
import com.ruijosecj.cursomc.domain.Cliente;
import com.ruijosecj.cursomc.domain.Endereco;
import com.ruijosecj.cursomc.domain.enums.TipoCliente;
import com.ruijosecj.cursomc.dto.ClienteDTO;
import com.ruijosecj.cursomc.dto.ClienteNewDTO;
import com.ruijosecj.cursomc.repositories.ClienteRepository;
import com.ruijosecj.cursomc.repositories.EnderecoRepository;
import com.ruijosecj.cursomc.services.exception.DataIntegrityException;
import com.ruijosecj.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = cliRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	} 
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = cliRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
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
			throw new DataIntegrityException("Não é possível excluir porque existem pedidos relacionados.");
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
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()));
		Cidade cid = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteNewDTO.getTelefone1());
		if(clienteNewDTO.getTelefone2()!= null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if(clienteNewDTO.getTelefone3()!= null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cli;
	}

}
