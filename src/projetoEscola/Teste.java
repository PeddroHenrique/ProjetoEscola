package projetoEscola;
	
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import interfaces.PermitirAcesso;

public class Teste {
	
    public static void main(String[] args) throws MyCustomException{
            /*Lista com os alunos*/
            List<Aluno> alunos = new ArrayList<>();

            // Criação dos campos de entrada
            JTextField loginField = new JTextField();
            JPasswordField senhaField = new JPasswordField();

            // Array de objetos com os componentes para exibir no painel
            Object[] camposAutenticar = {"Login:", loginField, "Senha:", senhaField};

            boolean autenticado = false;

            while (!autenticado) {
                // Exibição do JOptionPane com o painel personalizado
                int opcao = JOptionPane.showConfirmDialog(null, camposAutenticar, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                // Verificar qual botão foi pressionado
                if (opcao == JOptionPane.OK_OPTION) {
                    // Obter os valores dos campos de entrada
                    String login = loginField.getText().trim();
                    String senha = new String(senhaField.getPassword()).trim();

                    PermitirAcesso permitir = new Aluno();

                    if (permitir.autenticar(login, senha)) {
                        autenticado = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Login ou senha incorretos. Tente novamente.", "Erro de autenticação", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
                    throw new RuntimeException("Programa encerrado pelo usuário.");
                }
            }

            int resposta = JOptionPane.YES_OPTION;
            while (resposta == JOptionPane.YES_OPTION) {
                    Aluno aluno = new Aluno();
                    alunos.add(aluno);
                    preencherDadosAluno(aluno);

                    resposta = JOptionPane.showConfirmDialog(null, "Deseja adicionar outro aluno?", "Adicionar aluno", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }

            for(int i = 0; i < alunos.size(); i++) {
            JOptionPane.showMessageDialog(null, alunos.get(i).toString(), "Resultado para o aluno " + (i+1), JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public static void preencherDadosAluno(Aluno aluno) throws MyCustomException{

        boolean dadosValidos = false;

        while (!dadosValidos) {
            try {
                JTextField nome = new JTextField();
                JTextField idade = new JTextField();
                JTextField cpf = new JTextField();
                Object[] camposDadosAluno = {"Nome", nome, "Idade", idade, "Cpf", cpf};
                int dadosAlunoCancelar = JOptionPane.showConfirmDialog(null, camposDadosAluno, "Dados do Aluno", JOptionPane.OK_CANCEL_OPTION);
                if (dadosAlunoCancelar == 2 || dadosAlunoCancelar == -1) {
                    System.exit(0);
                }
                aluno.setNome(obterNome(nome.getText()));
                aluno.setIdade(obterIdade(idade.getText()));
                aluno.setCpf(obterCpf(cpf.getText()));
                byte materiaQuatidade = obterDisciplinas("Digite a quantidade de disciplinas", "Disciplinas");
                for (int x = 0; x < materiaQuatidade; x++) {
                    Disciplina disciplina = new Disciplina();
                    disciplina.setDisciplina(obterDisciplina("Qual o nome da disciplina?", "Disciplina"));

                    for (int i = 0; i < 4; i++) {
                            disciplina.setNota(obterNotas("Nota" + (i + 1), "notas"));
                    }
                    disciplina.setMedia();
                    aluno.getDisciplinas().add(disciplina);
                }
                int escolha = JOptionPane.showConfirmDialog(null, "Deseja remover alguma disciplina?", "Removendo Disciplina", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                while (escolha == 0) {
                    byte removerDisciplinaValor = removerDisciplina("Escreva o número da disciplina: \n\n" + aluno.imprimirDisciplinas(), "Removendo Disciplina");
                    if (removerDisciplinaValor == 0) {
                        break;
                    }else if (removerDisciplinaValor > aluno.getDisciplinas().size()) {
                        JOptionPane.showMessageDialog(null, "O número inserido não existe!\n Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    aluno.getDisciplinas().remove(removerDisciplinaValor - 1);
                    escolha = JOptionPane.showConfirmDialog(null, "Deseja remover mais alguma disciplina?", "Removendo Disciplina", JOptionPane.YES_NO_OPTION);
                }

                dadosValidos = true;
            } catch(NumberFormatException e) {
                e.printStackTrace();
            } catch(IllegalArgumentException | MyCustomException e) {
                e.printStackTrace();
            }
        }   
    JOptionPane.showMessageDialog(null, "Preenchimento do aluno finalizado.");
    }

    public static String obterNome(String mensagem) throws MyCustomException {
        String valor = mensagem;
        
        if (valor == null) {
            JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("Programa encerrado pelo usuário.");
        }else if (valor.trim().isEmpty() || !valor.matches("^[\\p{L}\\s]+$")) {
            JOptionPane.showMessageDialog(null, "Insira um dado válido no campo NOME!", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new MyCustomException("Ocorreu um erro no campo NOME.");
        }
        return valor;
    }
    
    public static byte obterIdade(String mensagem) throws MyCustomException {
        String valor = mensagem;
        byte valorByte = 0;
        
        if (valor == null) {
            JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("Programa encerrado pelo usuário.");
        } else if (valor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira um dado válido no campo IDADE!", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new MyCustomException("Ocorreu um erro no campo IDADE.");
        } 
        
        try {
            valorByte = Byte.parseByte(valor.trim());
            if (valorByte <= 0) {
                JOptionPane.showMessageDialog(null, "Insira um dado válido no campo IDADE!", "Erro", JOptionPane.ERROR_MESSAGE);
                throw new MyCustomException("Erro ao inserir um número negativo ou menor que 0.");
            }
            return valorByte;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Insira um dado válido no campo IDADE!", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new MyCustomException("Ocorreu um erro no campo IDADE.");
        }
    }
    
    public static String obterCpf(String mensagem) throws MyCustomException {
        String valor = mensagem;
        
        if (valor == null) {
            JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException("Programa encerrado pelo usuário.");
        }else if (valor.trim().isEmpty() || valor.length() != 11) {
            JOptionPane.showMessageDialog(null, "Insira a quantidade correta de caracteres no campo CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new MyCustomException("Ocorreu um erro no campo CPF.");
        } else if (!valor.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Letras não são permitidas no campo CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new MyCustomException("Ocorreu um erro no campo CPF.");
        }
        return valor.trim();
    }
    
    public static byte obterDisciplinas(String mensagem, String titulo) {
        boolean valorCorreto = false;
        byte valor = 0;

        while (!valorCorreto) {
            String input = JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException("Programa encerrado pelo usuário.");
            } else if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe um valor numérico correto para a quantidade de disciplinas!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    valor = Byte.parseByte(input.trim());
                    if (valor <= 0) {
                        JOptionPane.showMessageDialog(null, "Informe um valor maior que zero para a quantidade de disciplinas!", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        valorCorreto = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Informe um valor numérico correto para a quantidade de disciplinas!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    return valor;
    }
    
    public static String obterDisciplina(String mensagem, String titulo) {
        boolean valorCorreto = false;
        String valor = null;

        while (!valorCorreto) {
            valor = JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);

            if (valor == null) {
                JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException("Programa encerrado pelo usuário.");
            } else if (valor.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um dado válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (valor.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(null, "Insira um dado válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                valorCorreto = true;
            }
        }
    
    return valor.trim();
    }
    
    public static Double obterNotas(String mensagem, String titulo) {
        boolean valorCorreto = false;
        String valor = null;
        Double valorDouble = null;
        while (!valorCorreto) {
            valor = JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
            
            if (valor == null) {
                JOptionPane.showMessageDialog(null, "Programa encerrado pelo usuário.", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException("Programa encerrado pelo usuário.");
            } else if (valor.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um valor válido para esse campo!", "Erro", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            try {
                valorDouble = Double.parseDouble(valor.trim());
                if (valorDouble < 0) {
                    JOptionPane.showMessageDialog(null, "Insira um valor válido para esse campo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                valorCorreto = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Insira um valor válido para esse campo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        return valorDouble;
    }
    
    public static byte removerDisciplina(String mensagem, String titulo) {
        boolean valorCorreto = false;
        byte valor = 0;

        while (!valorCorreto) {
            String input = JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                return valor;
            } else if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe um valor numérico correto!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    valor = Byte.parseByte(input.trim());
                    if (valor <= 0) {
                        JOptionPane.showMessageDialog(null, "O número inserido não existe!\n Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        valorCorreto = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Informe um valor numérico correto!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    return valor;
    }
}