import javafx.scene.image.Image;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.io.File;

public class ImageCreate extends JFrame {
    public String [] arrText;
    public Integer [] arrWheel;
    //File imageChart;


    public File imageChart;

    public File getImage() {
        return imageChart;
    }

    public ImageCreate(String [] arrText,Integer [] arrWheel){
        this.arrText = arrText;
        this.arrWheel = arrWheel;
    }

    JFreeChart chart;

    public void createChartPanel(){
        createPieChart();
        ChartPanel chartPanel =new ChartPanel(chart);
        add(chartPanel);
        pack();
        setTitle("Колесо");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void createPieChart(){

        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
        defaultPieDataset.setValue(arrText[0],arrWheel[0]);
        defaultPieDataset.setValue(arrText[1],arrWheel[1]);
        defaultPieDataset.setValue(arrText[2],arrWheel[2]);
        defaultPieDataset.setValue(arrText[3],arrWheel[3]);
        defaultPieDataset.setValue(arrText[4],arrWheel[4]);
        defaultPieDataset.setValue(arrText[5],arrWheel[5]);
        defaultPieDataset.setValue(arrText[6],arrWheel[6]);
        defaultPieDataset.setValue(arrText[7],arrWheel[7]);
        chart = ChartFactory.createPieChart("Круговая диаграма",defaultPieDataset,true,true,false);


        File imageChart = new File( "src/main/java/WheelLifeBalance");


        try {
            ChartUtilities.saveChartAsPNG(imageChart,chart,500,300);
        }catch (Exception e){
            System.out.println("Problem");
        }

    }
}
