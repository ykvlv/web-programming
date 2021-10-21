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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        homeRedirect(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String absolutePath = req.getRequestURI()
                .replaceFirst(req.getContextPath(), "")
                .replaceFirst("/table", "");

        PrintWriter writer = resp.getWriter();
        try {
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
        } catch (ServletException | IOException e) {
            writer.println("вы мне не нравитесь — сегодня вы без ответа.");
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

        ServletContext context = getServletContext();
        Table table = (Table) context.getAttribute("table");
        Hit hit = (Hit) req.getAttribute("hit");
        table.addHit(hit);
        context.setAttribute("table", table);
        context.setAttribute("hit", hit);

        homeRedirect(req, resp);
    }

    private void clearTableRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Table table = (Table) context.getAttribute("table");
        table.clearHits();
        context.setAttribute("table", table);

        homeRedirect(req, resp);
    }

    private void homeRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect(req.getContextPath());
    }


}
