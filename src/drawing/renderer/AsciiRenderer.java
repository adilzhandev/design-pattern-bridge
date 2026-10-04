package drawing.renderer;

import java.util.Collections;

public class AsciiRenderer implements Renderer {

    private static final String PREFIX = "ASCII";
    private static final String CIRCLE_FILL = "o";
    private static final String SQUARE_FILL = "#";
    private static final String ROW_SEPARATOR = "/";

    @Override
    public String renderCircle(int radius) {
        String art = "(" + CIRCLE_FILL.repeat(2 * radius) + ")";
        return PREFIX + " circle radius=" + radius + " art=" + art;
    }

    @Override
    public String renderSquare(int side) {
        String row = SQUARE_FILL.repeat(side);
        String art = String.join(ROW_SEPARATOR, Collections.nCopies(side, row));
        return PREFIX + " square side=" + side + " art=" + art;
    }
}
