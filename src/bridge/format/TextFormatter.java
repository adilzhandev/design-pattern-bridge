package bridge.format;

import java.util.List;
import java.util.Locale;

public class TextFormatter implements Formatter {

    @Override
    public String heading(String title) {
        return title.toUpperCase(Locale.ROOT);
    }

    @Override
    public String field(String label, String value) {
        return label + ": " + value;
    }

    @Override
    public String document(String heading, List<String> fields) {
        return heading + "\n" + String.join("\n", fields);
    }
}
