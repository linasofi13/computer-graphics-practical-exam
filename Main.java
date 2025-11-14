import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Parcial Practico Final - Clipping 3D");
        Renderer renderer = new Renderer();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 800);
        window.setLocationRelativeTo(null);
        window.add(renderer);
        window.setVisible(true);
    }
}
