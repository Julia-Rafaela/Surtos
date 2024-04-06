package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import model.Curso;
<<<<<<< Updated upstream
import persistence.AlunoDao;
import persistence.CursoDao;
import persistence.GenericDao;
=======
import persistence.GenericDao;
import persistence.AlunoDao;
import persistence.CursoDao;
>>>>>>> Stashed changes

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AlunoServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String erro = "";
        List<Curso> cursos = new ArrayList<>();
        GenericDao gDao = new GenericDao();
        CursoDao cursoDao = new CursoDao(gDao);

        try {
            cursos = cursoDao.listar();

        } catch (ClassNotFoundException | SQLException e) {
            erro = e.getMessage();

        } finally {
            request.setAttribute("erro", erro);
            request.setAttribute("cursos", cursos);

            RequestDispatcher rd = request.getRequestDispatcher("aluno.jsp");
            rd.forward(request, response);
        }

    }
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//entrada
		String cmd = request.getParameter("botao");
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		String nome_social = request.getParameter("nome_social");
		String data_nascimento = request.getParameter("data_nascimento");
		String email_pessoal = request.getParameter("email_pessoal");
		String email_corporativo = request.getParameter("email_corporativo");
		String telefone = request.getParameter("telefone");
		String conclusao_segundo_grau = request.getParameter("conclusao_segundo_grau");
		String instituicao_conclusao = request.getParameter("instituicao_conclusao");
		String pontuacao_vestibular = request.getParameter("pontuacao_vestibular");
		String posicao_vestibular = request.getParameter("posicao_vestibular");
		String ano_ingresso = request.getParameter("ano_ingresso");
		String semestre_ingresso = request.getParameter("semestre_ingresso");
		String semestre_limite_graduacao = request.getParameter("semestre_limite_graduacao");
		String cursoCodigo = request.getParameter("curso");
		

		//saida
		String saida="";
		String erro="";
		Aluno a = new Aluno();
		List<Aluno> alunos = new ArrayList<>();
		List<Curso> cursos = new ArrayList<>();
		
		if(!cmd.contains("Listar")) {
			a.setCpf(cpf);
		}
		
		try {
        
            
            cursos = listarCursos();
            
		if(cmd.contains("Cadastrar") || cmd.contains("Alterar")){
	    	 a.setNome(nome);
	    	 a.setNome_social(nome_social);
	    	 a.setData_nascimento(data_nascimento);
	    	 a.setEmail_pessoal(email_pessoal);
	    	 a.setEmail_corporativo(email_corporativo);
	    	 a.setTelefone(telefone);
	    	 a.setConclusao_segundo_grau(conclusao_segundo_grau);
	    	 a.setInstituicao_conclusao(instituicao_conclusao);
	    	 
	    	
	    	 
	    	 double pontuacao = Double.parseDouble(pontuacao_vestibular);
	    	 int posicao = Integer.parseInt(posicao_vestibular); 
	    	 int ano = Integer.parseInt(ano_ingresso);
	    	 int semestre = Integer.parseInt(semestre_ingresso);
	    	 int semestre_limite = Integer.parseInt(semestre_limite_graduacao);
	    	 
	         
	    	 a.setPontuacao_vestibular(pontuacao);
	         a.setPosicao_vestibular(posicao);
	    	 a.setAno_ingresso(ano);
	    	 a.setSemestre_ingresso(semestre);
	    	 a.setSemestre_limite_graduacao(semestre_limite);
	    	 
	    	 Curso curso = new Curso();
             curso.setCodigo(Integer.parseInt(cursoCodigo));
             a.setCurso(curso);
		}
			if (cmd.contains("Cadastrar")) {
				cadastrarAluno(a);
<<<<<<< Updated upstream
                saida = "Aluno cadastrada com sucesso";
=======
                saida = "Aluno cadastrado com sucesso";
>>>>>>> Stashed changes
                a = null;
			}
			if (cmd.contains("Alterar")) {
				alterarAluno(a);
<<<<<<< Updated upstream
                saida = "Aluno alterada com sucesso";
=======
                saida = "Aluno alterado com sucesso";
>>>>>>> Stashed changes
                a = null;
			}
			if (cmd.contains("Excluir")) {
				excluirAluno(a);
<<<<<<< Updated upstream
                saida = "Aluno excluída com sucesso";
=======
                saida = "Aluno excluído com sucesso";
>>>>>>> Stashed changes
                a = null;
			}
			if (cmd.contains("Buscar")) {
				a = buscarAluno(a);
			}
			if (cmd.contains("Listar")) {
			    alunos = listarAluno();
			    request.setAttribute("tipoTabela", "Listar");
			}
		} catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("aluno", a);
			request.setAttribute("alunos", alunos);
			request.setAttribute("cursos", cursos);
			
			RequestDispatcher rd = request.getRequestDispatcher("aluno.jsp");
			rd.forward(request, response);
		}
	}

	private String cadastrarAluno(Aluno a)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    AlunoDao pDao = new AlunoDao (gDao);
		String saida = pDao.iudAluno("I", a);
		return saida;
		
	}

	private String alterarAluno(Aluno a)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		AlunoDao pDao = new AlunoDao (gDao);
		String saida = pDao.iudAluno("U", a);
		return saida;
		
	}

<<<<<<< Updated upstream
	private void excluirAluno(Aluno a)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		AlunoDao alunoDao = new AlunoDao (gDao);
        alunoDao.excluir(a);
=======
	private String excluirAluno(Aluno a)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		AlunoDao pDao = new AlunoDao (gDao);
		String saida = pDao.iudAluno("D", a);
		return saida;
>>>>>>> Stashed changes
		
	}

	private Aluno buscarAluno (Aluno a)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		AlunoDao pDao = new AlunoDao (gDao);
		a = pDao.consultar(a);
		return a;
	
	}

	private List<Aluno> listarAluno()throws SQLException, ClassNotFoundException {
		
		GenericDao gDao = new GenericDao();
		AlunoDao pDao = new AlunoDao (gDao);
		List<Aluno> alunos = pDao.listar();
		
	 return alunos;
	}
	
	  private List<Curso> listarCursos() throws SQLException, ClassNotFoundException {
	        GenericDao gDao = new GenericDao();
	        CursoDao cursoDao = new CursoDao(gDao);
	        List<Curso> cursos = cursoDao.listar();
	        return cursos;
	    }

}