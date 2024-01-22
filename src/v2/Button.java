package v2;

import processing.core.PImage;
import java.util.function.Function;

public class Button extends Image {
    Function<Void, Void> onClick;

    public Button(int x, int y, int w, int h, PImage img, Function<Void, Void> onClick) {
        super(x, y, w, h, img);
        this.onClick = onClick;
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h);
    }
}
