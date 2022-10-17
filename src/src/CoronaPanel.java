import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The type Corona panel.
 */
@SuppressWarnings("serial")
public class CoronaPanel extends JPanel {
    private final int DELAY = 10, PEOPLE = 300, RECORD_RATE = 2, IMMOBILE = 200, CAPACITY = 50, LAYOUT_WIDTH = 200;
    private int graphScale = 2, numPeople = PEOPLE, recordRate = RECORD_RATE, immobile = IMMOBILE, capacity = CAPACITY;
    /**
     * The Width.
     */
    static final int WIDTH = 600, /**
     * The Height.
     */
    HEIGHT = 800;
    /**
     * The Timer.
     */
    Timer timer;
    private int tick, timerTriggers;
    private boolean paused = false;

    /**
     * The People.
     */
    Person[] people, /**
     * The People distanced.
     */
    peopleDistanced;
    /**
     * The Graph.
     */
    int[][] graph, /**
     * The Graph distanced.
     */
    graphDistanced;

    private JPanel controls;
    private JSlider scaleSlider, timerSlider, peopleSlider, recordSlider, immobileSlider, capacitySlider;
    private JLabel controlLabel, scaleLabel, timerLabel, peopleLabel, recordLabel, immobileLabel, capacityLabel;
    private JButton pauseButton, restartButton, playButton;

    /**
     * Instantiates a new Corona panel.
     */
    public CoronaPanel() {
        timerTriggers = 0;
        tick = 0;
        people = new Person[PEOPLE];
        peopleDistanced = new Person[PEOPLE];
        graph = new int[11][WIDTH];
        graphDistanced = new int[11][WIDTH];
        int healthyCounter = 0, vaccinatedCounter = 0, infectedCounter = 0;

        int infected = (int) (Math.random() * PEOPLE);
        for (int i = 0; i < people.length; i++) {
            people[i] = new Person((int) (Math.random() * (WIDTH - 10)), (int) (Math.random() * (HEIGHT - 10)),
                    i == infected, false, false);
            switch (people[i].getStatus()) {
                case 0:
                    healthyCounter++;
                    break;
                case 1:
                    infectedCounter++;
                    break;
                case 2:
                    infectedCounter++;
                    break;
                case 3:
                    healthyCounter++;
                    break;
                case 4:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
                case 5:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
                case 6:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
                case 7:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
                case 8:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
                case 9:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
                case 10:
                    vaccinatedCounter++;
                    healthyCounter++;
                    break;
            }

        }

        infected = (int) (Math.random() * (PEOPLE - IMMOBILE)) + IMMOBILE;
        for (int i = 0; i < people.length; i++) {
            if (i < IMMOBILE) {
                peopleDistanced[i] = new Person((int) (Math.random() * (WIDTH - 10)) + WIDTH,
                        (int) (Math.random() * (HEIGHT - 10)), false, true, true);
            } else {
                peopleDistanced[i] = new Person((int) (Math.random() * (WIDTH - 10)) + WIDTH,
                        (int) (Math.random() * (HEIGHT - 10)), i == infected, false, true);
            }
        }
        SliderListener sliderListener = new SliderListener();

        controls = new JPanel();
        BoxLayout sliderLayout = new BoxLayout(controls, BoxLayout.Y_AXIS);
        controls.setLayout(sliderLayout);


        scaleLabel = new JLabel("Graph Scaling: 2");
        scaleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        timerSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, DELAY);
        timerSlider.setMajorTickSpacing(10);
        timerSlider.setMinorTickSpacing(5);
        timerSlider.setPaintTicks(true);
        timerSlider.setPaintLabels(true);
        timerSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        timerSlider.addChangeListener(sliderListener);

