package bridge.report;

import bridge.format.Formatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GradeReport extends Report {

    private final List<Integer> grades;

    public GradeReport(String id, List<Integer> grades, Formatter formatter) {
        super(id, formatter);
        if (grades.isEmpty()) {
            throw new IllegalArgumentException("grades must not be empty");
        }
        this.grades = List.copyOf(grades);
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public int average() {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return Math.round((float) sum / grades.size());
    }

    @Override
    protected String title() {
        return "Grade Report";
    }

    @Override
    protected Map<String, String> content() {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("grades", grades.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        content.put("average", String.valueOf(average()));
        return content;
    }
}
