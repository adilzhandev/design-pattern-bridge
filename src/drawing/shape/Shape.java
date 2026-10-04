package drawing.shape;

import drawing.renderer.Renderer;

import java.util.Objects;

public abstract class Shape {

    private final String id;
    private Renderer renderer;

    protected Shape(String id, Renderer renderer) {
        this.id = Objects.requireNonNull(id, "id");
        this.renderer = Objects.requireNonNull(renderer, "renderer");
    }

    public String execute() {
        return draw(renderer);
    }

    public void setImplementation(Renderer renderer) {
        this.renderer = Objects.requireNonNull(renderer, "renderer");
    }

    public String getId() {
        return id;
    }

    protected abstract String draw(Renderer renderer);

    protected static int requirePositive(int value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive: " + value);
        }
        return value;
    }
}
