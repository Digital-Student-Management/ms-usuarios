package com.sistemaEscolar.ms_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Punto de entrada del microservicio (contexto Spring, auto-config, escaneo de paquete base actual). */
@SpringBootApplication
public class MsUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUsuariosApplication.class, args);
	}

}
