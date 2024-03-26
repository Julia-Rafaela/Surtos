package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telefone {
	
	private Aluno aluno;
	private String numero;
	
	@Override
	public String toString() {
		return aluno +  numero;
	}	
}

