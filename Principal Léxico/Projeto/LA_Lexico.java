package Projeto;

public class LA_Lexico {

	public static void main(String[] args) {

		LA_Lex lex = new LA_Lex(args[0]);
		String txt = "Lexemas encontrados: ";
		Token t = null;

		while ((t = lex.proximoToken()).nome != TipoToken.Fim) {
			System.out.print(t);
			txt += t.lexema + ", ";
		}
		System.out.print(txt);
	}
}