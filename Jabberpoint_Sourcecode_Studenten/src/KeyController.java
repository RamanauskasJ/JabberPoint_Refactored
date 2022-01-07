import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <p>This is the KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter {
    private Presentation presentation; //Commands are given to the presentation

    public KeyController(Presentation p) {
        presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                presentation.nextSlide();
                break;
            case KeyEvent.VK_LEFT:
                presentation.prevSlide();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break; //Should not be reached
            default:
                break;
        }
    }
}
