package persistence;
<<<<<<< Updated upstream

import java.sql.CallableStatement;
=======
>>>>>>> Stashed changes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Updated upstream
import java.sql.Types;
=======
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Disciplina;
import model.Matricula;

<<<<<<< Updated upstream
public class MatriculaDao implements ICrud<Matricula>, IMatriculaDao {

	private GenericDao gDao;

	public MatriculaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public String iudMatricula(String op, Matricula m) throws SQLException, ClassNotFoundException {
		Connection cc = gDao.getConnection();
		String sql = "CALL GerenciarMatriculaD(?,?,?,?,?,?,?)";
		CallableStatement cs = cc.prepareCall(sql);
		cs.setString(1, op);
		cs.setString(2, m.getAluno().getCpf());

		Disciplina disciplina = m.getDisciplina();

		// Verificar se o objeto Disciplina é nulo antes de acessá-lo
		if (disciplina != null) {
			cs.setInt(3, disciplina.getCodigo());
			cs.setString(4, disciplina.getHoras_inicio());
			cs.setString(5, disciplina.getDia_semana());
		} else {
			// Lidar com o caso em que o objeto Disciplina é nulo
			throw new IllegalArgumentException("O objeto Disciplina em Matricula é nulo");
		}

		cs.setString(6, m.getData());

		// Verificar se já existe uma matrícula para o mesmo aluno na mesma disciplina e
		// no mesmo horário
		if (existeConflitoMatricula(m)) {
			throw new SQLException(
					"Já existe uma matrícula para o mesmo aluno na mesma disciplina e no mesmo horário.");
		}

		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(7);
		cs.close();
		cc.close();
		return saida;
	}

	// Método para verificar conflito de matrícula
	public boolean existeConflitoMatricula(Matricula m) throws SQLException, ClassNotFoundException {
		Connection conn = gDao.getConnection();
		String sql = "SELECT COUNT(*) AS total " + "FROM Matricula m " + "JOIN Disciplina d ON m.disciplina = d.codigo "
				+ "WHERE m.aluno = ? AND m.data_m = ? " + "AND d.dia_semana = ? AND d.horas_inicio = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, m.getAluno().getCpf());
			ps.setString(2, m.getData());
			ps.setString(3, m.getDisciplina().getDia_semana());
			ps.setString(4, m.getDisciplina().getHoras_inicio());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int total = rs.getInt("total");
					return total > 0;
				}
			}
		} finally {
			conn.close();
		}
		return false;
	}

	@Override
	public void inserir(Matricula m) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO Matricula (aluno, disciplina, data_m) VALUES (?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, m.getAluno().getCpf());
		ps.setInt(2, m.getDisciplina().getCodigo());
		ps.setString(3, m.getData());
		ps.execute();
		ps.close();
		c.close();

	}

	@Override
	public void atualizar(Matricula m) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE Matricula SET disciplina = ?, data_m = ? WHERE aluno = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, m.getDisciplina().getCodigo());
		ps.setString(2, m.getData());
		ps.setString(4, m.getAluno().getCpf());

		ps.execute();
		ps.close();
		c.close();

	}

	@Override
	public void excluir(Matricula m) throws SQLException, ClassNotFoundException {
		if (m == null || m.getAluno() == null || m.getAluno().getCpf() == null) {
			throw new IllegalArgumentException("Matricula ou Aluno em Matricula é nulo");
		}

		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = gDao.getConnection();
			String sql = "DELETE FROM Matricula WHERE aluno = ? AND disciplina = ?";
			ps = c.prepareStatement(sql);
			ps.setString(1, m.getAluno().getCpf());
			ps.setInt(2, m.getDisciplina().getCodigo());
			int rowsAffected = ps.executeUpdate(); // Executar a exclusão e obter o número de linhas afetadas

			if (rowsAffected > 0) {
				System.out.println("Matrícula excluída com sucesso.");
			} else {
				System.out.println("Não foi encontrada nenhuma matrícula para excluir.");
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
	}

	@Override
	public Matricula consultar(Matricula m) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
				+ "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula " + "FROM Matricula m "
				+ "INNER JOIN aluno a ON a.cpf = m.aluno " + "INNER JOIN disciplina d ON d.codigo = m.disciplina "
				+ "WHERE m.aluno = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, m.getAluno().getCpf());
		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Aluno a = new Aluno();
				a.setCpf(rs.getString("cpfAluno"));
				a.setNome(rs.getString("nomeAluno"));

				Disciplina disciplina = new Disciplina();
				disciplina.setCodigo(rs.getInt("codigoDisciplina"));
				disciplina.setNome(rs.getString("nomeDisciplina"));

				m.setData(rs.getString("dataMatricula"));

				m.setAluno(a);
				m.setDisciplina(disciplina);

				rs.close();
				ps.close();
				c.close();
			}
		}
		return m;

	}

	@Override
	public List<Matricula> listar() throws SQLException, ClassNotFoundException {
		List<Matricula> matriculas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
				+ "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula " + "FROM Matricula m "
				+ "INNER JOIN aluno a ON a.cpf = m.aluno " + "INNER JOIN disciplina d ON d.codigo = m.disciplina ";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Matricula m = new Matricula();

			Aluno a = new Aluno();
			a.setCpf(rs.getString("cpfAluno"));
			a.setNome(rs.getString("nomeAluno"));

			Disciplina disciplina = new Disciplina();
			disciplina.setCodigo(rs.getInt("codigoDisciplina"));
			disciplina.setNome(rs.getString("nomeDisciplina"));

			m.setData(rs.getString("dataMatricula"));

			m.setAluno(a);
			m.setDisciplina(disciplina);

			matriculas.add(m);
		}

		return matriculas;
	}
}
=======
public class MatriculaDao implements ICrud<Matricula> {

