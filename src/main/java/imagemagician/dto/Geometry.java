package imagemagician.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 座標情報
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    public static final char NONE_OPT = ' ';

    private int width;
    private int height;
    private int x = 0;
    private int y = 0;
    private char option = ' ';

    public Geometry(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(width > 0) sb.append(width);
        if(height > 0) sb.append('x').append(height);
        if (option != ' ') sb.append(option);
        if ( x > 0 || y > 0) sb.append('+').append(x).append('+').append(y);
        return sb.toString();
    }
}
