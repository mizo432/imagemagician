package imagemagician.constants;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 画像タイプ
 * @author adorechic
 */
public enum ImageType {
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    ;

    private String ext;
    private String contentType;

    ImageType(String ext, String contentType) {
        this.ext = ext;
        this.contentType = contentType;
    }

    private static final Map<String, ImageType> MAPPING = Maps.newHashMap();
    static {
        for (ImageType type : values()) MAPPING.put(type.contentType(), type);
    }

    public String ext() {
        return ext;
    }

    public String contentType() {
        return contentType;
    }

    public static ImageType get(String contentType) {
        return MAPPING.get(contentType);
    }
}
