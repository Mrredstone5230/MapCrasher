package me.polishkrowa.mapcrasher;

import org.bukkit.entity.Player;
import org.bukkit.map.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageRenderer extends MapRenderer {

    private BufferedImage image;
    private boolean done;

    public ImageRenderer() {
        done = false;
    }

    public ImageRenderer(String url) {
        load(url);
        done = false;
    }

    public boolean load(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
            image = MapPalette.resizeImage(image); //less ram
        } catch (IOException e) {
            return false;
        }
        this.image = image;
        return true;
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        if (done)
            return;
        canvas.drawImage(0,0,image);

        MapCursorCollection selection = new MapCursorCollection();
        MapCursor cursor = new MapCursor((byte)0, (byte) 0, (byte) 15, MapCursor.Type.TEMPLE, true);
        //    public MapCursor(byte x, byte y, byte direction, @NotNull MapCursor.Type type, boolean visible) {
        //        this(x, y, direction, type, visible, (String)null);
        //    }

        for (int i = 0; i <=400000; i++)
            selection.addCursor(cursor);
            //selection.addCursor(0,0, (byte) 10);

        canvas.setCursors(selection);

        view.setTrackingPosition(true);
        done = true;
    }
}
