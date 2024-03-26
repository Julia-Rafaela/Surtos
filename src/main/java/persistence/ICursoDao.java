package persistence;

import java.sql.SQLException;


import model.Curso;

public interface ICursoDao {
	public String iudCurso(String acao, Curso c) throws SQLException, ClassNotFoundException;
}
