package bridge.format;

import java.util.List;

public class HtmlFormatter implements Formatter {

    @Override
    public String heading(String title) {
        return "<h1>" + escape(title) + "</h1>";
    }

    @Override
    public String field(String label, String value) {
        return "<li>" + escape(label) + ": " + escape(value) + "</li>";
    }

    @Override
    public String document(String heading, List<String> fields) {
        return "<article>" + heading + "<ul>" + String.join("", fields) + "</ul></article>";
    }

    private static String escape(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
