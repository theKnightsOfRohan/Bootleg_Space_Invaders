import processing.core.PImage;

public class Image extends Main_Advanced {
    PImage sprite;
    int x, y;
    public Image(int x, int y, int length, int stature, String image) {
        this.x = x;
        this.y = y;
        this.sprite = loadImage(image);
        this.sprite.resize(length, stature);
        startScreenImages.add(this);
    }
}
