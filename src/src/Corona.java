import java.awt.*;
import javax.swing.*;

/**
 * The type Corona.
 */
public class Corona {
    /**
     * The Frame.
     */
    static JFrame frame = new JFrame("Coronavirus Simulation");

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new CoronaPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(CoronaPanel.WIDTH, CoronaPanel.HEIGHT));
        frame.setResizable(false);
    }
}