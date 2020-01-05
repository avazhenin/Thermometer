package prg.cw2;

/**
 *
 * Name: ANATOLIY Surname: VAZHENIN Student ID: 013301 Module : G52PRG
 *
 */
// imported libraries
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// main Thermometer class 
public class Thermometer {

    public static void main(String[] args) throws Exception {
        if (args.length == 1) { // array length must be 1, because only one parameter should be passed
            if (isNumeric(args[0])) { // check whether parameter is integer value
                if ((Double.valueOf(args[0])) >= -100 && (Double.valueOf(args[0])) <= 200) {
                    new RunThermometer(Double.valueOf(args[0])).setVisible(true); // show Window
                } else {
                    System.out.println("Value you have entered is too high to display, try between -100 and 200");
                }

            } else {
                System.out.println("You passed not integer value"); // error trapping for wrong value type
            }
        } else {
            System.out.println("You passed wrong number of parameters"); // error trapping for wrong number of parameters
        }
    }// end of main void 

    // check if the number is numeric
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            // if exception - then number is not numeric , return false in this case 
            return false;
        }
        return true;
    }
} // end of main Thermometer class

final class RunThermometer extends JFrame {

    // declaring variables
    int scaleInterval = 7; // scale interval
    int thermWidth = 10, thermHeight = 60 * scaleInterval, scaleHeight; // size of the scale, considering that 2° equal width=7
    int max = 100, min = -20, scaleType = 0; // 0 : Centigrade / 1 : Fahrenheit / 2 : Kelvin   
    private final String Cel = "\u00b0C", Fah = "\u00b0F", Kel = "\u00b0K";
    String currentTemperatureSign = Cel; // uses in print() procedure
    double C; // C holds temperature value
    private int buttonWidth = 94, buttonHeight = 30, frameWidth = 220, frameHeight = 500; // min - max range, to show HOT / COLD message
    public JButton Centigrade, Fahrenheit, Kelvin; // buttons list for each degree

    public RunThermometer(double degree) {
        this.C = degree; // set degree from user input
        scaleHeight = (int) (((C + 20) * scaleInterval) / 2); // calculate height for drawing scale, considering that 2° equal width=7
        initComponents(); // build Frame
    }

    void initComponents() {
        setLayout(null); // set default layout        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close window when close button is pressed
        setSize(frameWidth, frameHeight); // set window size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width / 2) - (frameWidth / 2), (screenSize.height - frameHeight) / 2);
        setResizable(false);
        setTitle("Temp: " + String.valueOf(getDegree(C)) + currentTemperatureSign); // set window title with temperature
        this.getContentPane().setBackground(Color.white); // set background color to back

        // Initiate buttons
        Centigrade = new JButton("Centigrade");
        Fahrenheit = new JButton("Fahrenheit");
        Kelvin = new JButton("Kelvin");

        // set buttons layout
        Centigrade.setBounds(0, 100, buttonWidth, buttonHeight);
        Fahrenheit.setBounds(0, 125, buttonWidth, buttonHeight);
        Kelvin.setBounds(0, 150, buttonWidth, buttonHeight);

        // Initiate action listeners for particular button
        Centigrade.addActionListener(new ButtonListener());
        Fahrenheit.addActionListener(new ButtonListener());
        Kelvin.addActionListener(new ButtonListener());
        //disable Centigrade button by default, because initially everything displayes in Centigrade
        Centigrade.setEnabled(false);

        // add buttons to the panel
        add(Centigrade);
        add(Fahrenheit);
        add(Kelvin);
    }// end of initComponents procedure

    // get temp related to scale type
    double getDegree(double degree) {
        switch (scaleType) {
            case 0:
                return (double) Math.round(degree * 100) / 100; // Centigrade
            case 1:
                return (double) Math.round((((degree * 9) / 5) + 32) * 100) / 100; // Fahrenheit
            case 2:
                return (double) Math.round((degree + 273.15) * 100) / 100; // Kelvin
            }
        return 0;
    }

