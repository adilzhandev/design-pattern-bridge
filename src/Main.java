import drawing.renderer.AsciiRenderer;
import drawing.renderer.RasterRenderer;
import drawing.renderer.Renderer;
import drawing.renderer.VectorRenderer;
import drawing.shape.Circle;
import drawing.shape.Shape;
import drawing.shape.Square;

public class Main {

    private static final String DEMO_FLAG = "--demo";
    private static final int CIRCLE_RADIUS = 2;
    private static final int SQUARE_SIDE = 3;

    private int passed;
    private int total;

    public static void main(String[] args) {
        if (args.length == 1 && DEMO_FLAG.equals(args[0])) {
            new Main().runDemo();
        } else {
            System.out.println("Usage: java -cp out Main " + DEMO_FLAG);
        }
    }

    private void runDemo() {
        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();
        Renderer ascii = new AsciiRenderer();

        checkCombination("T1", new Circle("C-1", CIRCLE_RADIUS, vector), vector,
                "VECTOR circle radius=2");
        checkCombination("T2", new Circle("C-2", CIRCLE_RADIUS, raster), raster,
                "RASTER circle radius=2 pixels=4x4");
        checkCombination("T3", new Square("S-1", SQUARE_SIDE, vector), vector,
                "VECTOR square side=3");
        checkCombination("T4", new Square("S-2", SQUARE_SIDE, raster), raster,
                "RASTER square side=3 pixels=3x3");
        checkRuntimeSwitch("T5", vector, raster,
                "VECTOR circle radius=2", "RASTER circle radius=2 pixels=4x4");
        checkCombination("T6", new Circle("C-6", CIRCLE_RADIUS, ascii), ascii,
                "ASCII circle radius=2 art=(oooo)");
        checkCombination("T7", new Square("S-7", SQUARE_SIDE, ascii), ascii,
                "ASCII square side=3 art=###/###/###");

        printSummary();
    }

    private void checkCombination(String checkId, Shape shape, Renderer renderer, String expected) {
        String actual = shape.execute();
        report(checkId, expected.equals(actual),
                classNames(shape, renderer) + " | result=" + actual,
                expected);
    }

    private void checkRuntimeSwitch(String checkId, Renderer first, Renderer second,
                                    String expectedBefore, String expectedAfter) {
        Circle original = new Circle("C-5", CIRCLE_RADIUS, first);
        String idBefore = original.getId();
        int radiusBefore = original.getRadius();

        Circle shape = original;
        String before = shape.execute();
        shape.setImplementation(second);
        String after = shape.execute();

        boolean sameObject = shape == original;
        boolean stateUnchanged = idBefore.equals(shape.getId()) && radiusBefore == shape.getRadius();
        boolean pass = sameObject && stateUnchanged
                && expectedBefore.equals(before) && expectedAfter.equals(after);

        report(checkId, pass,
                classNames(shape, first) + " -> " + second.getClass().getSimpleName()
                        + " | sameObject=" + sameObject
                        + " | stateUnchanged=" + stateUnchanged
                        + " (id=" + shape.getId() + ", radius=" + shape.getRadius() + ")"
                        + "\n    before=" + before + " | after=" + after,
                "sameObject=true | stateUnchanged=true | before=" + expectedBefore
                        + " | after=" + expectedAfter);
    }

    private String classNames(Shape shape, Renderer renderer) {
        return shape.getClass().getSimpleName() + " + " + renderer.getClass().getSimpleName();
    }

    private void report(String checkId, boolean pass, String details, String expected) {
        total++;
        if (pass) {
            passed++;
        }
        System.out.println(checkId + " " + (pass ? "PASS" : "FAIL") + " | " + details);
        if (!pass) {
            System.out.println("    expected=" + expected);
        }
    }

    private void printSummary() {
        int failed = total - passed;
        String summary = failed == 0
                ? passed + "/" + total + " PASS"
                : passed + "/" + total + " PASS, " + failed + " FAIL";
        System.out.println("SUMMARY: " + summary);
    }
}
