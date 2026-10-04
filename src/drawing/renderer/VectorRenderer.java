package drawing.renderer;

public class VectorRenderer implements Renderer {

    private static final String PREFIX = "VECTOR";

    @Override
    public String renderCircle(int radius) {
        return PREFIX + " circle radius=" + radius;
    }

    @Override
    public String renderSquare(int side) {
        return PREFIX + " square side=" + side;
    }
}
