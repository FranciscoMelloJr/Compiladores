package Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorDeArquivosTexto {

	InputStream inputs;
	private final static int TAMANHO_BUFFER = 20;
	Buffer buffer = new Buffer(TAMANHO_BUFFER);
	Lexema lexema = new Lexema();

	public LeitorDeArquivosTexto(String arquivo) {
		try {
			inputs = new FileInputStream(new File(arquivo));
			recarregarBuffer0();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int lerCaractereDoBuffer() {

		int caractere = buffer.vetor[buffer.getPonteiro()];
		incrementarPonteiro();
		return caractere;
	}

	private void incrementarPonteiro() {

		buffer.incrementaPonteiro();
		if (buffer.getPonteiro() == TAMANHO_BUFFER) {
			recarregarBuffer1();
		} else if (buffer.getPonteiro() == TAMANHO_BUFFER * 2) {
			recarregarBuffer0();
			buffer.setPonteiro(0);
		}
	}

	private void recarregarBuffer0() {

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

	private void recarregarBuffer1() {

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

		int caractere = lerCaractereDoBuffer();
		System.out.print((char) caractere);
		lexema.setNome(lexema.getNome() + (char) caractere);
		return caractere;

	}

	public void retroceder() {

		buffer.retrocederPonteiro();
		lexema.setNome(lexema.getNome().substring(0, lexema.getNome().length() - 1));
		if (buffer.getPonteiro() < 0) {
			buffer.setPonteiro(TAMANHO_BUFFER * 2 - 1);
		}
	}

}
