package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Curso {
	
	int codigo;
	String nome;
	String carga_horaria;
	String sigla;
	String nota_enade;
	@Override
	
	public String toString() {
		return + codigo + nome + carga_horaria + sigla + nota_enade;
	}
}
