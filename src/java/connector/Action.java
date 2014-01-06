/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jena.DbHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Action extends HttpServlet 
{

  private static Logger logger = Logger.getLogger("file");
  
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException 
  {
    try
    {
      PrintWriter out = response.getWriter();
      
      ServletContext sc = getServletContext();
      String log4jLocation = "WEB-INF\\classes\\log4j.properties";
      String webAppPath = sc.getRealPath("/");
      String log4jProp = webAppPath + log4jLocation;
      System.out.print(log4jProp);
      PropertyConfigurator.configure(log4jProp);
      
      String dbname = request.getParameterValues("dbname")[0];
      String filename = request.getParameterValues("filename")[0];
      String filetype = request.getParameterValues("filetype")[0];
      
      String dbuser = request.getParameterValues("dbuser")[0];
      String dbkey  = request.getParameterValues("dbkey")[0];
      
      Map map = new HashMap();
      map.put("db",dbname);
      map.put("dbuser",dbuser);
      map.put("dbkey",dbkey);
      map.put("filepath",webAppPath+"data"+File.separatorChar+filename);
      map.put("filetype",filetype);
      
      logger.debug("Import init ...");
      DbHandler lvoDbHandler = new DbHandler();
      double rdfcount = lvoDbHandler.createDb(map);
      logger.debug("Import over ...");
      out.println("Import complete ... " + Double.toString(rdfcount) + " triples");
    }
    catch (Exception e)
    {logger.error(e);}
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
