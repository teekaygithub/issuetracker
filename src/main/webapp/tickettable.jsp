<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="UTF-8" />
        <title>Project Ticket Table</title>
    </head>
    <body>
        <%@ page language="java" import="java.util.List" %>
        <%@ page language="java" import="java.util.ArrayList" %>
        <%@ page language="java" import="com.tomkato.issuetracker.*" %>
        <jsp:useBean id="myBean" class="com.tomkato.issuetracker.Ticket" />
        <jsp:useBean id="myDate" class="java.util.Date" />
        <h2>Your Project Tickets</h2>
        <div class="row justify-content-center" id="table-container" >
            <div class="col-sm-*">
                <table border="2" class="table table-striped table-bordered">
                    <p>Today's date: <%= myDate %></p>
                    <tr>
                        <th>ID</th>
                        <th>Project</th>
                        <th>Ticket Name</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Change Status</th>
                    </tr>
                    <%  
                        List<Ticket> mytickets = (ArrayList<Ticket>)request.getAttribute("tickets");
                        for (Ticket x : mytickets) {
                    %>
                    <tr>
                        <td><%= x.getId() %></td>
                        <td><%= x.getName() %></td>
                        <td><%= x.getProject() %></td>
                        <td><%= x.getDescription() %></td>
                        <td><%= x.getStatus() %></td>
                        <td>
                            <form method="POST" action="create">
                                <input type="text" name="id" value=<%= x.getId() %> style="display:none" />
                                <input type="text" name="submit" value="PUT" style="display:none" />
                                <select name="status">
                                    <option value="open">open</option>
                                    <option value="closed">closed</option>
                                </select>
                                <input type="submit" value="Submit Changes" />
                            </form>
                        </td>
                    </tr>
                        <% } %>
                </table>
                </div>
            </div>
        <div>
            <a href="/">Back to Home</a>
        </div>
    </body>
</html>