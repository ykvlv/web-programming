import table.Hit;
import table.Table;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

@WebServlet(urlPatterns = {"table/*"})
public class TableControllerServlet extends HttpServlet {
    public static final String TABLE_ATTRIBUTE = "table";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        homeRedirect(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String absolutePath = req.getRequestURI()
                .replaceFirst(req.getContextPath(), "")
                .replaceFirst(req.getServletPath(), "");

        switch (absolutePath) {
            case "/addHit":
                addHitRequest(req, resp);
                break;
            case "/clearTable":
                clearTableRequest(req, resp);
                break;
            default:
                homeRedirect(req, resp);
        }
    }

    private void addHitRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("time", LocalTime.now().toString());
        req.setAttribute("x", req.getParameter("x"));
        req.setAttribute("y", req.getParameter("y"));
        req.setAttribute("r", req.getParameter("r"));

        AreaCheckServlet areaCheckServlet = new AreaCheckServlet();
        areaCheckServlet.init();
        areaCheckServlet.service(req, resp);
        areaCheckServlet.destroy();

        Table table = getTable();
        Hit hit = (Hit) req.getAttribute("hit");
        table.addHit(hit);
        setTable(table);

        homeRedirect(req, resp);
    }

    private void clearTableRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Table table = getTable();
        table.clearHits();
        setTable(table);

        homeRedirect(req, resp);
    }

    private void homeRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect(req.getContextPath());
    }

    private Table getTable() {
        ServletContext context = getServletContext();
        return (Table) context.getAttribute(TABLE_ATTRIBUTE);
    }

    private void setTable(Table table) {
        ServletContext context = getServletContext();
        context.setAttribute(TABLE_ATTRIBUTE, table);
    }
}
