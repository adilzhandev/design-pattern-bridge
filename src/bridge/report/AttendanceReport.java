package bridge.report;

import bridge.format.Formatter;

import java.util.LinkedHashMap;
import java.util.Map;

public class AttendanceReport extends Report {

    private final int attended;
    private final int totalSessions;

    public AttendanceReport(String id, int attended, int totalSessions, Formatter formatter) {
        super(id, formatter);
        if (totalSessions <= 0 || attended < 0 || attended > totalSessions) {
            throw new IllegalArgumentException("attended must be between 0 and totalSessions > 0");
        }
        this.attended = attended;
        this.totalSessions = totalSessions;
    }

    public int getAttended() {
        return attended;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public int attendanceRate() {
        return Math.round(attended * 100f / totalSessions);
    }

    @Override
    protected String title() {
        return "Attendance Report";
    }

    @Override
    protected Map<String, String> content() {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("attended", attended + "/" + totalSessions);
        content.put("rate", attendanceRate() + "%");
        return content;
    }
}
