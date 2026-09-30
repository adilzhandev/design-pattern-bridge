import bridge.format.Formatter;
import bridge.format.HtmlFormatter;
import bridge.format.TextFormatter;
import bridge.report.AttendanceReport;
import bridge.report.GradeReport;
import bridge.report.Report;

import java.util.List;

public class Main {

    private static final String ATTENDANCE_ID = "AR-1";
    private static final int ATTENDED = 3;
    private static final int TOTAL_SESSIONS = 4;
    private static final String GRADE_ID = "GR-1";
    private static final List<Integer> GRADES = List.of(70, 80, 90);

    private static final String EXPECTED_T1 =
            "ATTENDANCE REPORT AR-1\nattended: 3/4\nrate: 75%";
    private static final String EXPECTED_T2 =
            "<article><h1>Attendance Report AR-1</h1><ul><li>attended: 3/4</li><li>rate: 75%</li></ul></article>";
    private static final String EXPECTED_T3 =
            "GRADE REPORT GR-1\ngrades: 70, 80, 90\naverage: 80";
    private static final String EXPECTED_T4 =
            "<article><h1>Grade Report GR-1</h1><ul><li>grades: 70, 80, 90</li><li>average: 80</li></ul></article>";
    private static final String EXPECTED_STATE = "id=AR-1 attended=3/4 rate=75%";

    private int passed;
    private int total;

    public static void main(String[] args) {
        if (args.length == 1 && "--demo".equals(args[0])) {
            new Main().runDemo();
        } else {
            System.out.println("Usage: java -cp out Main --demo");
        }
    }

    private void runDemo() {
        Formatter text = new TextFormatter();
        Formatter html = new HtmlFormatter();

        checkCombination("T1", newAttendanceReport(text), text, EXPECTED_T1);
        checkCombination("T2", newAttendanceReport(html), html, EXPECTED_T2);
        checkCombination("T3", newGradeReport(text), text, EXPECTED_T3);
        checkCombination("T4", newGradeReport(html), html, EXPECTED_T4);
        checkRuntimeSwitch();

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS"
                + (passed == total ? "" : " (" + (total - passed) + " FAIL)"));
    }

    private static AttendanceReport newAttendanceReport(Formatter formatter) {
        return new AttendanceReport(ATTENDANCE_ID, ATTENDED, TOTAL_SESSIONS, formatter);
    }

    private static GradeReport newGradeReport(Formatter formatter) {
        return new GradeReport(GRADE_ID, GRADES, formatter);
    }

    private void checkCombination(String checkId, Report report, Formatter formatter, String expected) {
        String actual = report.execute();
        boolean pass = actual.equals(expected);
        record(pass);
        System.out.println(checkId + " " + verdict(pass) + " | " + classNames(report, formatter)
                + " | result=" + oneLine(actual));
        if (!pass) {
            System.out.println("   expected=" + oneLine(expected));
        }
    }

    private void checkRuntimeSwitch() {
        Formatter first = new TextFormatter();
        Formatter second = new HtmlFormatter();
        AttendanceReport original = newAttendanceReport(first);
        String stateBefore = stateOf(original);
        String before = original.execute();

        Report current = original;
        current.setImplementation(second);
        String after = current.execute();

        boolean sameObject = current == original;
        boolean stateUnchanged = stateBefore.equals(EXPECTED_STATE) && stateOf(original).equals(stateBefore);
        boolean resultsCorrect = before.equals(EXPECTED_T1) && after.equals(EXPECTED_T2);
        boolean pass = sameObject && stateUnchanged && resultsCorrect;
        record(pass);

        System.out.println("T5 " + verdict(pass) + " | " + simpleName(original) + ": "
                + simpleName(first) + " -> " + simpleName(second)
                + " | sameObject=" + sameObject + " | stateUnchanged=" + stateUnchanged
                + " | state=" + stateOf(original));
        System.out.println("   before=" + oneLine(before) + " | after=" + oneLine(after));
        if (!pass) {
            System.out.println("   expected: sameObject=true | stateUnchanged=true | state=" + EXPECTED_STATE
                    + " | before=" + oneLine(EXPECTED_T1) + " | after=" + oneLine(EXPECTED_T2));
        }
    }

    private static String stateOf(AttendanceReport report) {
        return "id=" + report.getId()
                + " attended=" + report.getAttended() + "/" + report.getTotalSessions()
                + " rate=" + report.attendanceRate() + "%";
    }

    private void record(boolean pass) {
        total++;
        if (pass) {
            passed++;
        }
    }

    private static String verdict(boolean pass) {
        return pass ? "PASS" : "FAIL";
    }

    private static String classNames(Report report, Formatter formatter) {
        return simpleName(report) + " + " + simpleName(formatter);
    }

    private static String simpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    private static String oneLine(String text) {
        return text.replace("\n", "\\n");
    }
}
