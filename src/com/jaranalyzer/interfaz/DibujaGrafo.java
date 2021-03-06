package com.jaranalyzer.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.jaranalyzer.adapter.Adapter;
import com.jaranalyzer.dependencias.Dependencia;
import com.jaranalyzer.dependencias.DependenciaInterna;
import com.jaranalyzer.dependencias.DependenciasClases;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;
import javafx.application.Application;

/**
 * Clase encargada de dibujar los grafos
 * @author andrey
 *
 */
@SuppressWarnings("serial")
public class DibujaGrafo extends JFrame {

	//Atributos
	private String jarActual;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem zoomIn, zoomOut;
	private String vertex = "";
	private Graph grafo;
	Layout<Integer, String> layout;
	//
	
	/**
	 * Constructor
	 * @param jarActual el jar del cual se quiere dibujar el/los grafos
	 */
	public DibujaGrafo(String jarActual) {
		this.jarActual = jarActual;
	}
	
	/**
	 * Dibuja el grafo
	 * @param dependencia
	 */
	public void DibujarGrafo(Dependencia dependencia) {

		this.grafo = Adapter.grafoJung(dependencia);
		
		layout = new CircleLayout(grafo);
		layout.setSize(new Dimension(300, 300));
		
		menuBar = new JMenuBar();
		zoomOut = new JMenuItem("Out");
		zoomIn = new JMenuItem("In");
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(vertex.equals(jarActual)) {
						DependenciasClases dc = new DependenciasClases(dependencia.getJar().getName());
						DibujaGrafo nuevo = new DibujaGrafo(jarActual);
						nuevo.DibujarGrafo(Adapter.adapterDC(dc));
						InterfazGrafica.muestraGrado(vertex, dc.getGrafoClases());
						InterfazGrafica.muestraListas(dc.getGrafoClases());
					}
					else {
						DependenciaInterna di = new DependenciaInterna(dependencia.getJar(),
								dependencia.obtenerDependenciaInterna(vertex));
						if (di.getDependenciasInternas() != null) {
							DibujarGrafo(Adapter.adapterDI(di));
							InterfazGrafica.muestraGrado(vertex, di.getDependenciasInternas());
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}			
		});
		
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}});
		menu = new JMenu("Zoom");
		menu.add(zoomIn);
		menu.addSeparator();
		menu.add(zoomOut);
		menuBar.add(menu);
		
		
		this.setBounds(300, 100, 600, 500);
		this.setTitle("Grafo");

		VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
		vv.setPreferredSize(new Dimension(350, 350));

		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		vv.addKeyListener(gm.getModeKeyListener());

		vv.setGraphMouse(gm);

		final PickedState<Integer> pickedState = vv.getPickedVertexState();
		pickedState.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object subject = e.getItem();
				vertex = (String) subject;
			}
		});

		this.setLayout(new BorderLayout());
		this.add(menuBar, BorderLayout.NORTH);
		this.getContentPane().add(vv);
		this.pack();
		this.setVisible(true);

	}
	
}
