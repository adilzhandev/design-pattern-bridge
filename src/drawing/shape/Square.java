package drawing.shape;

import drawing.renderer.Renderer;

public class Square extends Shape {

    private final int side;

    public Square(String id, int side, Renderer renderer) {
        super(id, renderer);
        this.side = requirePositive(side, "side");
    }

    public int getSide() {
        return side;
    }

    @Override
    protected String draw(Renderer renderer) {
        return renderer.renderSquare(side);
    }
}
