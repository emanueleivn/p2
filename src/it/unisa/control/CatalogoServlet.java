package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String[] FORBIDDEN_FILES = { "META-INF/context.xml", "WEB-INF/web.xml" };

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProdottoDao prodDao = new ProdottoDao();
		ProdottoBean bean = new ProdottoBean();
		String sort = request.getParameter("sort");
		String action = request.getParameter("action");
		String redirectedPage = sanitizePath(request.getParameter("page"));

		// Check if the page is forbidden
		if (isForbiddenPage(redirectedPage)) {
			response.sendRedirect("errorPage.jsp");
			return;
		}

		try {
			if (action != null) {
				if (action.equalsIgnoreCase("add")) {
					bean.setNome(request.getParameter("nome"));
					bean.setDescrizione(request.getParameter("descrizione"));
					bean.setIva(request.getParameter("iva"));
					bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
					bean.setQuantità(Integer.parseInt(request.getParameter("quantità")));
					bean.setPiattaforma(request.getParameter("piattaforma"));
					bean.setGenere(request.getParameter("genere"));
					bean.setImmagine(request.getParameter("img"));
					bean.setDataUscita(request.getParameter("dataUscita"));
					bean.setDescrizioneDettagliata(request.getParameter("descDett"));
					bean.setInVendita(true);
					prodDao.doSave(bean);
				} else if (action.equalsIgnoreCase("modifica")) {
					bean.setIdProdotto(Integer.parseInt(request.getParameter("id")));
					bean.setNome(request.getParameter("nome"));
					bean.setDescrizione(request.getParameter("descrizione"));
					bean.setIva(request.getParameter("iva"));
					bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
					bean.setQuantità(Integer.parseInt(request.getParameter("quantità")));
					bean.setPiattaforma(request.getParameter("piattaforma"));
					bean.setGenere(request.getParameter("genere"));
					bean.setImmagine(request.getParameter("img"));
					bean.setDataUscita(request.getParameter("dataUscita"));
					bean.setDescrizioneDettagliata(request.getParameter("descDett"));
					bean.setInVendita(true);
					prodDao.doUpdate(bean);
				}

				request.getSession().removeAttribute("categorie");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		try {
			request.getSession().removeAttribute("products");
			request.getSession().setAttribute("products", prodDao.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		response.sendRedirect(request.getContextPath() + "/" + redirectedPage);
	}

	private boolean isForbiddenPage(String page) {
		if (page == null) {
			return true;
		}
		for (String forbidden : FORBIDDEN_FILES) {
			if (page.contains(forbidden)) {
				return true;
			}
		}
		return false;
	}

	private String sanitizePath(String page) {
		if (page == null) {
			return null;
		}
		page = page.replaceAll("[/\\\\]+", "/"); 
		while (page.contains("../")) {
			page = page.replace("../", "");
		}
		return page;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
