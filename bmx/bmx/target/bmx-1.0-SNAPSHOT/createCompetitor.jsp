<%@ page import="com.olympic.games.bmx.domain.enums.Country" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Competitor</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css">
</head>
<body>
<main>
    <header class="competitors-header">
        <h1>Crear competidor</h1>
    </header>
    <%
        Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    %>
    <form action="<%= request.getContextPath() %>/competitors/create" method="post">
        <div>
            <label for="firstName">Nombre:</label>
            <input type="text" id="firstName" name="firstName" value="<%= request.getParameter("firstName") != null ? request.getParameter("firstName") : "" %>" required>
            <p class="error-message"><%= errors != null && errors.get("firstName")  != null ? errors.get("firstName") : "" %></p>
        </div>

        <div>
            <label for="lastName">Apellido:</label>
            <input type="text" id="lastName" name="lastName" value="<%= request.getParameter("lastName") != null ? request.getParameter("lastName") : "" %>" required>
            <p class="error-message"><%= errors != null && errors.get("lastName") != null ? errors.get("lastName")  : "" %></p>
        </div>

        <div>
            <label for="country">País:</label>
            <select id="country" name="country">
                <%
                    for (Country country : Country.values()) {
                        String selectedCountry = request.getParameter("country") != null ? request.getParameter("country") : "";
                %>
                <option value="<%= country.name() %>" <%= country.name().equals(selectedCountry) ? "selected" : "" %>><%= country.getName() %></option>
                <%
                    }
                %>
            </select>
            <p class="error-message"><%= errors != null && errors.get("country") != null ?errors.get("country")  : "" %></p>
        </div>

        <div>
            <label for="birthDate">Fecha de nacimiento:</label>
            <input type="date" id="birthDate" name="birthDate" value="<%= request.getParameter("birthDate") != null ? request.getParameter("birthDate") : "" %>" required pattern="\d{4}-\d{2}-\d{2}">
            <p class="error-message"><%= errors != null && errors.get("birthDate") != null ? errors.get("birthDate") : "" %></p>
        </div>

        <div>
            <label for="description">Descripción:</label>
            <textarea id="description" name="description"><%= request.getParameter("description") != null ? request.getParameter("description") : "" %></textarea>
            <p class="error-message"><%= errors != null && errors.get("description") != null ? errors.get("description") : "" %></p>
        </div>
        <div style="display: flex; justify-content: center">

            <button type="submit">Crear</button>
        </div>
    </form>
</main>
</body>
</html>