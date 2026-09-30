package bridge.format;

import java.util.List;

public interface Formatter {

    String heading(String title);

    String field(String label, String value);

    String document(String heading, List<String> fields);
}
