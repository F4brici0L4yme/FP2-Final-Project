import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tablero extends JFrame {
    private JLabel lblVidasP1;
    private JLabel lblVidasP2;

    public Tablero() {
        setTitle("Shotgun party");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel panelVidas = new JPanel(new GridLayout(1, 2));
        lblVidasP1 = new JLabel("Jugador 1: 4 vidas");
        lblVidasP2 = new JLabel("Jugador 2: 4 vidas");
        lblVidasP1.setHorizontalAlignment(SwingConstants.CENTER);
        lblVidasP2.setHorizontalAlignment(SwingConstants.CENTER);
        panelVidas.add(lblVidasP1);
        panelVidas.add(lblVidasP2);
        add(panelVidas, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(3, 3, 10, 10));
        panelCentral.setBackground(Color.DARK_GRAY);

        for (int i = 0; i < 9; i++) {
            if (i == 4) {
                JButton btnEscopeta = new JButton();
                btnEscopeta.setIcon(new ImageIcon("./images/shotgun.png")); // Ruta de la imagen
                btnEscopeta.setBorderPainted(false);
                btnEscopeta.setContentAreaFilled(false);
                btnEscopeta.setFocusPainted(false);

                btnEscopeta.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mostrarOpcionesDisparo();
                    }
                });

                panelCentral.add(btnEscopeta);
            } else {
                panelCentral.add(new JLabel());
            }
        }

        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    private void mostrarOpcionesDisparo() {
        String[] opciones = {"Dispararme a mí mismo", "Disparar al jugador 2"};
        int seleccion = JOptionPane.showOptionDialog(
                this,
                "¿A quién deseas disparar?",
                "Seleccionar objetivo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == 0) {
            // Acción: Dispararse a sí mismo
            JOptionPane.showMessageDialog(this, "Te disparaste a ti mismo.", "Disparo", JOptionPane.INFORMATION_MESSAGE);
        } else if (seleccion == 1) {
            // Acción: Disparar al jugador 2
            JOptionPane.showMessageDialog(this, "Disparaste al jugador 2.", "Disparo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Tablero();
    }
}
