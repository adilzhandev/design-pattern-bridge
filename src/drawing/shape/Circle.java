package drawing.shape;

import drawing.renderer.Renderer;

public class Circle extends Shape {

    private final int radius;

    public Circle(String id, int radius, Renderer renderer) {
        super(id, renderer);
        this.radius = requirePositive(radius, "radius");
    }

    public int getRadius() {
        return radius;
    }

    @Override
    protected String draw(Renderer renderer) {
        return renderer.renderCircle(radius);
    }
}
