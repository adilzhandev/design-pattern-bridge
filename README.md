# Assignment 3 | Bridge Pattern

| | |
|---|---|
| **Student** | Adilzhan Kuandykov |
| **Group** | SE-2526 |
| **Topic** | C — Reports (`Report` × `Formatter`) |
| **Repository** | https://github.com/adilzhandev/design-pattern-bridge |
| **Base commit (working I1/I2 version)** | `0da31fcda61f99ef99560021967baacf06fd89cf` |

## Role map

| Role | Class | Source path |
|---|---|---|
| Abstraction | `Report` (abstract) | [src/bridge/report/Report.java](src/bridge/report/Report.java) |
| A1 (refined abstraction) | `AttendanceReport` | [src/bridge/report/AttendanceReport.java](src/bridge/report/AttendanceReport.java) |
| A2 (refined abstraction) | `GradeReport` | [src/bridge/report/GradeReport.java](src/bridge/report/GradeReport.java) |
| Implementor | `Formatter` (interface) | [src/bridge/format/Formatter.java](src/bridge/format/Formatter.java) |
| I1 | `TextFormatter` | [src/bridge/format/TextFormatter.java](src/bridge/format/TextFormatter.java) |
| I2 | `HtmlFormatter` | [src/bridge/format/HtmlFormatter.java](src/bridge/format/HtmlFormatter.java) |
| I3 (extension) | `MarkdownFormatter` | [src/bridge/format/MarkdownFormatter.java](src/bridge/format/MarkdownFormatter.java) |
| Client | `Main` (default package) | [src/Main.java](src/Main.java) |

## Where to look

| What | Location |
|---|---|
| Bridge field (`private Formatter formatter`) | [Report.java:13](src/bridge/report/Report.java#L13) |
| Constructor receiving the implementor | [Report.java:15](src/bridge/report/Report.java#L15) |
| `setImplementation(Formatter)` | [Report.java:24](src/bridge/report/Report.java#L24) |
| `execute()` — delegates to `heading`, `field`, `document` | [Report.java:28](src/bridge/report/Report.java#L28) |
| T1–T4, T6, T7 check (`checkCombination`) | [Main.java:69](src/Main.java#L69) |
| T5 runtime switch (`checkRuntimeSwitch`) | [Main.java:80](src/Main.java#L80), `==` check at [Main.java:91](src/Main.java#L91) |

Delegation trace: `Main` → `report.execute()` → `content()` (A1/A2 calculates data) →
`formatter.field(...)` / `formatter.heading(...)` / `formatter.document(...)` (I1/I2/I3 formats it).

## Build and run

From the project root (JDK 17+, no extra dependencies):

```bash
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
```

```bash
java -cp out Main --demo
```

Line breaks inside a result are printed as `\n` so every check stays on one line.

## Sample data

- `AttendanceReport` id `AR-1`: 3 of 4 sessions attended → **75%**
- `GradeReport` id `GR-1`: grades 70, 80, 90 → average **80**

## Expected results

| Check | Setup | Expected result |
|---|---|---|
| T1 | AttendanceReport + TextFormatter | `ATTENDANCE REPORT AR-1\nattended: 3/4\nrate: 75%` |
| T2 | AttendanceReport + HtmlFormatter | `<article><h1>Attendance Report AR-1</h1><ul><li>attended: 3/4</li><li>rate: 75%</li></ul></article>` |
| T3 | GradeReport + TextFormatter | `GRADE REPORT GR-1\ngrades: 70, 80, 90\naverage: 80` |
| T4 | GradeReport + HtmlFormatter | `<article><h1>Grade Report GR-1</h1><ul><li>grades: 70, 80, 90</li><li>average: 80</li></ul></article>` |
| T5 | One AttendanceReport: TextFormatter → `setImplementation(HtmlFormatter)` | `sameObject=true`, `stateUnchanged=true`, `state=id=AR-1 attended=3/4 rate=75%`, before = T1 result, after = T2 result |
| T6 | AttendanceReport + MarkdownFormatter | `# Attendance Report AR-1\n\n- **attended:** 3/4\n- **rate:** 75%` |
| T7 | GradeReport + MarkdownFormatter | `# Grade Report GR-1\n\n- **grades:** 70, 80, 90\n- **average:** 80` |

Final line: `SUMMARY: 7/7 PASS`. The actual captured run is in [demo-output.txt](demo-output.txt).

## Extension (I3)

`MarkdownFormatter` was added after the base commit. The only source changes are the new class and
`Main` (T6/T7); `Report`, `AttendanceReport`, `GradeReport`, `Formatter`, `TextFormatter` and
`HtmlFormatter` are unchanged. The diff was produced with:

```bash
git diff 0da31fcda61f99ef99560021967baacf06fd89cf HEAD -- src > extension.diff
```

## Submission files

`src/`, `sources.txt`, `README.md`, `report.pdf`, `demo-output.txt`, `extension.diff`.
