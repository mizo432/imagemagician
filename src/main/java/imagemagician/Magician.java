package imagemagician;

import imagemagician.dto.MagicianMaterial;
import imagemagician.spell.MagicianSpell;

import java.io.*;
import java.util.concurrent.*;

/**
 * @author adorechic
 */
public class Magician implements MagicianFuture {

	private final MagicianMaterial material;

	private Process process;
	private FutureTask<byte[]> productOut;
	private FutureTask<byte[]> errorOut;
	private MagicianSpell spell;

	public static Magician newMagician(MagicianMaterial material) {
		return new Magician(material);
	}

	private Magician(MagicianMaterial material) {
		this.material = material;
	}

	public Magician setup(MagicianSpell spell) {
		Magician magician = new Magician(material);
		return magician.buildSpell(spell);
	}

	private Magician buildSpell(MagicianSpell spell) {
		this.spell = spell.build(material);
		return this;
	}

	public MagicianFuture cast() {
		try {
			return startProcess();
		} catch (IOException e) {
			throw new MagicianException(e);
		}
	}

	private MagicianFuture startProcess() throws IOException {
		this.process = new ProcessBuilder(spell.command()).start();
		this.productOut = listens(process.getInputStream());
		this.errorOut = listens(process.getErrorStream());
		new Thread(productOut).start();
		new Thread(errorOut).start();

		return this;
	}

	private FutureTask<byte[]> listens(final InputStream is) {
		return new FutureTask<>(
				() -> consumeOutput(is)
		);
	}

	private byte[] consumeOutput(InputStream is) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(is, material.getBufferSize());
			 ByteArrayOutputStream ba = new ByteArrayOutputStream();
			 BufferedOutputStream bo = new BufferedOutputStream(ba)) {

			int c;
			while ((c = bis.read()) != -1) {
				bo.write(c);
			}
			bo.flush();
			return ba.toByteArray();
		}
	}

	@Override
	public byte[] result() {
		String error = null;
		try {
			error = new String(errorOut.get());
			byte[] dst = productOut.get();
			process.waitFor();

			int rc = process.exitValue();
			if (rc != 0) {
				StringBuilder sb = new StringBuilder("rc=").append(rc);
				if (error.length() != 0) {
					sb.append(",error=").append(error);
				}
				sb.append(",").append(spell.toString());
				throw new MagicianException(sb.toString());
			}

			return dst;
		} catch (Exception e) {
			throw new MagicianException(error, e);
		} finally {
			try {
				spell.clean();
				process.getInputStream().close();
				process.getErrorStream().close();
				process.getOutputStream().close();
			} catch (IOException e) {
				throw new MagicianException(spell.toString(), e);
			}
		}
	}

	@Override
	public String toString() {
		return spell.toString();
	}
}
