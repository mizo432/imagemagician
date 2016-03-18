package imagemagician;

import imagemagician.constants.ImageType;
import imagemagician.dto.MagicianMaterial;
import imagemagician.spell.ConvertSpell;
import imagemagician.spell.MagicianSpell;
import org.junit.*;
import junit.framework.JUnit4TestAdapter;

import java.io.*;

/**
 * @author adorechic
 */
public class MagicianTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MagicianTest.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void PNG変換テスト() throws IOException{
        byte[] src = read(getFullPath("/test01.png"));

        MagicianSpell spell = ConvertSpell.newSpell(src, ImageType.PNG)
                .resize(400)
                .colorSpace("RGB")
                .autoOrient()
                .strip();

        MagicianMaterial material = new MagicianMaterial();
        Magician magician = Magician.newMagician(material);
        MagicianFuture future = magician.setup(spell).cast();
        future.result();
    }

    @Test
    public void GIF変換テスト() throws IOException{

        byte[] src = read(getFullPath("/test02.gif"));

        MagicianSpell spell = ConvertSpell.newSpell(src, ImageType.GIF)
                .resize(200)
                .colorSpace("RGB")
                .autoOrient()
                .strip();

        MagicianMaterial material = new MagicianMaterial();
        Magician magician = Magician.newMagician(material);
        MagicianFuture future = magician.setup(spell).cast();
        future.result();
    }

    @Test
    public void JPG変換テスト() throws IOException{

        byte[] src = read(getFullPath("/test03.jpg"));

        MagicianSpell spell = ConvertSpell.newSpell(src, ImageType.JPG)
                .resize(200)
                .colorSpace("RGB")
                .autoOrient()
                .strip();

        MagicianMaterial material = new MagicianMaterial();
        Magician magician = Magician.newMagician(material);
        MagicianFuture future = magician.setup(spell).cast();
        future.result();
    }

    private void write(byte[] data, String path) throws IOException {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(path);
            fos.write(data);
        } finally {
            if(fos != null) fos.close();
        }
    }

    private String getFullPath(String path) {
        return getClass().getResource(path).getPath();
    }

    private byte[] read(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             ByteArrayOutputStream ba = new ByteArrayOutputStream();
             BufferedOutputStream bo = new BufferedOutputStream(ba)) {
            int c;
            while ((c = fis.read()) != -1) {
                bo.write(c);
            }
            bo.flush();
            return ba.toByteArray();
        }
    }
}