    private GenericDao gDao;

    public MatriculaDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    @Override
    public void inserir(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Matricula (aluno, disciplina, data_m) VALUES (?, ?, ?)";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getAluno().getCpf());
            ps.setInt(2, m.getDisciplina().getCodigo());
            ps.setString(3, m.getData());
            ps.executeUpdate();
        }
    }

    @Override
    public void atualizar(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Matricula SET disciplina = ?, data_m = ? WHERE aluno = ?";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getDisciplina().getCodigo());
            ps.setString(2, m.getData());
            ps.setString(3, m.getAluno().getCpf());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Matricula WHERE aluno = ?";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getAluno().getCpf());
            ps.executeUpdate();
        }
    }

    @Override
    public Matricula consultar(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "SELECT m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
                   + "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula "
                   + "FROM Matricula m "
                   + "INNER JOIN aluno a ON a.cpf = m.aluno "
                   + "INNER JOIN disciplina d ON d.codigo = m.disciplina "
                   + "WHERE m.aluno = ?";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getAluno().getCpf());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setCpf(rs.getString("cpfAluno"));
                    a.setNome(rs.getString("nomeAluno"));

                    Disciplina disciplina = new Disciplina();
                    disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                    disciplina.setNome(rs.getString("nomeDisciplina"));

                    m.setData(rs.getString("dataMatricula"));

                    m.setAluno(a);
                    m.setDisciplina(disciplina);
                }
            }
        }
        return m;
    }

    @Override
    public List<Matricula> listar() throws SQLException, ClassNotFoundException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
                + "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula "
                + "FROM Matricula m "
                + "INNER JOIN aluno a ON a.cpf = m.aluno "
                + "INNER JOIN disciplina d ON d.codigo = m.disciplina ";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Matricula m = new Matricula();

                Aluno a = new Aluno();
                a.setCpf(rs.getString("cpfAluno"));
                a.setNome(rs.getString("nomeAluno"));

                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                disciplina.setNome(rs.getString("nomeDisciplina"));

                m.setData(rs.getString("dataMatricula"));

                m.setAluno(a);
                m.setDisciplina(disciplina);

                matriculas.add(m);
            }
        }
        return matriculas;
    }
}


>>>>>>> Stashed changes
