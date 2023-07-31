
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PieChart extends JFrame {

    private Map<String, Integer> data;

    public PieChart() {
        data = new HashMap<>();

        // Call a method to get user input for data
        getUserInput();

        setTitle("Data Visualization System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel chartPanel = new ChartPanel();
        add(chartPanel);

        setVisible(true);
    }

    private void getUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of data points:");
        int numDataPoints = scanner.nextInt();
        scanner.nextLine(); 
        // Consume the newline character left by nextInt()

        for (int i = 0; i < numDataPoints; i++) {
            System.out.println("Enter category " + (i + 1) + ":");
            String category = scanner.nextLine();

            System.out.println("Enter value for " + category + ":");
            int value = scanner.nextInt();
            scanner.nextLine(); 
            // Consume the newline character left by nextInt()

            data.put(category, value);
        }

        scanner.close();
    }

    class ChartPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int total = data.values().stream().mapToInt(Integer::intValue).sum();
            int startAngle = 0;

            for (String category : data.keySet()) {
                int value = data.get(category);
                int arcAngle = (int) ((value / (double) total) * 360);
                g.setColor(getRandomColor());
                g.fillArc(100, 100, 200, 200, startAngle, arcAngle);
                double percentage = (value / (double) total) * 100;
                String label = category + " (" + new DecimalFormat("0.00").format(percentage) + "%)";

                double midAngle = Math.toRadians(startAngle + arcAngle / 2.0);
                int xLabel = (int) (200 + 120 * Math.cos(midAngle));
                int yLabel = (int) (200 - 120 * Math.sin(midAngle));

                // Display the value as a label
                g.setColor(Color.BLACK);
                String valueString = String.valueOf(value);
                g.drawString(valueString, xLabel, yLabel);

                startAngle += arcAngle;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        private Color getRandomColor() {
            return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieChart::new);
    }
}
