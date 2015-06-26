package org.camunda.bpm.rest;

import java.io.IOException;
import java.io.PrintWriter;
 
import java.util.regex.Pattern;
import java.util.regex.Matcher;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class TestRest extends HttpServlet {
 
  private class RestRequest {
    // Accommodate two requests, one for all resources, another for a specific resource
    private Pattern regExAllPattern = Pattern.compile("/resource");
    private Pattern regExIdPattern = Pattern.compile("/resource/([0-9]*)");
 
    private Integer id;
 
    public RestRequest(String pathInfo) throws ServletException {
      // regex parse pathInfo
      Matcher matcher;
 
      // Check for ID case first, since the All pattern would also match
      matcher = regExIdPattern.matcher(pathInfo);
      if (matcher.find()) {
        id = Integer.parseInt(matcher.group(1));
        return;
      }
 
      matcher = regExAllPattern.matcher(pathInfo);
      if (matcher.find()) return;
 
      throw new ServletException("Invalid URI");
    }
 
    public Integer getId() {
      return id;
    }
 
    public void setId(Integer id) {
      this.id = id;
    }
  }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
 
    out.println("GET request handling");
    out.println(request.getPathInfo());
    out.println(request.getParameterMap());
    try {
      RestRequest resourceValues = new RestRequest(request.getPathInfo());
      out.println(resourceValues.getId());
    } catch (ServletException e) {
      response.setStatus(400);
      response.resetBuffer();
      e.printStackTrace();
      out.println(e.toString());
    }
    out.close();
  }
 
  
}