package v2;
import processing.core.PImage;

public class Image extends Main {
    PImage sprite;
    int x, y;
    public Image(int x, int y, int length, int stature, String imageURL) {
        this.x = x;
        this.y = y;
        this.sprite = loadImage(imageURL);
        this.sprite.resize(length, stature);
        startScreenImages.add(this);
    }
}
