package projetoEscola;

import java.util.ArrayList;
import java.util.List;
import interfaces.PermitirAcesso;

public class Aluno extends Pessoa implements PermitirAcesso {
	
	private String login;
	private String senha;
	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	public String imprimirDisciplinas() {
		String retorno = "";
		int contador = 1;
		for (Disciplina disciplina : disciplinas) {
			retorno = retorno + contador + disciplina + "\n";
			contador++;
		}
		return retorno;
	}
	
	public Aluno(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public Aluno() {
		
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	@Override
	public String toString() {
		return "Aluno:\n\n" + imprimirDisciplinas() + "Nome = " + nome + "\n" + "CPF = " + cpf + "\n" + "Idade = " + idade;
	}

	@Override
	public boolean autenticar(String login, String senha) {
            this.login = login;
            this.senha = senha;
            return autenticar();
	}

	@Override
	public boolean autenticar() {
            return login.equals("admin") && senha.equals("admin");
	}
	
	
}
