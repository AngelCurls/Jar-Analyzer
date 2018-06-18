package com.jaranalyzer.interfaz;

import java.io.File;

import javax.swing.JFrame;

import com.jaranalyzer.adapter.Adapter;
import com.jaranalyzer.dependencias.Dependencia;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.tools.jar.resources.jar;

public class InterfazGrafica extends Application {
	
    	private Dependencia jarGrafo;
    	private Pane layout = new Pane();
    	private Button btnagregarJAR = new Button("A�adir JAR");
    	private Button btnGrafo = new Button("Grafo");
    	private ListView lstRankingDep = new ListView();
    	private ListView lstRankingRef = new ListView();
    	private Label lblRankDep = new Label("Ranking de Dependencias");
    	private Label lblRankRef = new Label("Ranking de Referencias");
    	private Label jarActual = new Label();
    	private Label lblJar = new Label();
    	private Label lblGradoSaliente = new Label();
    	private Label lblGradoEntrante = new Label();
    	private Label lblGS = new Label();
    	private Label lblGE = new Label();
    	private Label lblEsConexo = new Label();
    	private Label lblConexo = new Label();

    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
        primaryStage.setTitle("JarAnalyzer");

        btnagregarJAR.setLayoutX(350);
        btnagregarJAR.setLayoutY(300);
        
        btnGrafo.setLayoutX(365);
        btnGrafo.setLayoutY(270);
                      
        //Listas y Labels
                        
        lblRankDep.setLayoutX(27);
        lblRankDep.setLayoutY(7);
        lblRankDep.setFont(Font.font("Centurie Gothic", 14));
        lblRankDep.setTextFill(Color.CHOCOLATE);
        lstRankingDep.setLayoutX(10);
        lstRankingDep.setLayoutY(25);
        lstRankingDep.setMaxWidth(200);
        lstRankingDep.setMaxHeight(200);
        
        lblRankRef.setLayoutX(240);
        lblRankRef.setLayoutY(7);
        lblRankRef.setFont(Font.font("Centurie Gothic", 14));
        lblRankRef.setTextFill(Color.CHOCOLATE);
        lstRankingRef.setLayoutX(220);
        lstRankingRef.setLayoutY(25);
        lstRankingRef.setMaxWidth(200);
        lstRankingRef.setMaxHeight(200);
        
        jarActual.setLayoutX(110);
        jarActual.setLayoutY(338);
    	jarActual.setText("(Ninguno)");
        jarActual.setFont(Font.font("Centurie Gothic", 12));

        lblJar.setLayoutX(15);
        lblJar.setLayoutY(338);
        lblJar.setText("Jar Seleccionado:");
        lblJar.setFont(Font.font("Centurie Gothic", 12));
        
        lblGradoSaliente.setLayoutX(15);
        lblGradoSaliente.setLayoutY(250);
        lblGradoSaliente.setText("Grado Saliente: ");
        lblGradoSaliente.setFont(Font.font("Centurie Gothic", 12));
        
        lblGS.setLayoutX(110);
        lblGS.setLayoutY(250);
        lblGS.setText("(Vac�o)");
        lblGS.setFont(Font.font("Centurie Gothic", 12));
        
        lblGE.setLayoutX(110);
        lblGE.setLayoutY(270);
        lblGE.setText("(Vac�o)");
        lblGE.setFont(Font.font("Centurie Gothic", 12));
        
        lblGradoEntrante.setLayoutX(15);
        lblGradoEntrante.setLayoutY(270);
        lblGradoEntrante.setText("Grado Entrante: ");
        lblGradoEntrante.setFont(Font.font("Centurie Gothic", 12));
        
        lblEsConexo.setLayoutX(15);
        lblEsConexo.setLayoutY(305);
        lblEsConexo.setText("Conexo: ");
        lblEsConexo.setFont(Font.font("Centurie Gothic", 12));
        
        lblConexo.setLayoutX(110);
        lblConexo.setLayoutY(305);
        lblConexo.setText("(Ninguno)");
        lblConexo.setFont(Font.font("Centurie Gothic", 12));
        //
        
        //Funci�n del bot�n de agregar jar
        btnagregarJAR.setOnAction(new EventHandler<ActionEvent>(){  
            @Override
            public void handle(ActionEvent event){
                Stage stage = new Stage();
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Add Files(*.jar)", "*.jar");
                fileChooser.getExtensionFilters().add(extension);
                File file = fileChooser.showOpenDialog(stage);
                if(file != null) {
	                	jarActual.setText(file.toString());
	                	jarGrafo = new Dependencia(file.toString());
	                	jarGrafo.generarGrafoJars();
              }
            }
        });
        //Fin de la funci�n
        
        
        //Funcion del boton para mostrar el grafo
        btnGrafo.setOnAction(new EventHandler<ActionEvent>(){  
            @Override
            public void handle(ActionEvent event){
            	if(jarGrafo != null) {
            		DibujaGrafo dibujar = new DibujaGrafo();
            		dibujar.DibujarGrafo(jarGrafo);
            	}                 	

            }});
       //Fin de la funci�n      

                
        layout.getChildren().add(lstRankingRef);
		layout.getChildren().add(lstRankingDep);
        layout.getChildren().add(lblRankDep);
        layout.getChildren().add(lblRankRef);
        layout.getChildren().addAll(jarActual, lblJar, lblGradoSaliente, lblGradoEntrante, lblGE, lblGS, lblEsConexo, lblConexo);
        layout.getChildren().addAll(btnagregarJAR, btnGrafo);
		Scene scene = new Scene(layout, 430, 360);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
        
}
