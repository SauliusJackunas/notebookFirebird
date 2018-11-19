package my.notebook.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoteDAO noteDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUserName = getServletContext().getInitParameter("jdbcUserName");
		String jdbcPwd = getServletContext().getInitParameter("jdbcPwd");

		noteDAO = new NoteDAO(jdbcURL, jdbcUserName, jdbcPwd);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String takeAction = request.getServletPath();

		try {
			switch (takeAction) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertNewNote(request, response);
				break;
			case "/delete":
				deleteNote(request, response);
				break;
			case "/edit":
				editNote(request, response);
				break;
			case "/update":
				updateNote(request, response);
				break;
			default:
				notesList(request, response);
				break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void notesList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Note> notesList = noteDAO.listAllNotes();
		request.setAttribute("notesList", notesList);
		RequestDispatcher disp = request.getRequestDispatcher("NoteList.jsp");
		disp.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher disp = request.getRequestDispatcher("NoteForm.jsp");
		disp.forward(request, response);
	}

	private void editNote(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		Note existing = noteDAO.getNote(id);
		RequestDispatcher disp = request.getRequestDispatcher("NoteForm.jsp");
		request.setAttribute("note", existing);
		disp.forward(request, response);
	}

	private void insertNewNote(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		String title = request.getParameter("title");
		String message = request.getParameter("message");

		Note newNote = new Note(title, message);
		noteDAO.insertNote(newNote);
		response.sendRedirect("list");
	}

	private void updateNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String message = request.getParameter("message");

		Note note = new Note(id, title, message);
		noteDAO.updateNote(note);
		response.sendRedirect("list");
	}

	private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Note note = new Note(id);
		noteDAO.deleteNote(note);
		response.sendRedirect("list");
	}
}
