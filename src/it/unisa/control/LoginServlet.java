package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String[] FORBIDDEN_FILES = { "META-INF/context.xml", "WEB-INF/web.xml" };

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao usDao = new UserDao();

		try {
			UserBean user = new UserBean();
			user.setUsername(request.getParameter("un"));
			user.setPassword(request.getParameter("pw"));
			user = usDao.doRetrieve(request.getParameter("un"), request.getParameter("pw"));

			String checkout = request.getParameter("checkout");

			if (user.isValid()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				if (checkout != null) {
					String redirectedPage = sanitizePath("Checkout.jsp");

					if (isForbiddenPage(redirectedPage)) {
						response.sendRedirect("Home.jsp");
						return;
					}

					response.sendRedirect(request.getContextPath() + "/account?page=" + redirectedPage);
				} else {
					response.sendRedirect(request.getContextPath() + "/Home.jsp");
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/Login.jsp?action=error");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
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
}
