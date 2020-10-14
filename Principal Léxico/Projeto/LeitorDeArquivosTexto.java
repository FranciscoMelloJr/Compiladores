package Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorDeArquivosTexto {

	InputStream inputs;
	private final static int TAMANHO_BUFFER = 20;
	Buffer buffer = new Buffer();
	int inicioLexema;
	private String lexema;

	public LeitorDeArquivosTexto(String arquivo) {
		try {
			inputs = new FileInputStream(new File(arquivo));
			inicializarBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inicializarBuffer() {

		buffer.duplo = true;
		inicioLexema = 0;
		lexema = "";
		buffer.setTamanho(TAMANHO_BUFFER * 2);
		buffer.setPonteiro(0);
		recarregarBuffer1();

	}

	private int lerCaractereDoBuffer() {

		int ret = buffer.getPonteiro();
		incrementarPonteiro();
		return ret;
	}

	private void incrementarPonteiro() {

		buffer.incrementaPonteiro();
		if (buffer.getPonteiro() == TAMANHO_BUFFER) {
			recarregarBuffer2();
		} else if (buffer.getPonteiro() == TAMANHO_BUFFER * 2) {
			recarregarBuffer1();
			buffer.setPonteiro(0);
		}
	}

	private void recarregarBuffer1() {

		if (buffer.isDuplo()) {
			buffer.setDuplo(false);

			try {
				for (int i = 0; i < TAMANHO_BUFFER; i++) {
					buffer.vetor[i] = inputs.read();
					if (buffer.vetor[i] == -1) {
						break;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}

	private void recarregarBuffer2() {

		if (!buffer.isDuplo()) {
			buffer.setDuplo(true);

			try {
				for (int i = TAMANHO_BUFFER; i < TAMANHO_BUFFER * 2; i++) {
					buffer.vetor[i] = inputs.read();
					if (buffer.vetor[i] == -1) {
						break;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}

	public int lerProximoCaractere() {

		int c = lerCaractereDoBuffer();
		System.out.print((char) c);
		lexema = lexema + (char) c;
		return c;

	}

	public void retroceder() {

		buffer.retrocederPonteiro();
		lexema = lexema.substring(0, lexema.length() - 1);
		if (buffer.getPonteiro() < 0) {
			buffer.setPonteiro(TAMANHO_BUFFER * 2 - 1);
		}
	}

}
