package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;

import model.Telefone;


public class TelefoneDao implements ICrud<Telefone>, ITelefoneDao {
	private GenericDao gDao;

	public TelefoneDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public void inserir(Telefone t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO telefone VALUES (?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, t.getAluno().getCpf());
		ps.setString(2, t.getNumero());
		ps.execute();
		ps.close();
		c.close();

	}

	@Override
	public void atualizar(Telefone t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE telefone SET aluno = ?, numero = ? ";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, t.getAluno().getCpf());
		ps.setString(2, t.getNumero());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void excluir(Telefone t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE viagem WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, t.getNumero());
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public Telefone consultar(Telefone t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.cpf AS CpfAluno, t.numero AS numero");
		sql.append("FROM telefone t ");
		sql.append("INNER JOIN aluno a ON t.cpf = a.cpf ");
		sql.append("WHERE t.numero = ?");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setString(1, t.getNumero());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Aluno a = new Aluno();
			a.setCpf(rs.getString("CpfAluno"));
		}
		rs.close();
		ps.close();
		c.close();

		return t;
		
	}

	@Override
	public List<Telefone> listar() throws SQLException, ClassNotFoundException {
		List<Telefone> telefones = new ArrayList<>();
		Connection c = gDao.getConnection();
	    String sql ="SELECT * FROM t_listar";

		PreparedStatement ps = c.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Telefone tel = new Telefone();

			Aluno a = new Aluno();
			a.setCpf(rs.getString("CpfAluno"));
			
			tel.setNumero(rs.getString("numero"));
		

			telefones.add(tel);
		}

		rs.close();
		ps.close();
		c.close();

		return telefones;
	}
	@Override
	public String iudTelefone(String op, Telefone t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "CALL GerenciarTelefone(?, ?, ?)";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, op);
		cs.setString(2, t.getAluno().getCpf());
		cs.setString(3, t.getNumero());
		
		cs.registerOutParameter(15, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(15);
		cs.close();
		c.close();
		return saida;
	}


}
