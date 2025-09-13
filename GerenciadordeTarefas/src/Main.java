import view.TelaTarefas;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal que inicia o aplicativo de gerenciamento de tarefas
 */
public class Main {

    public static void main(String[] args) {
        // Configura o look and feel para parecer com o sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Erro ao configurar Look and Feel: " + e.getMessage());
        }
        
        // Inicia a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            TelaTarefas telaTarefas = new TelaTarefas();
            telaTarefas.setVisible(true);
        });
    }
}