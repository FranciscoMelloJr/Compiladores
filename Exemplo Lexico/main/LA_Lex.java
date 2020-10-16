package main;

public class LA_Lex {

	LeitorDeArquivosTexto ldat;

	public LA_Lex(String arquivo) {
		ldat = new LeitorDeArquivosTexto(arquivo);
	}
	
	public Token proximoToken() {

		int caractereLido = -1;
		while ((caractereLido = ldat.lerProximoCaractere()) != -1) {
			char c = (char) caractereLido;

			if (c != ' ' && c != '\n') {
				switch (c) {
				case ':': return new Token(TipoToken.Delim, ":");
				case '*': return new Token(TipoToken.OpAritMult, "*");
				case '/': return new Token(TipoToken.OpAritDiv, "/");
				case '+': return new Token(TipoToken.OpAritSoma, "+");
				case '-': return new Token(TipoToken.OpAritSub, "-");
				case '(': return new Token(TipoToken.AbrePar, "(");
				case ')': return new Token(TipoToken.FechaPar, ")");
				case '>':
					c = (char) ldat.lerProximoCaractere();
					if (c == '=') return new Token(TipoToken.OpRelMaiorIgual, ">=");					
					else {
							ldat.retroceder();
							return new Token(TipoToken.OpRelMaior, ">");
					}
				case '<':
					c = (char) ldat.lerProximoCaractere();
					if (c == '=') {
						return new Token(TipoToken.OpRelMenorIgual, "<=");
					} else if (c == '>') {						
						return new Token(TipoToken.OpRelDif, "<>");
					} else { 
						ldat.retroceder();
						return new Token(TipoToken.OpRelMenor, "<");
					}
				}
			}	
		}
		return null;
	}
	
	
	
}