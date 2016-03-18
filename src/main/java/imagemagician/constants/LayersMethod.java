package imagemagician.constants;

/**
 * レイヤーメソッド
 * Created by IntelliJ IDEA.
 * User: yosuque
 * Date: 2016/01/12 20:03
 */
public enum LayersMethod {
    OPTIMIZE("optimize"),
    OPTIMIZE_FRAME("optimize-frame"),
    OPTIMIZE_PLUS("optimize-plus"),
    OPTIMIZE_TRANSPARENCY("optimize-transparency"),
    ;

    private final String method;

    LayersMethod(String method) {
        this.method = method;
    }

    public String method() {
        return method;
    }
}
