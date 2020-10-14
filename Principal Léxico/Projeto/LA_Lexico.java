package Projeto;

public class LA_Lexico {

	public static void main(String[] args) {
		LA_Lex lex = new LA_Lex(args[0]);
		Token t = null;
		while ((t = lex.proximoToken()) != null) {
			System.out.print(t);
		}
	}

}
