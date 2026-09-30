package bridge.report;

import bridge.format.Formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Report {

    private final String id;
    private Formatter formatter;

    protected Report(String id, Formatter formatter) {
        this.id = Objects.requireNonNull(id, "id");
        this.formatter = Objects.requireNonNull(formatter, "formatter");
    }

    public String getId() {
        return id;
    }

    public void setImplementation(Formatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter");
    }

    public String execute() {
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, String> entry : content().entrySet()) {
            fields.add(formatter.field(entry.getKey(), entry.getValue()));
        }
        return formatter.document(formatter.heading(title() + " " + id), fields);
    }

    protected abstract String title();

    protected abstract Map<String, String> content();
}
