package my.notebook.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
	private String jdbcURL;
	private String jdbcUserName;
	private String jdbcPwd;
	private Connection jdbcConnection;
	
	public NoteDAO(String jdbcURL, String jdbcUserName, String jdbcPwd) {
		this.jdbcURL = jdbcURL;
		this.jdbcUserName = jdbcUserName;
		this.jdbcPwd = jdbcPwd;
	}

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("org.firebirdsql.jdbc.FBDriver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPwd);
		}
	}
	
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public boolean insertNote(Note note) throws SQLException {
		String sql = "INSERT INTO note (title, message) VALUES (?, ?)";

		connect();
		
		PreparedStatement st = jdbcConnection.prepareStatement(sql);
		st.setString(1, note.getTitle());
		st.setString(2, note.getMessage());
		
		
		boolean rowInserted = st.executeUpdate() > 0;
		st.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Note> listAllNotes() throws SQLException {
		List<Note> notesList = new ArrayList<>();
		
		String sql = "SELECT * FROM note";
		
		connect();
		
		Statement st = jdbcConnection.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			int id = rs.getInt("note_id");
			String title = rs.getString("title");
			String message = rs.getString("message");
			
			Note note = new Note(id, title, message);
			notesList.add(note);
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return notesList;
	}
	
	public boolean deleteNote(Note note) throws SQLException {
		String sql = "DELETE FROM note where note_id = ?";
		
		connect();
		
		PreparedStatement st = jdbcConnection.prepareStatement(sql);
		st.setInt(1, note.getId());
		
		boolean rowDeleted = st.executeUpdate() > 0;
		st.close();
		disconnect();
		
		return rowDeleted;
	}
	
	public boolean updateNote(Note note) throws SQLException {
		String sql = "UPDATE note SET title = ?, message = ?";
		sql += "WHERE note_id = ?";
		connect();
		
		PreparedStatement st = jdbcConnection.prepareStatement(sql);
		st.setString(1,	note.getTitle());
		st.setString(2, note.getMessage());
		st.setInt(3, note.getId());
		
		boolean rowUpdated = st.executeUpdate() > 0;
		st.close();
		disconnect();
		
		return rowUpdated;
	}
	
	public Note getNote(int id) throws SQLException {
		Note note = null;
		String sql = "SELECT * FROM note WHERE note_id = ?";
		
		connect();
		
		PreparedStatement st = jdbcConnection.prepareStatement(sql);
		st.setInt(1, id);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next()) {
			String title = rs.getString("title");
			String msg = rs.getString("message");
			
			note = new Note(id, title, msg);
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return note;
	}
}