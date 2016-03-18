imagemagician
======================
im4javaライクなJava用imagemagickクライアントです。
ProcessBuilderによるコマンド発行を利用し非同期に処理を行います。

使い方
------

	public void PNG変換テスト() throws IOException {
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
