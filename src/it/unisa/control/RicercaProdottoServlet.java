package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

/**
 * Servlet implementation class RicercaProdottoServlet
 */
@WebServlet("/RicercaProdotto")
public class RicercaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String query = request.getParameter("query");
		
		ArrayList<ProdottoBean> risultato = new ArrayList<>();
		ProdottoDao dao = new ProdottoDao(); 
		ArrayList<ProdottoBean> prodotti;
		
		try {
			prodotti = dao.doRetrieveAll(null);
			for(ProdottoBean p : prodotti) {
				for(int i=0; i<p.getNome().length() - 1;i++) {
					for(int j=i+1; j<p.getNome().length(); j++) {
						if(((String) p.getNome().subSequence(i,j)).equalsIgnoreCase(query) && !risultato.contains(p)){
							risultato.add(p);
						}

					}
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		String json = new Gson().toJson(risultato);
		
		response.getWriter().write(json);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
