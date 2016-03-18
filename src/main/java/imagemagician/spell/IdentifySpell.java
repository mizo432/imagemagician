package imagemagician.spell;

import imagemagician.MagicianException;
import imagemagician.constants.FormatType;
import imagemagician.constants.ImageType;
import imagemagician.dto.MagicianMaterial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * identifyコマンドオプション
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
public class IdentifySpell extends BaseSpell {

    private FormatType format = FormatType.RESOLUTION;

    private IdentifySpell(byte[] src, ImageType type) {
        super(src, type);
    }

    public static IdentifySpell newSpell(byte[] src, ImageType type) {
        return new IdentifySpell(src, type);
    }

    public IdentifySpell format(FormatType format) {
        this.format = format;
        return this;
    }

    @Override
    public MagicianSpell build(MagicianMaterial material) {
        try {
            destTmpFile(material.getDataDir());
            this.src = null;
        } catch (IOException e) {
            throw new MagicianException(e);
        }
        List<String> list = new ArrayList<>();
        list.add(material.getImageMagickBin() + "identify");
        list.add("-format");
        list.add(format.pattern());
        list.add(tmpFile.getAbsolutePath());

        this.command = list;
        return this;
    }
}
