package projetoEscola;

public class Disciplina {
	private String status;
	private double nota;
	private String disciplina;
        
        public void setMedia() {
            nota = nota / 4;
            if (nota >= 7) {
                    setStatus(VariaveisStaticas.APROVADO);
            } else if (nota < 5) {
                    setStatus(VariaveisStaticas.REPROVADO);
            } else {
                    setStatus(VariaveisStaticas.RECUPERACAO);
            }
        }

	public double getNota() {
		return nota;
	}

	public void setNota(Double notaInput) {
		this.nota = this.nota + notaInput;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
            String disciplinaMaiusculo = disciplina.substring(0,1).toUpperCase();
            String disciplinaMinusculo = disciplina.substring(1).toLowerCase();
            disciplina = disciplinaMaiusculo + disciplinaMinusculo;
            this.disciplina = disciplina;
	}

	@Override
	public String toString() {
		return " Disciplina = " + getDisciplina() + "\n" + "Nota = " + getNota() + "\n" + getStatus() + "\n";
	}

}