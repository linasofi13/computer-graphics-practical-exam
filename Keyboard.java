import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private Renderer renderer;
    private double step = 5.0;

    public Keyboard(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toLowerCase(e.getKeyChar());
        
        switch (key) {
            case 'a':
                renderer.P3.x -= step;
                renderer.repaint();
                System.out.println("P3: (" + renderer.P3.x + ", " + renderer.P3.y + ", " + renderer.P3.z + ")");
                break;
                
            case 'd':
                renderer.P3.x += step;
                renderer.repaint();
                System.out.println("P3: (" + renderer.P3.x + ", " + renderer.P3.y + ", " + renderer.P3.z + ")");
                break;
                
            case 'w':
                renderer.P3.y += step;
                renderer.repaint();
                System.out.println("P3: (" + renderer.P3.x + ", " + renderer.P3.y + ", " + renderer.P3.z + ")");
                break;
                
            case 's':
                renderer.P3.y -= step;
                renderer.repaint();
                System.out.println("P3: (" + renderer.P3.x + ", " + renderer.P3.y + ", " + renderer.P3.z + ")");
                break;
                
            case 'e':
                renderer.P3.z -= step;
                renderer.repaint();
                System.out.println("P3: (" + renderer.P3.x + ", " + renderer.P3.y + ", " + renderer.P3.z + ")");
                break;
                
            case 'q':
                renderer.P3.z += step;
                renderer.repaint();
                System.out.println("P3: (" + renderer.P3.x + ", " + renderer.P3.y + ", " + renderer.P3.z + ")");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void setStep(double step) {
        this.step = step;
    }

    public double getStep() {
        return this.step;
    }
}
