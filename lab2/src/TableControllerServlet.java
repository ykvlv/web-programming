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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String absolutePath = req.getRequestURI()
                .replaceFirst(req.getContextPath(), "")
                .replaceFirst("/table", "");
        try {
            switch (absolutePath) {
                case "/addHit":
                    addPointRequest(req, resp);
                    break;
                case "/clearTable":
                    clearTableRequest(req, resp);
                    break;
                default:
                    homeRedirect(req, resp);
            }
        } catch (ServletException | IOException e) {
            try {
                PrintWriter writer = resp.getWriter();
                writer.println("Я не могу это обработать");
            } catch (IOException ee) {
                log("Я умер");
            }
        }
    }

    private void addPointRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("time", LocalTime.now());


        AreaCheckServlet areaCheckServlet = new AreaCheckServlet();
        areaCheckServlet.init();
        areaCheckServlet.service(req, resp);
        areaCheckServlet.destroy();
    }

    private void homeRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath());
    }

    // not added
    private void clearTableRequest(HttpServletRequest req, HttpServletResponse resp) {

    }
}
