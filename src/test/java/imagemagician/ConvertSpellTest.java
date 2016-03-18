package imagemagician;

import imagemagician.constants.ImageType;
import imagemagician.dto.MagicianMaterial;
import imagemagician.spell.ConvertSpell;
import imagemagician.spell.MagicianSpell;
import org.junit.*;
import junit.framework.JUnit4TestAdapter;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.endsWith;

/**
 * @author adorechic
 */
public class ConvertSpellTest {
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ConvertSpellTest.class);
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test(expected = MagicianException.class)
	public void 未ビルドテスト() {
		ConvertSpell.newSpell("test".getBytes(), ImageType.PNG).command();
	}

	@Test
	public void 通常ケース() {
		MagicianMaterial material = new MagicianMaterial();

		MagicianSpell spell =
				ConvertSpell.newSpell("test".getBytes(), ImageType.PNG)
				.resize(400)
				.autoOrient()
				.colorSpace("RGB")
				.strip()
				.build(material);

		List<String> list = spell.command();
		assertThat(list.get(0), is("convert"));
		assertThat(list.get(1), is(startsWith("/tmp/magician-")));
		assertThat(list.get(1), is(endsWith(".png")));
		assertThat(list.get(2), is("-resize"));
		assertThat(list.get(3), is("400x400>"));
		assertThat(list.get(4), is("-colorspace"));
		assertThat(list.get(5), is("RGB"));
		assertThat(list.get(6), is("-auto-orient"));
		assertThat(list.get(7), is("-strip"));
		assertThat(list.get(8), is("png:-"));

		spell.clean();
	}

	@Test
	public void 最小限指定ケース() {
		MagicianMaterial material = new MagicianMaterial();

		MagicianSpell spell =
				ConvertSpell.newSpell("test".getBytes(), ImageType.GIF)
				.resize(200, 150, ' ')
				.build(material);

		List<String> list = spell.command();
		assertThat(list.get(0), is("convert"));
		assertThat(list.get(1), is(startsWith("/tmp/magician-")));
		assertThat(list.get(1), is(endsWith(".gif")));
		assertThat(list.get(2), is("-resize"));
		assertThat(list.get(3), is("200x150"));
		assertThat(list.get(4), is("gif:-"));

		spell.clean();
	}
	@Test
	public void jpegdefine指定ケース() {
		MagicianMaterial material = new MagicianMaterial();

		MagicianSpell spell =
				ConvertSpell.newSpell("test".getBytes(), ImageType.JPG)
				.resize(200, 150, ' ')
				.build(material);

		List<String> list = spell.command();
		assertThat(list.get(0), is("convert"));
		assertThat(list.get(1), is(startsWith("/tmp/magician-")));
		assertThat(list.get(1), is(endsWith(".jpg")));
		assertThat(list.get(2), is("-define"));
		assertThat(list.get(3), is("jpeg:size=200x150"));
		assertThat(list.get(4), is("-resize"));
		assertThat(list.get(5), is("200x150"));
		assertThat(list.get(6), is("jpg:-"));

		spell.clean();
	}

	@Test
	public void 一時ファイル出力テスト() throws IOException {
		MagicianMaterial material = new MagicianMaterial();

		MagicianSpell spell =
				ConvertSpell.newSpell("test".getBytes(), ImageType.GIF)
				.build(material);

		String path = spell.command().get(1);

		try (FileInputStream fis = new FileInputStream(path);
			 ByteArrayOutputStream ba = new ByteArrayOutputStream();
			 BufferedOutputStream bo = new BufferedOutputStream(ba)) {
			int c;
			while ((c = fis.read()) != -1) {
				bo.write(c);
			}
			bo.flush();

			assertThat(ba.toByteArray(), is("test".getBytes()));
		}

		spell.clean();
	}
}
