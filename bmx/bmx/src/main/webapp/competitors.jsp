<%@ page import="com.olympic.games.bmx.domain.models.Competitor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.olympic.games.bmx.domain.enums.Country" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<html>
<head>
    <title>Tabla de competidores</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<main>
    <header class="competitors-header">
        <h1>Tabla de competidores</h1>
        <p>Acá podras ver toda la información disponible de los competidores</p>
    </header>
    <form action="competitors" method="get" class="filter-form">
        <label for="firstName">Nombre:</label>
        <input type="text" id="firstName" name="firstName" value="<%= request.getParameter("firstName") != null ?request.getParameter("firstName") : ""%>">
        <label for="lastName">Apellido:</label>
        <input type="text" id="lastName" name="lastName" value="<%= request.getParameter("lastName") != null ? request.getParameter("lastName") : "" %>">
        <label for="country">País:</label>
        <select id="country" name="country" >
            <option value="" <%= "".equals(request.getParameter("country")) ? "selected" : "" %>>Selecciona un país</option>
            <%
                for (Country country : Country.values()) {
                    String selectedCountry = request.getParameter("country") != null ? request.getParameter("country") : "";
            %>
            <option value="<%= country.name() %>" <%= country.name().equals(selectedCountry) ? "selected" : "" %>><%= country.getName() %></option>
            <%
                }
            %>
        </select>
        <label for="field">Ordenar por:</label>
        <select id="field" name="field">
            <option value="firstName" <%= "firstName".equals(request.getParameter("field")) ?  "selected": "" %>>Nombre</option>
            <option value="lastName" <%= "lastName".equals(request.getParameter("field")) ?  "selected": "" %>>Apellido</option>
            <option value="country" <%= "country".equals(request.getParameter("field")) ?  "selected": "" %>>País</option>
            <option value="birthDate" <%= "birthDate".equals(request.getParameter("field")) ?  "selected": "" %>>Fecha de nacimiento</option>
            <option value="totalScore" <%= "totalScore".equals(request.getParameter("field")) ?  "selected": "" %>>Puntaje Total</option>
        </select>
        <label for="order">Orden:</label>
        <select id="order" name="order">
            <option value="asc" <%= "asc".equals(request.getParameter("order")) ? "selected" : "" %>>Ascendente</option>
            <option value="desc" <%= "desc".equals(request.getParameter("order")) ? "selected" : "" %>>Descendente</option>
        </select>
        <button type="submit">Buscar</button>
    </form>
    <table>
        <tr style="border-bottom: 3px solid black">
            <th>Nombre</th>
            <th>Apellido</th>
            <th>País</th>
            <th>Código País</th>
            <th>Fecha de nacimiento</th>
            <th style="width: 10%">Puntaje total</th>
            <th style="width: 14%">Acciones</th>
        </tr>
        <%
            List<Competitor> competitors = (List<Competitor>) request.getAttribute("competitors");

            List<Competitor> sortedByScore = new ArrayList<>(competitors);
            sortedByScore.sort(Comparator.comparingInt(Competitor::getTotalScore));
            for (Competitor competitor : competitors) {
                String inlineStyle = "";
                boolean isRaceInitialized = sortedByScore.size() > 0 && sortedByScore.get(0).getTotalScore() != 0;
                if (isRaceInitialized && competitor.getId().equals(sortedByScore.get(0).getId())) {
                    inlineStyle = "background-color: #FFD700";
                } else if (isRaceInitialized && sortedByScore.size() > 1 && competitor.getId().equals(sortedByScore.get(1).getId())) {
                    inlineStyle = "background-color: #C0C0C0";
                } else if ( isRaceInitialized && sortedByScore.size() > 2 && competitor.getId().equals(sortedByScore.get(2).getId())) {
                    inlineStyle = "background-color: #cd7f32";
                }
        %>
        <tr style="<%=inlineStyle%>" >
            <td><%= competitor.getFirstName() %></td>
            <td><%= competitor.getLastName().toUpperCase() %></td>
            <td><%= competitor.getCountry().getName() %></td>
            <td><%= competitor.getCountry().getCode() %></td>
            <td><%= competitor.getBirthDate() %></td>
            <td><%= competitor.getTotalScore() %></td>
            <td style="display: flex; align-items: center; gap:4px;">
                <form action="competitors/deleteCompetitor" method="post">
                    <input type="hidden" name="id" value="<%= competitor.getId() %>">
                    <button type="submit" style="background-color: red; color: white">Eliminar</button>
                </form>
                <a href="competitors/competitorDetail?id=<%= competitor.getId() %>" >
                    <button style="background-color: #00bbff">Ver detalles</button>
                </a>

            </td>
        </tr>
        <%
            }
        %>
    </table>
    <form action="competitors/simulateRace" method="post">
        <button type="submit">Simular carrera</button>
    </form>
    <a href="competitors/create" style="margin-top: 20px">
        <button style="background-color: #00bbff">Agregar competidor</button
    </a>

    <div style="margin-top: 10px">
        <a  href="competitors/export-xls">
            <button>Exportar xls</button>
        </a>

        <a href="competitors/export-json">
            <button>Exportar JSON</button>
        </a>

    </div>
</main>

</body>
</html>
