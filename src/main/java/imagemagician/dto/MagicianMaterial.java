package imagemagician.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * ImageMagick情報
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagicianMaterial {
    private String imageMagickBin = "";
    private int bufferSize = 65536;
    private File dataDir = new File("/tmp");
}
