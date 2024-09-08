<%@ page import="com.olympic.games.bmx.domain.models.Competitor" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<html>
<head>
  <title>Competitor Details</title>
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/styles.css"></head>
<body>
<main>
  <header class="competitors-header">
    <h1>Competitor Details</h1>
  </header>
  <%
    Optional<Competitor> optionalCompetitor = (Optional<Competitor>) request.getAttribute("competitor");
    if (optionalCompetitor.isPresent()) {
      Competitor competitor = optionalCompetitor.get();
  %>
  <table>
    <tr>
    <th>ID</th>
    <td><%= competitor.getId() %></td>
    </tr>
    <tr>
      <th>Nombre</th>
      <td><%= competitor.getFirstName() %></td>
    </tr>
    <tr>
      <th>Apellido</th>
      <td><%= competitor.getLastName() %></td>
    </tr>
    <tr>
      <th>País</th>
      <td><%= competitor.getCountry().getName() %></td>
    </tr>
    <tr>
      <th>Fecha de nacimiento</th>
      <td><%= competitor.getBirthDate() %></td>
    </tr>
    <tr>
      <th>Puntaje total</th>
      <td><%= competitor.getTotalScore() %></td>
    </tr>
    <tr>
      <th>Descripción</th>
      <td><%= competitor.getDescription() %></td>
    </tr>
    <tr>
      <th>Puntajes</th>
      <td>
        <table>
          <tr>
            <th>Ronda</th>
            <th>Puntaje</th>
          </tr>
          <%
            for (Map.Entry<Integer, Integer> entry : competitor.getScores().entrySet()) {
          %>
          <tr>
            <td><%= entry.getKey() %></td>
            <td><%= entry.getValue() %></td>
          </tr>
          <%
            }
          %>
        </table>
      </td>
    </tr>
  </table>
  <%
  } else {
  %>
  <p style="font-size: 3rem; color: white">Competidor no encontrado</p>
  <%
    }
  %>
</main>
</body>
</html>