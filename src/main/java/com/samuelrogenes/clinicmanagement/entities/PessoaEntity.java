package com.samuelrogenes.clinicmanagement.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PessoaEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nomeCompleto;
	
	@Column(length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Column(nullable = false)
	private String logradouro;
	
	@Column(nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String cidade;
	
	@Column(nullable = false)
	private String uf;
	
	@Column(nullable = false)
	private String cep;
	
	@Column(nullable = false, unique = true)
	private String telefone;
	
	@Column(nullable = false, unique = true)
	private String email;
}
