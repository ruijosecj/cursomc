package com.ruijosecj.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ruijosecj.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderComfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage msg);
}
