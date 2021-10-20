import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        } catch (NumberFormatException | NullPointerException e) {
            writer.println(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
        } catch (ServletException | IOException e) {
            writer.println("big ex");
        }
    }

    private void addHitRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, NumberFormatException, NullPointerException {
        req.setAttribute("time", LocalTime.now().toString());
        req.setAttribute("x", req.getParameter("x"));
        req.setAttribute("y", req.getParameter("y"));
        req.setAttribute("r", req.getParameter("r"));

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
