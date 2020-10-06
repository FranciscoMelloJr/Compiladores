package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorDeArquivosTexto {
	
	InputStream istr;
	
	public LeitorDeArquivosTexto(String arquivo) {
		try {
			istr = new FileInputStream(new File(arquivo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int lerProximoCaractere() {
		try {
			int ret = istr.read();
			System.out.print((char)ret);
			return ret;
		} catch (Exception e) { 
			e.printStackTrace(System.err);
			return -1;
		}
	}

}
