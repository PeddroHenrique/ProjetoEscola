package projetoEscola;

public abstract class Pessoa {

	protected String nome;
	protected String cpf;
	protected byte idade;
	
	public String getNome() {
            return nome;
	}
	public void setNome(String nome) {
            String nomeMaiusculo = nome.substring(0,1).toUpperCase();
            String nomeMinusculo = nome.substring(1).toLowerCase();
            nome = nomeMaiusculo + nomeMinusculo;
            this.nome = nome;
	}
	public String getCpf() {
            return cpf;
	}
	public void setCpf(String cpf) {
            String cpfFormatado = cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
            this.cpf = cpfFormatado;
	}
	public int getIdade() {
            return idade;
	}
	public void setIdade(byte idade) {
            this.idade = idade;
	}
	
}
