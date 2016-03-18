package imagemagician.constants;

/**
 * フォーマットタイプ
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
public enum FormatType {
    RESOLUTION("%w,%h,"),
    GEOMETRY("%g,"),
    ;

    private final String pattern;

    FormatType(String pattern) {
        this.pattern = pattern;
    }

    public String pattern() {
        return pattern;
    }

    public static FormatType get(ImageType type) {
        if (type == ImageType.GIF) {
            return GEOMETRY;
        }
        return RESOLUTION;
    }
}
