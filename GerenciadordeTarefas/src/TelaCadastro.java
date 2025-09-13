import javax.swing.JOptionPane;

public class TelaCadastro {
    public void Tela() {
        String nome = JOptionPane.showInputDialog(null, "Digite seu nome: ");

        int idade = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite sua idade: "));

        double salario = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite seu salario: "));

        char sexo = JOptionPane.showInputDialog(null, "Digite seu sexo: ").toUpperCase().charAt(0);

        int reposta = JOptionPane.showInputDialog(null, "Você está empregado?  ").toUpperCase().charAt(0);
        boolean empregado = (reposta == JOptionPane.YES_OPTION);

        String mensagem = "=======DADOS CADASTRADOS=======\n" +
            "Nome: " + nome + "\n" +
            "idade: " + idade + "\n" +
            "Salario"  + salario + "\n" +
            "Sexo"  + sexo + "\n" +
            "Está empregado?"  + (empregado? "sim" : "não");

        JOptionPane.showMessageDialog(null, mensagem);


    }
}
