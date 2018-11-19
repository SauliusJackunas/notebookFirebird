<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>My Notebook Application</title>
</head>
<body>
	<div align="center">
		<h1>Notes Management</h1>
		<h2>
			<a href="new">Add A New Note</a> &nbsp;&nbsp;&nbsp; <a href="list">List
				All Added Notes</a>

		</h2>
	</div>
	<div align="center">
		<c:if test="${note != null}">
			<form action="update" method="post">
		</c:if>
		<c:if test="${note == null}">
			<form action="insert" method="post">
		</c:if>
		<table border="0">
			<caption>
				<h2>
					<c:if test="${note != null}">
                        Edit Note
                    </c:if>
					<c:if test="${note == null}">
                        Add A New Note
                    </c:if>
				</h2>
			</caption>
			<c:if test="${note != null}">
				<input type="hidden" name="id" value="<c:out value='${note.id}' />" />
			</c:if>
			<tr>
				<th>Title:</th>
				<td><input type="text" name="title" size="45"
					value="<c:out value='${note.title}' />" />
				</td>
			</tr>
			<tr>
				<th>Message:</th>
				<td><input type="text" name="message" size="45"
					value="<c:out value='${note.message}' />" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save" /></td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>