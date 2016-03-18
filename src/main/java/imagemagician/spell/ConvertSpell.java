package imagemagician.spell;

import com.google.common.collect.Lists;
import imagemagician.MagicianException;
import imagemagician.constants.ImageType;
import imagemagician.constants.LayersMethod;
import imagemagician.dto.Geometry;
import imagemagician.dto.MagicianMaterial;

import java.io.IOException;
import java.util.List;

/**
 * convertコマンドオプション
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
public class ConvertSpell extends BaseSpell {

    private String define;
    private Geometry cropGeometry;
    private boolean crop = false;
    private Geometry resizeGeometry;
    private boolean resize = false;
    private int quality = 0;
    private List<Geometry> chopGeometrys;
    private boolean chop = false;
    private String colorSpace;
    private boolean autoOrient;
    private boolean strip;
    private int depth = 0;
    private int colors = 0;
    private String unsharp;
    private String blur;
    private boolean coalesce = false;
    private boolean deconstruct = false;
    private LayersMethod layers;

    private ConvertSpell(byte[] src, ImageType type) {
        super(src, type);
    }

    public static ConvertSpell newSpell(byte[] src, ImageType type) {
        return new ConvertSpell(src, type);
    }

    public ConvertSpell define(String str) {
        this.define = str;
        return this;
    }

    public ConvertSpell crop(int width, int height, int x, int y) {
        this.cropGeometry = new Geometry(width, height, x, y, Geometry.NONE_OPT);
        this.crop = true;
        return this;
    }

    public ConvertSpell resize(int size) {
        return resize(size, size);
    }

    public ConvertSpell resize(int width, int height) {
        return resize(width, height, '>');
    }

    public ConvertSpell resize(int width, int height, char c) {
        this.resizeGeometry = new Geometry(width, height, 0, 0, c);
        this.resize = true;
        return this;
    }

    public ConvertSpell quality(int quality) {
        this.quality = quality;
        return this;
    }

    public ConvertSpell chop(List<Geometry> chopGeometrys) {
        this.chopGeometrys = chopGeometrys;
        this.chop = true;
        return this;
    }

    public ConvertSpell unsharp(String unsharp) {
        this.unsharp = unsharp;
        return this;
    }

    public ConvertSpell blur(String blur) {
        this.blur = blur;
        return this;
    }

    public ConvertSpell depth(int depth) {
        this.depth = depth;
        return this;
    }

    public ConvertSpell colors(int colors) {
        this.colors = colors;
        return this;
    }

    public ConvertSpell colorSpace(String str) {
        this.colorSpace = str;
        return this;
    }

    public ConvertSpell autoOrient() {
        this.autoOrient = true;
        return this;
    }

    public ConvertSpell strip() {
        this.strip = true;
        return this;
    }

    public ConvertSpell coalesce() {
        this.coalesce = true;
        return this;
    }

    public ConvertSpell deconstruct() {
        this.deconstruct = true;
        return this;
    }

    public ConvertSpell layers(LayersMethod layers) {
        this.layers = layers;
        return this;
    }

    @Override
    public MagicianSpell build(MagicianMaterial material) {
        try{
            destTmpFile(material.getDataDir());
            this.src = null;

            List<String> list = Lists.newArrayList();
            list.add(material.getImageMagickBin() + "convert");
            list.add(tmpFile.getAbsolutePath());

            if(ImageType.JPG.equals(type)) {
                define = "jpeg:size=" + getImageGeometry(resizeGeometry);
            }
            if(define != null) {
                // -define jpeg:size=100x100
                list.add("-define");
                list.add(define);
            }
            if (coalesce) {
                // -coalesce
                list.add("-coalesce");
            }
            if(crop) {
                // -crop 100x100+10+10
                list.add("-crop");
                list.add(getImageGeometry(cropGeometry));
                list.add("+repage");
            }
            if (resize) {
                // -resize 100x100
                list.add("-resize");
                list.add(getImageGeometry(resizeGeometry));
            }
            if (quality > 0) {
                // -quality 80
                list.add("-quality");
                list.add(String.valueOf(quality));
            }
            if(colorSpace != null) {
                // -colorspace RGB
                list.add("-colorspace");
                list.add(colorSpace);
            }
            if (chop && chopGeometrys != null) {
                for (Geometry geometry : chopGeometrys) {
                    // -chop 100x100+10+10
                    list.add("-chop");
                    list.add(getImageGeometry(geometry));
                }
            }
            if (unsharp != null) {
                // -unsharp 12x6+0.5+0
                list.add("-unsharp");
                list.add(unsharp);
            }
            if (blur != null) {
                // -gaussian-blur 0x10
                list.add("-gaussian-blur");
                list.add(blur);
            }
            if (depth > 0) {
                // -depth 8
                list.add("-depth");
                list.add(String.valueOf(depth));
            }
            if (colors > 0) {
                // -colors 256
                list.add("-colors");
                list.add(String.valueOf(colors));
            }
            if (deconstruct) {
                // -deconstruct
                list.add("-deconstruct");
            }
            if (layers != null) {
                // -layers optimize
                list.add("-layers");
                list.add(layers.method());
            }
            if(autoOrient) {
                // -auto-orient
                list.add("-auto-orient");
            }
            if(strip) {
                // -strip
                list.add("-strip");
            }

            //png:-
            list.add(type.ext() + ":-");

            this.command = list;

            return this;
        } catch (IOException e) {
            throw new MagicianException(e);
        }
    }

    private String getImageGeometry(Geometry geometry) {
        return geometry != null ? geometry.toString() : "";
    }
}
