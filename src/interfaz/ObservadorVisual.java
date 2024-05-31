package interfaz;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import negocio.Observador;
import negocio.Poblacion;

public class ObservadorVisual implements Observador 
{
	private JFrame frame;
	private static Poblacion _poblacion;	
	private static int iteracion=1;
	private XYSeriesCollection dataSet;
	private JFreeChart chart;

	/**
	 * Create the application.
	 */
	public ObservadorVisual(Poblacion poblacion) 
	{	
		_poblacion = poblacion;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setTitle("Algoritmo Gen�tico. La mochila.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		elGrafico();
	}
	
	private void elGrafico() 
	{
		crearSerie();
		// Creamos un JFreeChart y un ChartPanel que lo contiene, y agregamos el
		// ChartPanel al frame
		// vamos a usar un gr�fico XY y le pedimos a ChartFactory el tipo de gr�fico
		chart = ChartFactory.createXYLineChart("Poblaci�n", "Iteraci�n",
				"Fitness", dataSet, PlotOrientation.VERTICAL, true, false,
				false);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(10, 10, 570, 380);
		frame.getContentPane().add(chartPanel);			
	}

	private void crearSerie()
	{
		dataSet = new XYSeriesCollection();
		dataSet.addSeries(new XYSeries("Promedio"));
		dataSet.addSeries(new XYSeries("Mejor"));
		dataSet.addSeries(new XYSeries("Peor"));
	}

	@Override
	public void notificar() 
	{
		dataSet.getSeries(0).add(iteracion, _poblacion.fitnessPromedio());
		dataSet.getSeries(1).add(iteracion, _poblacion.mejorIndividuo().fitness());
		dataSet.getSeries(2).add(iteracion, _poblacion.peorIndividuo().fitness());	
		iteracion++;			
	}
}

