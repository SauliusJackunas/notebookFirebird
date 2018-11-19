package my.notebook.project;

public class Note {
	protected int id;
    protected String title;
    protected String message;

    public Note() {}

	public Note(int id) {
		this.id = id;
	}

    public Note(int id, String title, String message) {
        this(title, message);
        this.id = id;
    }
     
    public Note(String title, String message) {
        this.title = title;
        this.message = message;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
