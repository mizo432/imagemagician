package imagemagician;

import imagemagician.constants.FormatType;
import imagemagician.dto.ImageSize;

/**
 * ImageMagick実行結果のハンドラ
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
public final class MagicianHandler {

    private MagicianHandler() { }
    
    public static ImageSize identify(byte[] bytes) {
        return identify(bytes, FormatType.RESOLUTION);
    }

    public static ImageSize identify(byte[] bytes, FormatType format) {
        String response = new String(bytes).trim();
        if(!response.contains(",")) {
            throw new MagicianException("invalid data -> " + response);
        }

        int width;
        int height;

        String[] data = response.split(",");

        switch (format) {
            case GEOMETRY:
                String[] xSplit = data[0].split("x");
                width = Integer.parseInt(xSplit[0]);
                String tmp = xSplit[1];
                String[] plusSplit = tmp.split("\\+");
                height = Integer.parseInt(plusSplit[0]);
                break;
            case RESOLUTION:
            default:
                width = Integer.parseInt(data[0]);
                height = Integer.parseInt(data[1]);
                break;
        }

        return new ImageSize(width, height);
    }
}
