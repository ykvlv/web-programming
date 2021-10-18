import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"request/*"})
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String absolutePath = req.getRequestURI()
                .replaceFirst(req.getContextPath(), "")
                .replaceFirst("/request", "");

        switch (absolutePath) {
            case "/addPoint":
                addPointRequest(req, resp);
                break;
            case "/clearTable":
                clearTableRequest(req, resp);
                break;
            default:
                homeRedirect(req, resp);
        }
    }

    private void addPointRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AreaCheckServlet checkServlet = new AreaCheckServlet();
        checkServlet.init();
        checkServlet.service(req, resp);
        checkServlet.destroy();
    }

    private void homeRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath());
    }

    // not added
    private void clearTableRequest(HttpServletRequest req, HttpServletResponse resp) {

    }
}
