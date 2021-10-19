import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AreaCheckServlet extends HttpServlet {
    private static final int[] allowedR = {1, 2, 3, 4, 5};
    private static final int[] possibleX = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    private static final int maxLengthY = 15;
    private static final int minY = -5;
    private static final int maxY = 3;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        LocalTime time; double x, y; int r;
        try {
            time = (LocalTime) req.getAttribute("time");
            x = Double.parseDouble(req.getParameter("x"));
            y = Double.parseDouble(req.getParameter("y"));
            r = Integer.parseInt(req.getParameter("r"));
        } catch (NumberFormatException | ClassCastException | NullPointerException e) {
            writeTagsInHTML(writer, Collections.singletonMap("valid", "false"));
            return;
        }

        if(validate(x, y, r)) {
            boolean hit = checkHit(x, y, r);
            writeTagsInHTML(writer, new HashMap<String, String>() {{
                put("valid", "true");
                put("x", String.valueOf(x));
                put("y", String.valueOf(y));
                put("r", String.valueOf(r));
                put("currentTime", time.format(DateTimeFormatter.ofPattern("HH:mm:ss:SSSSSSSSS")));
                put("executionTime", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SSSSSSSSS")));
                put("hit", String.valueOf(hit));
            }});
        } else {
            writeTagsInHTML(writer, Collections.singletonMap("valid", "false"));
        }
    }

    private void writeTagsInHTML(PrintWriter writer, Map<String, String> tagAndTextContent) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element html = doc.createElement("html");
            doc.appendChild(html);

            for (Map.Entry<String, String> entry: tagAndTextContent.entrySet()) {
                Element element = doc.createElement(entry.getKey());
                element.setTextContent(entry.getValue());
                html.appendChild(element);
            }

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.METHOD, "html");
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (ParserConfigurationException | TransformerException ignored) { }
    }

    private boolean checkHit(double x, double y, int r) {
        return checkCircle(x, y, r) && checkRectangle(x, y, r) && checkTriangle(x, y, r);
    }

    private boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y <= 0 && Math.sqrt(x * x + y * y) <= r / 2;
    }

    private boolean checkTriangle(double x, double y, int r) {
//переделать...
        return x <= 0 && y >= 0;
    }

    private boolean checkRectangle(double x, double y, int r) {
        return x <= 0 && y >= 0 && x >= r && y <= r;
    }

    private boolean validate(double x, double y, int r) {
        return validateR(r) && validateX(x, r) && validateY(y, r);
    }

    private boolean validateY(double y, int r) {
        return String.valueOf(y).length() < maxLengthY && ((minY <= y && y <= maxY) || Math.abs(y) <= r);
    }

    private boolean validateX(double x, int r) {
        return Math.abs(x) <= r || Arrays.stream(possibleX).anyMatch(Integer.valueOf((int) x)::equals);
    }

    private boolean validateR(int r) {
        return Arrays.stream(allowedR).anyMatch(Integer.valueOf(r)::equals);
    }

}
