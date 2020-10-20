package Projeto;

public class LA_MainLexico {

	public static void main(String[] args) {

		LA_Lexico lex = new LA_Lexico(args[0]);
		String txt = "Lexemas encontrados: ";
		Token t = null;

		while ((t = lex.nextToken()).nome != TipoToken.Fim) {
			System.out.print(t);
			txt += t.lexema + ", ";
		}
		System.out.print(txt);
	}
}