        timerLabel = new JLabel("Tickrate: " + DELAY);
        timerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        peopleSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, PEOPLE);
        peopleSlider.setMajorTickSpacing(250);
        peopleSlider.setMinorTickSpacing(50);
        peopleSlider.setPaintTicks(true);
        peopleSlider.setPaintLabels(true);
        peopleSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        peopleSlider.addChangeListener(sliderListener);
        peopleSlider.setEnabled(false);

        peopleLabel = new JLabel("People: " + PEOPLE);
        peopleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        recordSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, RECORD_RATE);
        recordSlider.setMajorTickSpacing(1);
        recordSlider.setMinorTickSpacing(1);
        recordSlider.setPaintTicks(true);
        recordSlider.setPaintLabels(true);
        recordSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        recordSlider.addChangeListener(sliderListener);
        recordSlider.setEnabled(false);

        recordLabel = new JLabel("Record rate: " + RECORD_RATE);
        recordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        immobileSlider = new JSlider(JSlider.HORIZONTAL, 0, PEOPLE, IMMOBILE);
        immobileSlider.setMajorTickSpacing(250);
        immobileSlider.setMinorTickSpacing(50);
        immobileSlider.setPaintTicks(true);
        immobileSlider.setPaintLabels(true);
        immobileSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        immobileSlider.addChangeListener(sliderListener);
        immobileSlider.setEnabled(false);

        immobileLabel = new JLabel("Immobile people: " + IMMOBILE);
        immobileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        controlLabel = new JLabel("Controls");
        controlLabel.setFont(new Font("Consolas", Font.BOLD, 40));

        pauseButton = new JButton("Pause");
        pauseButton.setContentAreaFilled(false);
        pauseButton.setOpaque(true);
        pauseButton.addActionListener(new ControlListener());

        playButton = new JButton("Play");
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(true);
        playButton.addActionListener(new ControlListener());

        restartButton = new JButton("Restart");
        restartButton.setContentAreaFilled(false);
        restartButton.setOpaque(true);
        restartButton.addActionListener(new ControlListener());

        controls.add(controlLabel);
        controls.add(pauseButton);
        controls.add(playButton);
        controls.add(restartButton);
        controls.add(Box.createRigidArea(new Dimension(0, 30)));
        /*controls.add(scaleLabel);
        controls.add(scaleSlider);*/
        controls.add(Box.createRigidArea(new Dimension(0, 30)));
        controls.add(timerLabel);
        controls.add(timerSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 30)));
        controls.add(peopleLabel);
        controls.add(peopleSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 30)));
        controls.add(recordLabel);
        controls.add(recordSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 30)));
        controls.add(immobileLabel);
        controls.add(immobileSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 30)));

        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        add(Box.createRigidArea(new Dimension(WIDTH, 0)));

        add(controls);

        timer = new Timer(DELAY, new CoronaListener());
        setPreferredSize(new Dimension(WIDTH + LAYOUT_WIDTH, HEIGHT + (numPeople / graphScale)));

        setBackground(Color.white);
        timer.start();
    }

    public void paintComponent(Graphics page) {
        super.paintComponent(page);

        // Draw people (normal)
        for (int i = 0; i < people.length; i++) {
            switch (people[i].getStatus()) {
                case 0:
                    page.setColor(Color.green);
                    break;
                case 1:
                    page.setColor(Color.red);
                    break;
                case 2:
                    page.setColor(Color.pink);
                    break;
                case 3:
                    page.setColor(Color.blue);
                    break;
                case 4:
                    page.setColor(Color.orange);
                    break;
                case 5:
                    page.setColor(Color.yellow);
                    break;
                case 6:
                    page.setColor(Color.cyan);
                    break;
                case 7:
                    page.setColor(Color.magenta);
                    break;
                case 8:
                    page.setColor(Color.darkGray);
                    break;
                case 9:
                    page.setColor(Color.lightGray);
                    break;
                case 10:
                    page.setColor(Color.gray);
                    break;
            }
            if (timerTriggers % recordRate == 0 && people[i].getStatus() != 4) {
                graph[people[i].getStatus()][tick]++;
            }
            page.fillOval((int) people[i].getX(), (int) people[i].getY(), 10, 10);
            for (int j = 0; j < people.length; j++) {
                if (i != j) {
                    people[i].check(people[j]);
                }
            }
        }

        // Draw graph (normal)
        for (int i = 0; i < tick; i++) {
            int temp = HEIGHT + (numPeople / graphScale);

            page.setColor(Color.pink);
            page.drawLine(i, temp - ((int) Math.ceil(graph[2][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[2][i] / graphScale);

            page.setColor(Color.red);
            page.drawLine(i, temp - ((int) Math.ceil(graph[1][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[1][i] / graphScale);

            page.setColor(Color.green);
            page.drawLine(i, temp - ((int) Math.ceil(graph[0][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[0][i] / graphScale);

            page.setColor(Color.blue);
            page.drawLine(i, temp - ((int) Math.ceil(graph[3][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[3][i] / graphScale);

            page.setColor(Color.orange);
            page.drawLine(i, temp - ((int) Math.ceil(graph[4][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[4][i] / graphScale);

            page.setColor(Color.yellow);
            page.drawLine(i, temp - ((int) Math.ceil(graph[5][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[5][i] / graphScale);

            page.setColor(Color.cyan);
            page.drawLine(i, temp - ((int) Math.ceil(graph[6][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[6][i] / graphScale);

            page.setColor(Color.magenta);
            page.drawLine(i, temp - ((int) Math.ceil(graph[7][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[7][i] / graphScale);

            page.setColor(Color.darkGray);
            page.drawLine(i, temp - ((int) Math.ceil(graph[8][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[8][i] / graphScale);

            page.setColor(Color.lightGray);
            page.drawLine(i, temp - ((int) Math.ceil(graph[9][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[9][i] / graphScale);

            page.setColor(Color.gray);
            page.drawLine(i, temp - ((int) Math.ceil(graph[10][i] / graphScale)), i, temp);
            temp -= Math.ceil(graph[10][i] / graphScale);
        }

    }

    /**
     * Reinitialize.
     */
    public void reinitialize() {
        timerTriggers = 0;
        tick = 0;
        people = new Person[numPeople];
        peopleDistanced = new Person[numPeople];
        graph = new int[11][WIDTH];
        graphDistanced = new int[11][WIDTH];

        int infected = (int) (Math.random() * numPeople);
        for (int i = 0; i < people.length; i++) {
            people[i] = new Person((int) (Math.random() * (WIDTH - 10)), (int) (Math.random() * (HEIGHT - 10)),
                    i == infected, false, false);
        }

        infected = (int) (Math.random() * (numPeople - immobile)) + immobile;
        for (int i = 0; i < people.length; i++) {
            if (i < immobile) {
                peopleDistanced[i] = new Person((int) (Math.random() * (WIDTH - 10)) + WIDTH,
                        (int) (Math.random() * (HEIGHT - 10)), false, true, true);
            } else {
                peopleDistanced[i] = new Person((int) (Math.random() * (WIDTH - 10)) + WIDTH,
                        (int) (Math.random() * (HEIGHT - 10)), i == infected, false, true);
            }
        }

    }

    private class SliderListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == scaleSlider || e.getSource() == peopleSlider || e.getSource() == recordSlider
                    || e.getSource() == immobileSlider || e.getSource() == capacitySlider) {
                playButton.setEnabled(false);
                pauseButton.setEnabled(false);
            }
            if (e.getSource() == peopleSlider) {
                immobileSlider.setValue(numPeople / 2);
                immobileSlider.setMaximum(numPeople);

                capacitySlider.setValue(numPeople / 2);
                capacitySlider.setMaximum(numPeople);
            }


            timer.setDelay(timerSlider.getValue());
            timerLabel.setText("Tickrate: " + timerSlider.getValue());

            numPeople = peopleSlider.getValue();
            peopleLabel.setText("People: " + numPeople);

            recordRate = recordSlider.getValue();
            recordLabel.setText("Record rate: " + recordRate);

            immobile = immobileSlider.getValue();
            immobileLabel.setText("Immobile people: " + immobile);


        }
    }

    private class ControlListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == playButton) {
                timer.restart();
                paused = false;
            } else if (e.getSource() == pauseButton) {
                timer.stop();
                paused = true;
            } else if (e.getSource() == restartButton) {
                reinitialize();
                paused = false;
                playButton.setEnabled(true);
                pauseButton.setEnabled(true);
                restartButton.setEnabled(true);
                timerSlider.setEnabled(true);
                peopleSlider.setEnabled(false);
                recordSlider.setEnabled(false);
                immobileSlider.setEnabled(false);
                timer.restart();
            }

            peopleSlider.setEnabled(paused);
            recordSlider.setEnabled(paused);
            immobileSlider.setEnabled(paused);

        }

    }

    private class CoronaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < people.length; i++) {
                people[i].tick();
                if (people[i].isInfected() && i < PEOPLE * 0.2 && graph[2][i] < capacity) {
                    people[i].hospitalize();
                }
                peopleDistanced[i].tick();
                if (peopleDistanced[i].isInfected() && i < PEOPLE * 0.2 && graphDistanced[2][i] < capacity) {
                    peopleDistanced[i].hospitalize();
                }
            }
            repaint();
            timerTriggers++;
            if (timerTriggers % recordRate == 0) {
                tick++;
            }
            if (tick > 599) {
                tick = 0;
                graph = new int[11][WIDTH];
                graphDistanced = new int[11][WIDTH];
            }
        }
    }
}