package bridge.format;

import java.util.List;

public class MarkdownFormatter implements Formatter {

    @Override
    public String heading(String title) {
        return "# " + title;
    }

    @Override
    public String field(String label, String value) {
        return "- **" + label + ":** " + value;
    }

    @Override
    public String document(String heading, List<String> fields) {
        return heading + "\n\n" + String.join("\n", fields);
    }
}
