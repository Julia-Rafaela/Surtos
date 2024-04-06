package persistence;

import java.sql.SQLException;
<<<<<<< Updated upstream


import model.Matricula;

public interface IMatriculaDao {
	public String iudMatricula(String acao, Matricula m) throws SQLException, ClassNotFoundException;
=======
import model.Grade;

public interface IMatriculaDao {
	
	public String iudMatricula(String acao, Grade d) throws SQLException, ClassNotFoundException;

>>>>>>> Stashed changes

}
