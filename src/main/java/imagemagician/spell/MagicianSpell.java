package imagemagician.spell;

import imagemagician.dto.MagicianMaterial;

import java.util.List;

/**
 * ImageMagickコマンドオプション
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
public interface MagicianSpell {

    MagicianSpell build(MagicianMaterial material);

    List<String> command();

    void clean();
}
