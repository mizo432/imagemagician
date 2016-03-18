package imagemagician.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 画像サイズ情報
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageSize {
    private int width;
    private int height;
}
