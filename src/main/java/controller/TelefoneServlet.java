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
import model.Telefone;
import persistence.AlunoDao;
import persistence.GenericDao;
import persistence.TelefoneDao;

@WebServlet("/telefone")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TelefoneServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("telefone.jsp");
		rd.forward(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//entrada
		String cmd = request.getParameter("botao");
		String cpf = request.getParameter("cpf");
		String numero = request.getParameter("numero");
		//saida
		String saida="";
		String erro="";
	
	    Telefone t = new Telefone();
	    t.setNumero(numero); 
	    
	 
	    List<Telefone> telefones = new ArrayList<>();
		if(!cmd.contains("Listar")) {
			t.setNumero(numero);
		}
		if(cmd.contains("Cadastrar") || cmd.contains("Alterar")){
	    	 t.setNumero(numero);
		}
		try {
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarTelefone(t);
				t = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarTelefone(t);
				t = null;
			}
			if (cmd.contains("Excluir")) {
				saida = excluirTelefone(t);
				t = null;
			}
			if (cmd.contains("Buscar")) {
				t = buscarTelefone(t);
			}
			if (cmd.contains("Listar")) {
			    telefones = listarTelefone();
			}
		} catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("telefone", t);
			request.setAttribute("telefones", telefones);
			
			RequestDispatcher rd = request.getRequestDispatcher("telefone.jsp");
			rd.forward(request, response);
		}
		

	}



	private String cadastrarTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    TelefoneDao pDao = new TelefoneDao (gDao);
		String saida = pDao.iudTelefone("I", t);
		return saida;
	}

	private String alterarTelefone(Telefone t) throws SQLException, ClassNotFoundException{
		GenericDao gDao = new GenericDao();
	    TelefoneDao pDao = new TelefoneDao (gDao);
		String saida = pDao.iudTelefone("U", t);
		return saida;
	}

	private String excluirTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    TelefoneDao pDao = new TelefoneDao (gDao);
		String saida = pDao.iudTelefone("D", t);
		return saida;
	}

	private Telefone buscarTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    TelefoneDao pDao = new TelefoneDao (gDao);
		t = pDao.consultar(t);
		return t;
	}

	private List<Telefone> listarTelefone() throws SQLException, ClassNotFoundException{
		GenericDao gDao = new GenericDao();
	    TelefoneDao pDao = new TelefoneDao (gDao);
	    List<Telefone> telefones = pDao.listar();
		
		 return telefones;
	}

}
