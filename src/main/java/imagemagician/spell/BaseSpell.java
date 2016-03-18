package imagemagician.spell;

import com.google.common.base.Joiner;
import imagemagician.MagicianException;
import imagemagician.constants.ImageType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * ImageMagickコマンドオプションのベース
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/13 11:52
 */
abstract class BaseSpell implements MagicianSpell {

    protected byte[] src;
    protected ImageType type;

    protected File tmpFile;
    protected List<String> command;

    protected BaseSpell(byte[] src, ImageType type) {
        this.src = src;
        this.type = type;
    }

    protected void destTmpFile(File dataDir) throws IOException {
        tmpFile = File.createTempFile("magician-", "." + type.ext(), dataDir);
        try (FileOutputStream fos =  new FileOutputStream(tmpFile)) {
            fos.write(src);
        }
    }

    @Override
    public List<String> command() {
        if(command == null) throw new MagicianException("not build yet.");
        return command;
    }

    @Override
    public void clean() {
        if (tmpFile != null) tmpFile.delete();
    }

    @Override
    public String toString() {
        return "command -> " + Joiner.on(' ').join(command);
    }
}
