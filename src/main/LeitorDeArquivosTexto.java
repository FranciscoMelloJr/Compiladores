package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorDeArquivosTexto {

	InputStream istr;
	private final static int TAMANHO_BUFFER = 5;
	int[] bufferDeLeitura;
	int ponteiro;

	public LeitorDeArquivosTexto(String arquivo) {
		try {
			istr = new FileInputStream(new File(arquivo));
			inicializarBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inicializarBuffer() {

		bufferDeLeitura = new int[TAMANHO_BUFFER * 2];
		ponteiro = 0;
		recarregarBuffer1();

	}

	private int lerCaractereDoBuffer() {

		int ret = bufferDeLeitura[ponteiro];
		incrementarPonteiro();
		return ret;
	}

	private void incrementarPonteiro() {

		ponteiro++;
		if (ponteiro == TAMANHO_BUFFER) {
			recarregarBuffer2();
		} else if (ponteiro == TAMANHO_BUFFER * 2) {
			recarregarBuffer1();
			ponteiro = 0;
		}
	}

	private void recarregarBuffer1() {

		try {
			for (int i = 0; i < TAMANHO_BUFFER; i++) {
				bufferDeLeitura[i] = istr.read();
				if (bufferDeLeitura[i] == -1) {
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}

	private void recarregarBuffer2() {

		try {
			for (int i = TAMANHO_BUFFER; i < TAMANHO_BUFFER * 2; i++) {
				bufferDeLeitura[i] = istr.read();
				if (bufferDeLeitura[i] == -1) {
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}

	}

	public int lerProximoCaractere() {

		int c = lerCaractereDoBuffer();
		System.out.print((char)c);
		return c;
		
		/*	try {
			int ret = istr.read();
			System.out.print((char) ret);
			return ret;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return -1;
		}
	}
		 */
		}
	
	public void retroceder() {
		
		ponteiro--;
		if (ponteiro <0) {
			ponteiro = TAMANHO_BUFFER * 2 -1 ;
		}
	}
	
}