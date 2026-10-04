package drawing.renderer;

public class RasterRenderer implements Renderer {

    private static final String PREFIX = "RASTER";

    @Override
    public String renderCircle(int radius) {
        int diameter = 2 * radius;
        return PREFIX + " circle radius=" + radius + " pixels=" + grid(diameter);
    }

    @Override
    public String renderSquare(int side) {
        return PREFIX + " square side=" + side + " pixels=" + grid(side);
    }

    private String grid(int size) {
        return size + "x" + size;
    }
}
