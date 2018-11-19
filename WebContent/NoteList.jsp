<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Notebook Application</title>
</head>
<body>
    <div align="center">
        <h1>Notebook Management</h1>
        <h2>
            <a href="new">Add New Note</a>
            &nbsp;&nbsp;&nbsp;
            <a href="list">List All Added Notes</a>
             
        </h2>
    </div>
    <div align="center">
        <table border="1">
            <caption><h2>List of all added notes</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Message</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="note" items="${notesList}">
                <tr>
                    <td><c:out value="${note.id}" /></td>
                    <td><span style="font-weight: bold;"><c:out value="${note.title}" /></span></td>
                    <td><c:out value="${note.message}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${note.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${note.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>