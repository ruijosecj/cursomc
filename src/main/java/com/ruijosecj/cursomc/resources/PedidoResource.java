package com.ruijosecj.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ruijosecj.cursomc.domain.Pedido;
import com.ruijosecj.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido pedido = pedService.find(id);
		
		return ResponseEntity.ok().body(pedido);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
		pedido = pedService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
}
