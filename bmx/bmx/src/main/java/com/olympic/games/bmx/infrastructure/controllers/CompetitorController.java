package com.olympic.games.bmx.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.olympic.games.bmx.application.searchcriteria.CompetitorSearchCriteria;
import com.olympic.games.bmx.application.searchcriteria.Sorting;
import com.olympic.games.bmx.application.services.CompetitorService;
import com.olympic.games.bmx.application.services.RaceService;
import com.olympic.games.bmx.application.services.imp.CompetitorServiceImp;
import com.olympic.games.bmx.application.services.imp.RaceServiceImp;
import com.olympic.games.bmx.domain.enums.Country;
import com.olympic.games.bmx.domain.models.Competitor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.olympic.games.bmx.domain.utils.constants.CompetitorConstant.MIN_AGE;

@WebServlet(name = "competitorController", value = {"/competitors", "/competitors/simulateRace", "/competitors/competitorDetail", "/competitors/deleteCompetitor", "/competitors/create", "/competitors/export-xls", "/competitors/export-json"})
public class CompetitorController extends HttpServlet {

  private CompetitorService competitorService;
  private RaceService raceService;

  public void init() {
    competitorService = new CompetitorServiceImp();
    raceService = new RaceServiceImp();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getServletPath();
    if("/competitors".equals(path)) {
      getCompetitors(req, resp);
    } else if ("/competitors/competitorDetail".equals(path)) {
      getCompetitorDetail(req, resp);
    } else if("/competitors/create".equals(path)){
      RequestDispatcher dispatcher = req.getRequestDispatcher("/createCompetitor.jsp");
      dispatcher.forward(req, resp);
    }else if("/competitors/export-xls".equals(path)){
      exportCompetitorsToXls(req,resp);
      resp.sendRedirect(req.getContextPath() + "/competitors");
    }else if("/competitors/export-json".equals(path)){
      exportCompetitorsToJson(req,resp);
    }

  }

  private void exportCompetitorsToXls(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    List<Competitor> competitors = competitorService.getCompetitors();

    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/vnd.ms-excel");
    resp.setHeader("Content-Disposition", "attachment;filename=competitors.xls");

    try (PrintWriter out = resp.getWriter()) {
      out.println("<table border='1'>");
      out.println("<tr>");
      out.println("<th>ID</th>");
      out.println("<th>Nombre</th>");
      out.println("<th>Apellido</th>");
      out.println("<th>País</th>");
      out.println("<th>Fecha de nacimiento</th>");
      out.println("<th>Puntaje total</th>");
      out.println("</tr>");

      for (Competitor competitor : competitors) {
        out.println("<tr>");
        out.println("<td>" + competitor.getId() + "</td>");
        out.println("<td>" + competitor.getFirstName() + "</td>");
        out.println("<td>" + competitor.getLastName() + "</td>");
        out.println("<td>" + competitor.getCountry().getName() + "</td>");
        out.println("<td>" + competitor.getBirthDate() + "</td>");
        out.println("<td>" + competitor.getTotalScore() + "</td>");
        out.println("</tr>");
      }

      out.println("</table>");
    }
  }

  private void exportCompetitorsToJson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    List<Competitor> competitors = competitorService.getCompetitors();
    String json = objectMapper.writeValueAsString(competitors);
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().write(json);

  }

  private void getCompetitors(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String countryName = req.getParameter("country");
    Country country = countryName != null && !countryName.isEmpty() ? Country.valueOf(countryName) : null;

    String sortingField = req.getParameter("field");
    String sortingOrder = req.getParameter("order");
    Sorting sorting = new Sorting(sortingField, sortingOrder);

    CompetitorSearchCriteria searchCriteria = new CompetitorSearchCriteria(firstName, lastName, country);

    List<Competitor> competitors = competitorService.getCompetitors(sorting, searchCriteria);
    req.setAttribute("competitors", competitors);
    RequestDispatcher dispatcher = req.getRequestDispatcher("/competitors.jsp");
    dispatcher.forward(req, resp);
  }

  private void getCompetitorDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Long id = Long.valueOf(req.getParameter("id"));
    Optional<Competitor> competitorOptional = competitorService.getCompetitorById(id);
    req.setAttribute("competitor", competitorOptional);
    RequestDispatcher dispatcher = req.getRequestDispatcher("/competitorDetail.jsp");
    dispatcher.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getServletPath();

    if ("/competitors/simulateRace".equals(path)) {
      raceService.simulateRace(competitorService.getCompetitors());
      resp.sendRedirect(req.getContextPath() + "/competitors");
    }else if("/competitors/deleteCompetitor".equals(path)){
      Long id = Long.valueOf(req.getParameter("id"));
      competitorService.deleteCompetitor(id);
      resp.sendRedirect(req.getContextPath() + "/competitors");
    }else if("/competitors/create".equals(path)){
      createCompetitor(req, resp);
    }
  }

  private void createCompetitor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Map<String, String> errors = new HashMap<>();

    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String countryName = req.getParameter("country");
    LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));
    Country country = countryName != null && !countryName.isEmpty() ? Country.valueOf(countryName) : null;
    String description = req.getParameter("description");

    if(firstName == null || firstName.isEmpty()){
      errors.put("firstName", "El nombre es requerido");
    }
    if(lastName == null || lastName.isEmpty()){
      errors.put("lastName", "El apellido es requerido");
    }
    if(birthDate == null){
      errors.put("birthDate", "La fecha de nacimiento es requerida");
    }
    if(birthDate != null && Period.between(birthDate, LocalDate.now()).getYears() < MIN_AGE){
      errors.put("birthDate", "El competidor debe ser mayor de edad");
    }
    if(country == null){
      errors.put("country", "El país es requerido");
    }
    if(description == null || description.isEmpty()){
      errors.put("description", "La descripción es requerida");
    }
    if(!errors.isEmpty()){
      req.setAttribute("errors", errors);
      RequestDispatcher dispatcher = req.getRequestDispatcher("/createCompetitor.jsp");
      dispatcher.forward(req, resp);
      return;
    }

    Competitor competitor = new Competitor(firstName, lastName, country, birthDate, description);
    competitorService.addCompetitor(competitor);
    resp.sendRedirect(req.getContextPath() + "/competitors");
  }
}