    // inner class for listening button actions
    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Centigrade
            if (e.getSource() == Centigrade) {
                scaleType = 0; // scale type 0 is for Centigrade
                currentTemperatureSign = Cel; // set current sign for displaying
                setTitle("Temp: " + String.valueOf(getDegree(C)) + Cel);// set title pemperature letter as C
                Centigrade.setEnabled(false);// Disable Centigrade button
                Fahrenheit.setEnabled(true);
                Kelvin.setEnabled(true);
                max = 100; // set max for Celsuim
                min = -20;// set min for Celsuim
                scaleInterval = 7;// set scale jumps interval's width for Celsuim
                thermHeight = 60 * scaleInterval; // calculate therm height according to scale interval jump width
                scaleHeight = (int) (((C + 20) * scaleInterval) / 2);// calculate scaleHeight for Celsium
            }
            // Fahrenheit
            if (e.getSource() == Fahrenheit) {
                scaleType = 1; // scale type 0 is for Fahrenheit
                currentTemperatureSign = Fah; // set current sign for displaying
                setTitle("Temp: " + String.valueOf(getDegree(C)) + Fah); // set title pemperature letter as F                
                Centigrade.setEnabled(true);
                Fahrenheit.setEnabled(false);// Disable Fahrenheit button
                Kelvin.setEnabled(true);
                max = 215; // set max for Fahrenheit
                min = -5; // set min for Fahrenheit
                scaleInterval = 4; // set scale jumps interval's width for Fahrenheit
                thermHeight = 110 * scaleInterval; // calculate therm height according to scale interval jump width
                scaleHeight = (int) ((getDegree(C) + 5) * 2); // calculate scaleHeight for Fahrenheit
            }
            // Kelvin
            if (e.getSource() == Kelvin) {
                scaleType = 2; // scale type 0 is for Kelvin
                currentTemperatureSign = Kel; // set current sign for displaying
                setTitle("Temp: " + String.valueOf(getDegree(C)) + Kel);// set title pemperature letter as K
                Centigrade.setEnabled(true);
                Fahrenheit.setEnabled(true);
                Kelvin.setEnabled(false); // Disable Kelvin button
                max = 380;// set max for Kelvin
                min = 250;// set min for Kelvin
                scaleInterval = 6; // set scale jumps interval's width for Kelvin
                thermHeight = 70 * scaleInterval;  // calculate therm height according to scale interval jump width
                scaleHeight = (int) (((C + 23) * scaleInterval) / 2);// calculate scaleHeight for Kelvin
            }
            repaint(); // redraw panel
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // draw general stuff        
        // declare variables
        int panelHeight = getSize().height + 25, panelWidth = getSize().width, IntervalCounter = 0, degreeLabel = min, LabelCounter = 1, intervalLineWidth = 15;

        // set font to bold
        g.setFont(new Font("default", Font.BOLD, 16));
        // draw a degree char
        g.drawString(
                currentTemperatureSign,
                ((panelWidth - thermWidth) / 2) - 40,
                ((panelHeight - thermHeight) / 2) + 5);
        // draw temperature value with degree char
        g.drawString(
                String.valueOf(getDegree(C)) + currentTemperatureSign,
                ((panelWidth - thermWidth) / 2) - 90,
                90);

        // set bold font for printing labels
        g.setFont(new Font("default", Font.BOLD, 14));
        // hote and cold behavior        
        //HOT
        if (getDegree(C) > max) {
            g.setColor(Color.RED);
            g.drawString("Too Hot!", ((panelWidth - thermWidth) / 2) - 90, 110);
            scaleHeight = 0;
            g.setColor(Color.BLACK);
        }
        //COLD
        if (getDegree(C) < min) {
            g.setColor(Color.BLUE);
            g.drawString("Too Cold!", ((panelWidth - thermWidth) / 2) - 90, 110);
            scaleHeight = 0;
            g.setColor(Color.BLACK);
        }
        // draw a bulb for thermometer
        g.drawRect((panelWidth - thermWidth) / 2, (panelHeight - thermHeight) / 2, thermWidth, thermHeight);
        // setting color to red for the mercury
        g.setColor(Color.RED);
        // draw the mercury
        g.fillRect(
                ((panelWidth - thermWidth) / 2) + 1,
                (panelHeight - ((panelHeight - thermHeight) / 2)) - (int) scaleHeight,
                thermWidth - 1,
                (int) scaleHeight);

        // Set color back, to draw degrees
        g.setColor(Color.BLACK);

        // calculate loop start point according to thermHeight and scaleInterval variable data
        int loopStart = thermHeight / scaleInterval;
        for (int i = loopStart; i >= 0; i--) { // loop start
            // draw bold lines at each 20° intervals
            if (i == loopStart || LabelCounter == 10) {
                // draw line two times to make it bold
                g.drawLine(//1st
                        (panelWidth / 2) - (intervalLineWidth + 5) / 2,
                        ((panelHeight / 2) + (thermHeight / 2)) - IntervalCounter,
                        ((panelWidth / 2) + (intervalLineWidth + 5) / 2),
                        ((panelHeight / 2) + (thermHeight / 2)) - IntervalCounter);
                g.drawLine(//2nd
                        (panelWidth / 2) - (intervalLineWidth + 5) / 2,
                        ((panelHeight / 2) + (thermHeight / 2)) - IntervalCounter + 1,
                        ((panelWidth / 2) + (intervalLineWidth + 5) / 2),
                        ((panelHeight / 2) + (thermHeight / 2)) - IntervalCounter + 1);
                // each 20° draw degree value 
                g.drawString(
                        String.valueOf(degreeLabel),
                        ((panelWidth / 2) - intervalLineWidth / 2) + 30,
                        ((panelHeight / 2) + (thermHeight / 2) + 5) - IntervalCounter);

                LabelCounter = 1; // set counter to 1, to count from the begining
                degreeLabel = degreeLabel + 20; // decrement max variable by 20, each 20°interval
            } else {
                LabelCounter++; // increasing counter until reach next 20° interval
            } // end of else clause
            // draw interval
            g.drawLine(
                    (panelWidth / 2) - intervalLineWidth / 2,
                    ((panelHeight / 2) + (thermHeight / 2)) - IntervalCounter,
                    ((panelWidth / 2) + intervalLineWidth / 2),
                    ((panelHeight / 2) + (thermHeight / 2)) - IntervalCounter);
            IntervalCounter += scaleInterval; // increase the scale interval            
        } // loop end
    }// paint method end
} // end of RunThermometer class
