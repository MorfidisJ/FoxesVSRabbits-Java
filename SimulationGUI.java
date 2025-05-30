import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class CellPanel extends JPanel {
    private static final int PADDING = 2;
    private Color animalColor;
    private boolean isRabbit;
    
    public CellPanel() {
        setPreferredSize(new Dimension(30, 30));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(Color.WHITE);
        animalColor = null;
        isRabbit = false;
    }
    
    public void setAnimal(boolean isRabbit, Color color) {
        this.isRabbit = isRabbit;
        this.animalColor = color;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (animalColor != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(animalColor);
            
            int width = getWidth() - (2 * PADDING);
            int height = getHeight() - (2 * PADDING);
            int x = PADDING;
            int y = PADDING;
            
            if (isRabbit) {
                // Draw rabbit
                // Body
                g2d.fillOval(x + width/4, y + height/3, width/2, height/2);
                // Head
                g2d.fillOval(x + width/2, y + height/6, width/3, height/3);
                // Ears
                g2d.fillOval(x + width/2, y, width/6, height/3);
                g2d.fillOval(x + width*2/3, y, width/6, height/3);
            } else {
                // Draw fox
                // Body
                g2d.fillOval(x + width/4, y + height/3, width/2, height/2);
                // Head
                g2d.fillOval(x + width/2, y + height/6, width/3, height/3);
                // Ears (pointed)
                int[] xPoints = {x + width/2, x + width/2 - width/8, x + width/2 + width/8};
                int[] yPoints = {y, y + height/4, y + height/4};
                g2d.fillPolygon(xPoints, yPoints, 3);
                xPoints[0] = x + width*2/3;
                xPoints[1] = x + width*2/3 - width/8;
                xPoints[2] = x + width*2/3 + width/8;
                g2d.fillPolygon(xPoints, yPoints, 3);
                // Tail
                g2d.fillOval(x, y + height/2, width/3, height/4);
            }
        }
    }
}

public class SimulationGUI extends JFrame {
    private static final int CELL_SIZE = 30;
    private JPanel gridPanel;
    private JLabel foxCountLabel;
    private JLabel rabbitCountLabel;
    private JButton stepButton;
    private JButton autoButton;
    private Timer timer;
    private Simulation simulation;
    private boolean isAutoRunning = false;
    private int gridSize;

    public SimulationGUI(Simulation simulation) {
        this.simulation = simulation;
        this.gridSize = simulation.getGrid().getSize();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Animal Population Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        stepButton = new JButton("Step");
        autoButton = new JButton("Auto Run");
        foxCountLabel = new JLabel("Foxes: 0");
        rabbitCountLabel = new JLabel("Rabbits: 0");

        controlPanel.add(stepButton);
        controlPanel.add(autoButton);
        controlPanel.add(foxCountLabel);
        controlPanel.add(rabbitCountLabel);

        // Create grid panel
        gridPanel = new JPanel(new GridLayout(gridSize, gridSize, 1, 1));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        initializeGrid();

        // Add panels to frame
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        // Setup timer for auto-run
        timer = new Timer(500, e -> {
            simulation.step();
            updateDisplay();
        });

        // Add action listeners
        stepButton.addActionListener(e -> {
            simulation.step();
            updateDisplay();
        });

        autoButton.addActionListener(e -> {
            if (isAutoRunning) {
                timer.stop();
                autoButton.setText("Auto Run");
            } else {
                timer.start();
                autoButton.setText("Stop");
            }
            isAutoRunning = !isAutoRunning;
        });

        // Initial display update
        updateDisplay();
        
        // Pack and show
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGrid() {
        gridPanel.removeAll();
        for (int i = 0; i < gridSize * gridSize; i++) {
            CellPanel cell = new CellPanel();
            gridPanel.add(cell);
        }
    }

    public void updateDisplay() {
        // Update statistics
        foxCountLabel.setText("Foxes: " + simulation.getFoxCount());
        rabbitCountLabel.setText("Rabbits: " + simulation.getRabbitCount());

        // Update grid display
        Grid grid = simulation.getGrid();
        Component[] cells = gridPanel.getComponents();
        
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cell cell = grid.getCell(x, y);
                CellPanel cellPanel = (CellPanel) cells[y * gridSize + x];
                
                if (cell == null || cell.isEmpty()) {
                    cellPanel.setAnimal(false, null);
                } else if (cell.getAnimal().isRabbit()) {
                    cellPanel.setAnimal(true, new Color(64, 64, 64)); // Dark grey
                } else {
                    cellPanel.setAnimal(false, new Color(255, 140, 0)); // Orange
                }
            }
        }
        
        gridPanel.repaint();
    }
} 