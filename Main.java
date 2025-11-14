import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Parcial Practico Final - Clipping 3D");
        Renderer renderer = new Renderer();
        
        Keyboard keyboard = new Keyboard(renderer);
        window.addKeyListener(keyboard);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 800);
        window.setLocationRelativeTo(null);
        window.add(renderer);
        window.setVisible(true);
        
        // Mostrar instrucciones de uso en consola
        System.out.println("=== CONTROLES ===");
        System.out.println("A/D: Mover P3 en X (izquierda/derecha)");
        System.out.println("W/S: Mover P3 en Y (arriba/abajo)");
        System.out.println("Q/E: Mover P3 en Z (alejar/acercar)");
        System.out.println("================");
    }
}
