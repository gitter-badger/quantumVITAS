/*******************************************************************************
 * Copyright (c) 2020 Haonan Huang.
 *
 *     This file is part of QuantumVITAS (Quantum Visualization Interactive Toolkit for Ab-initio Simulations).
 *
 *     QuantumVITAS is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     QuantumVITAS is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with QuantumVITAS.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 *******************************************************************************/
package app;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import com.consts.Constants.EnumCalc;
import com.consts.Constants.EnumStep;
import com.consts.DefaultFileNames.settingKeys;
import com.error.ErrorMsg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.MainClass;
import app.input.*;
import app.viewer3d.WorkScene3D;

public class MainWindowController implements Initializable{
	
	@FXML private BorderPane basePane;
	
    @FXML private Menu menuFile;
    
    @FXML private MenuButton calcMain;
    
    @FXML private MenuItem calcScf,calcOpt,calcDos,calcBands,calcMd,calcTddft,calcCustom,menuAbout,menuSaveProjectAs,menuLoadProject;
    
    @FXML private Button createProject,showInputButton,runJob,buttonOpenWorkSpace,buttonOpenQEEngine,saveProjectButton;
    
    @FXML private Label textWorkSpace,textQEEngine;
    
    @FXML private Pane paneQEEngine,paneWorkSpace;
    
    @FXML private ComboBox<String> comboProject,comboCalculation;
    
	@FXML private ScrollPane inputField;
	
	@FXML private HBox hboxRight,hboxLeft;
	
	@FXML private Label calcLabel;
	
	@FXML private TabPane workSpaceTabPane;
	
	@FXML private RadioButton radioGeometry, radioCalculation;
	
	@FXML private BorderPane rootPane;
	
	private ScrollPane scrollGeo,scrollOpt,scrollScf,scrollDos,scrollBands,scrollMd,scrollTddft;
	
	private ScrollPane scrollLeft;
	
	private TabPane tabPaneRight;
	
	private Boolean tabPaneStatusRight,scrollStatusLeft;
	
	private VBox vboxRight,vboxLeft;
	
	private Button btnRight,btnLeft;
	
	private MainClass mainClass;
	
	private InputScfController contScf;
	
	private InputGeoController contGeo;
	
	private MainLeftPaneController contTree;
	
	private HashMap<String, Tab> projectTabDict;
	
	private final ToggleGroup tgGroup = new ToggleGroup();
	
	
	
	public MainWindowController(MainClass mc) {
		mainClass = mc;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		workSpaceTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);

		
		projectTabDict = new HashMap<String, Tab>();
		
		//set the style of workspace and QEEngine fields
		textWorkSpace.setBackground(new Background(new BackgroundFill(Color.WHITE, 
				CornerRadii.EMPTY, Insets.EMPTY)));
		textQEEngine.setBackground(new Background(new BackgroundFill(Color.WHITE, 
				CornerRadii.EMPTY, Insets.EMPTY)));
		textWorkSpace.prefWidthProperty().bind(paneWorkSpace.widthProperty());
		textWorkSpace.prefHeightProperty().bind(paneWorkSpace.heightProperty());
		textQEEngine.prefWidthProperty().bind(paneQEEngine.widthProperty());
		textQEEngine.prefHeightProperty().bind(paneQEEngine.heightProperty());
		
		tabPaneRight = null;
		tabPaneStatusRight = false;
		
		
		
		// load all relevant panes and sub-panes
		try {
			contScf = new InputScfController(mainClass);
			FXMLLoader fxmlLoader1 = new FXMLLoader(this.getClass().getResource("input/InputScf.fxml"));
			fxmlLoader1.setController(contScf);
			scrollScf = fxmlLoader1.load();
			contScf.initialize();//must be later than the load
			
			contGeo = new InputGeoController(mainClass);
			FXMLLoader fxmlLoader2 = new FXMLLoader(this.getClass().getResource("input/InputGeo.fxml"));
			fxmlLoader2.setController(contGeo);
			scrollGeo = fxmlLoader2.load();
			contGeo.initialize();//must be later than the load
			
			scrollOpt = FXMLLoader.load(getClass().getResource("input/InputOpt.fxml"));
			scrollDos = FXMLLoader.load(getClass().getResource("input/InputDos.fxml"));
			scrollBands = FXMLLoader.load(getClass().getResource("input/InputBands.fxml")); 
			scrollMd = FXMLLoader.load(getClass().getResource("input/InputMd.fxml")); 
			scrollTddft = FXMLLoader.load(getClass().getResource("input/InputTddft.fxml")); 
			
			contTree = new MainLeftPaneController(mainClass);
			FXMLLoader fxmlLoaderTree = new FXMLLoader(this.getClass().getResource("MainLeftPane.fxml"));
			fxmlLoaderTree.setController(contTree);
			scrollLeft = fxmlLoaderTree.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		contTree.buttonOpenSelected.setOnAction((event) -> {
			String projName = contTree.getSelectedProject();
			if(projName==null || projName.isEmpty()) return;
			
			File wsDir = mainClass.projectManager.getWorkSpaceDir();
			
			if(wsDir==null || !wsDir.canRead()) {return;}
			
			String msg = mainClass.projectManager.loadProject(wsDir, projName);
			
			if(msg.contains(ErrorMsg.alreadyContainsProject)) {
				Alert alert1 = new Alert(AlertType.INFORMATION);
		    	alert1.setTitle("Error");
		    	alert1.setContentText(msg);
		    	alert1.showAndWait();
		    	return;
	    	}
					
			if(msg.contains(ErrorMsg.cannotFindProjectFolder)) {
				contTree.updateProjects(wsDir);
				Alert alert1 = new Alert(AlertType.INFORMATION);
		    	alert1.setTitle("Error");
		    	alert1.setContentText(msg);
		    	alert1.showAndWait();
				return;}
			
			creatProject(projName);//loading GUI
			
			contTree.setOpenCloseButtons(false);
			
			if(msg!=null) {
				Alert alert1 = new Alert(AlertType.INFORMATION);
		    	alert1.setTitle("Info");
		    	alert1.setContentText(msg);
		    	alert1.showAndWait();
			}
		});
		contTree.buttonCloseSelected.setOnAction((event) -> {
			String projName = contTree.getSelectedProject();
			closeProject(projName);//should be null safe and empty safe
		});
		
		radioGeometry.setText("Geometry");
		radioGeometry.setToggleGroup(tgGroup);
		radioGeometry.setSelected(true);
		radioCalculation.setText("Calculation");
		radioCalculation.setToggleGroup(tgGroup);
		tgGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
			toggleGeometry();
		});
		
		setProjectNull();
		
		initializeLeftRightPane();//initialize tabPaneRight
		
		//load environment variable
		String wsp = mainClass.projectManager.readGlobalSettings(settingKeys.workspace.toString());
		mainClass.projectManager.workSpacePath = wsp;
		if (wsp!=null) {
			File wsDir = new File(wsp);
			if(wsDir!=null && wsDir.canRead()) {
				textWorkSpace.setText(wsp);
				setWorkSpace(true);
			}
			else {
				setWorkSpace(false);
				textWorkSpace.setBackground(new Background(new BackgroundFill(Color.RED, 
						CornerRadii.EMPTY, Insets.EMPTY)));
				Alert alert1 = new Alert(AlertType.INFORMATION);
		    	alert1.setTitle("Warning");
		    	alert1.setContentText("Cannot load the previous workspace directory. Please specify a new one.");
		    	alert1.showAndWait();
			}
		}
		else {
			setWorkSpace(false);
			Alert alert1 = new Alert(AlertType.INFORMATION);
	    	alert1.setTitle("Message");
	    	alert1.setContentText("Please specify a workspace directory to start with.");
	    	alert1.showAndWait();
		}
		
		if(wsp!=null) {contTree.updateProjects(new File(wsp));}
		
		String wsp2 = mainClass.projectManager.readGlobalSettings(settingKeys.pseudolibroot.toString());
		mainClass.projectManager.pseudoLibPath = wsp2;
		
		createProject.setOnAction((event) -> {
//			String oldProjectTemp = currentProject;
			
			TextInputDialog promptProjName = new TextInputDialog(); 
			String projName = null;
			String msg = null;
			promptProjName.setHeaderText("Enter the project name");
			do {
				Optional<String> result = promptProjName.showAndWait();
				if (result.isPresent()) {
					projName = promptProjName.getEditor().getText();
					msg = mainClass.projectManager.checkProjectName(projName);
					promptProjName.setHeaderText(msg);
				}else {
					return;
				}
			} while (msg!=null);
			
			File wsDir = mainClass.projectManager.getWorkSpaceDir();
			if(wsDir==null) return;
			File projDir = new File(wsDir,projName);
			
			if (projDir.exists()) {
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setContentText("Project with the same name already existed in the workspace. Please try another name.");
		    	alert.showAndWait();
				return;
			}
			
			msg = mainClass.projectManager.addProject(projName);
			
			if (msg!=null) return;
			
			//set project tree
			contTree.addProject(projName);
			creatProject(projName);
		});
		workSpaceTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			//******main code for changing project********
			if (newTab==null) {
				setProjectNull();
			}
			else {
				mainClass.projectManager.setActiveProject(newTab.getText());
				
				//add 3D view
				mainClass.projectManager.updateViewerPlot();
				WorkScene3D workScene = mainClass.projectManager.getActiveProject().getViewer3D();
				workScene.centerSubScene(workSpaceTabPane);
				AnchorPane acp = workScene.getRootPane();
				newTab.setContent(acp);
				
				comboProject.getSelectionModel().select(newTab.getText());
				//update calculation list
				comboCalculation.getItems().clear();
				//current calculation exists, so at least one calculation exists
				if (mainClass.projectManager.existCurrentCalc()) {
					ArrayList<String> al = mainClass.projectManager.getCurrentCalcList();
					for (String ec : al) {
						comboCalculation.getItems().add(ec);//******later, organize/sort the items
					}
					openCalc(mainClass.projectManager.getCurrentCalcName());
				}
				else {
					//******not the most efficient way, may run twice
					radioGeometry.setSelected(true);
					toggleGeometry();
				}
			}
	    });
		comboProject.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			if (newVal!=null) {
				//-------------********not the most efficient way*******---------------
				//will bounce between comboProject and workSpaceTabPane
				//no need to put the code executed when changing project again here!
				workSpaceTabPane.getSelectionModel().select(projectTabDict.get(newVal));
//				mainClass.projectManager.setActiveProject(newVal);
				openCalc(mainClass.projectManager.getCurrentCalcName());//openCalc is null tolerant
				
			}
	    });
		comboCalculation.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			if (newVal!=null && !newVal.isEmpty()) {
				mainClass.projectManager.setActiveCalculation(newVal);
				//-------------********not the most efficient way*******---------------
				openCalc(mainClass.projectManager.getCurrentCalcName());//openCalc is null tolerant
			}
	    });
		calcScf.setOnAction((event) -> {
			openCalc(EnumCalc.SCF,true);
		});
		calcOpt.setOnAction((event) -> {
			openCalc(EnumCalc.OPT,true);
		});
		calcDos.setOnAction((event) -> {
			openCalc(EnumCalc.DOS,true);
		});
		calcBands.setOnAction((event) -> {
			openCalc(EnumCalc.BANDS,true);
		});
		calcMd.setOnAction((event) -> {
			openCalc(EnumCalc.BOMD,true);
		});
		calcTddft.setOnAction((event) -> {
			openCalc(EnumCalc.TDDFT,true);
		});
		showInputButton.setOnAction((event) -> {
			mainClass.projectManager.genInputFromAgent();
			String pj = mainClass.projectManager.getActiveProjectName();
			if (pj==null || pj.isEmpty()) return;
			//workSceneDict.get(pj).buildGeometry(mainClass.projectManager.getCurrentGeoAgent());
//			Alert alert = new Alert(AlertType.INFORMATION);
//	    	alert.setTitle("Information Dialog");
//	    	if (mainClass.projectManager.getActiveProject()==null) {
//	    		alert.setContentText("Project not existing!");
//	    	}
//	    	else {
//	    		String tmpStr = "Current project: ";
//	    		tmpStr+=mainClass.projectManager.getActiveProjectName();
//	    		tmpStr+="\nCurrent calculation: ";
//	    		tmpStr+=mainClass.projectManager.getActiveProject().getActiveCalcName();
//	    		alert.setContentText(tmpStr);
//	    	}
//
//	    	alert.showAndWait();
		});
		saveProjectButton.setOnAction((event) -> {
			File wsDir = mainClass.projectManager.getWorkSpaceDir();
			if(wsDir==null || !wsDir.canWrite()) {return;}
			mainClass.projectManager.saveActiveProjectInMultipleFiles(wsDir);
		});
		menuSaveProjectAs.setOnAction((event) -> {
			
			DirectoryChooser dirChooser = new DirectoryChooser ();
			dirChooser.setTitle("Choose an alternative workspace folder to save the project");
			
			//go to current directory
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			File tmpFile = new File(currentPath);
			if(tmpFile!=null && tmpFile.canRead()) {
				dirChooser.setInitialDirectory(tmpFile);
			}
			
			File selectedDir = dirChooser.showDialog((Stage)rootPane.getScene().getWindow());
			
			if(selectedDir!=null && selectedDir.canWrite()) {
				mainClass.projectManager.saveActiveProjectInMultipleFiles(selectedDir);
			}
		});
		menuLoadProject.setOnAction((event) -> {
//			File wsDir = getWorkSpaceDir();
//			if(wsDir==null || !wsDir.canWrite()) {return;}
//			
//			FileChooser fileChooser = new FileChooser();
//			fileChooser.setInitialDirectory(wsDir);
//			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("project files", "*.proj"));
//			
//			File selectedFile = fileChooser.showOpenDialog((Stage)rootPane.getScene().getWindow());
//			
//			loadProject(selectedFile);
			
		});
		menuAbout.setOnAction((event) -> {
			Alert alert1 = new Alert(AlertType.INFORMATION);
	    	alert1.setTitle("Error");
	    	alert1.setContentText("QuantumVITAS (Quantum Visualization Interactive Toolkit for Ab-initio Simulations)"+
	    			" Copyright (C) 2020  Haonan Huang\r\n" + 
	    			"    This program comes with ABSOLUTELY NO WARRANTY; for details press `show more'.\r\n" + 
	    			"    This is free software, and you are welcome to redistribute it\r\n" + 
	    			"    under certain conditions; press `license' for details.");
	    	alert1.showAndWait();
		});
				
		buttonOpenWorkSpace.setOnAction((event) -> {
			String wsp1 = mainClass.projectManager.readGlobalSettings(settingKeys.workspace.toString());
			if(wsp1!=null) {
				File wsDir = new File(wsp1);
				if(mainClass.projectManager.existCurrentProject() && wsDir!=null && wsDir.canRead()) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
			    	alert1.setTitle("Warning");
			    	alert1.setContentText("Please close ALL projects before changing the workspace directory.");
			    	alert1.showAndWait();
			    	return;
				}
			}
			
			DirectoryChooser dirChooser = new DirectoryChooser ();
			
			//go to current directory
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			File tmpFile = new File(currentPath);
			if(tmpFile!=null && tmpFile.canRead()) {
				dirChooser.setInitialDirectory(tmpFile);
			}
			
			File selectedDir = dirChooser.showDialog((Stage)rootPane.getScene().getWindow());
			
			if(selectedDir!=null && selectedDir.canRead()) {
				mainClass.projectManager.workSpacePath = selectedDir.getPath();
				textWorkSpace.setText(mainClass.projectManager.workSpacePath);
				mainClass.projectManager.writeGlobalSettings(settingKeys.workspace.toString(),selectedDir.getPath());
				setWorkSpace(true);
				contTree.updateProjects(new File(mainClass.projectManager.workSpacePath));
//				textWorkSpace.setBackground(new Background(new BackgroundFill(Color.WHITE, 
//						CornerRadii.EMPTY, Insets.EMPTY)));
			}
			
		});
		buttonOpenQEEngine.setOnAction((event) -> {
			DirectoryChooser dirChooser = new DirectoryChooser ();
			
			//go to current directory
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			File tmpFile = new File(currentPath);
			if(tmpFile!=null && tmpFile.canRead()) {
				dirChooser.setInitialDirectory(tmpFile);
			}
			
			File selectedDir = dirChooser.showDialog((Stage)rootPane.getScene().getWindow());
			
			if(selectedDir!=null && selectedDir.canRead()) {
				textQEEngine.setText(selectedDir.getPath());
			}
			
		});
	}
	private void closeProject(String pj) {
		String tmp = mainClass.projectManager.removeProject(pj);
		if(tmp!=null) return;//cannot remove project: pj==null || pj is empty or pj not in the list
		Tab tab = projectTabDict.get(pj);
		if(tab!=null) {workSpaceTabPane.getTabs().remove(tab);}
		contTree.closeProject(pj);
		projectTabDict.remove(pj);
		comboProject.getItems().remove(pj);
		//openCalc(null);//not necessary. Covered by the change tab listener
		contTree.setOpenCloseButtons(true);
	}
	
	
	private void setWorkSpace(boolean bl) {
		if (bl) {
			for (Node node : rootPane.getChildrenUnmodifiable()) {
				node.setDisable(false);
		    }
			for (Node node : buttonOpenWorkSpace.getParent().getParent().getChildrenUnmodifiable()) {
				node.setDisable(false);
		    }
			for (Node node : buttonOpenWorkSpace.getParent().getChildrenUnmodifiable()) {
				node.setDisable(false);
		    }
		}
		else{
			for (Node node : rootPane.getChildrenUnmodifiable()) {
				node.setDisable(true);
		    }
			buttonOpenWorkSpace.getParent().getParent().setDisable(false);
			for (Node node : buttonOpenWorkSpace.getParent().getParent().getChildrenUnmodifiable()) {
				node.setDisable(true);
		    }
			buttonOpenWorkSpace.getParent().setDisable(false);
			for (Node node : buttonOpenWorkSpace.getParent().getChildrenUnmodifiable()) {
				node.setDisable(true);
		    }
			buttonOpenWorkSpace.setDisable(false);
			textWorkSpace.setDisable(false);
		}
	}
	private void toggleGeometry() {
		if (tabPaneRight==null) return;
		if (radioGeometry.isSelected()) 
		{
			if (!mainClass.projectManager.existCurrentProject()) return;//abnormal!
			//radioGeometry.setSelected(true);
			mainClass.projectManager.setGeoActive(true);
			
			Tab tab = new Tab();
			tab.setText(EnumStep.GEO.getName());
			tab.setContent(scrollGeo);
			tabPaneRight.getTabs().clear();
			tabPaneRight.getTabs().add(tab);
			contGeo.loadProjectParameters();
			contGeo.setEnabled();
			calcLabel.setText("Geometry");
			comboCalculation.setValue("");
			if (!tabPaneStatusRight) {
				hboxRight.getChildren().add(0,tabPaneRight);
				tabPaneStatusRight = true;
			}
		}
		else {
			openCalc(mainClass.projectManager.getCurrentCalcName());
		}
		
	}
	public void addRightPane(ScrollPane scroll,EnumStep es) {
		if (tabPaneRight==null) return;
		
		Tab tab = new Tab();
		tab.setText(es.getName());
		tab.setContent(scroll);
		tabPaneRight.getTabs().add(tab);
		
		if (!tabPaneStatusRight) {
			hboxRight.getChildren().add(0,tabPaneRight);
			tabPaneStatusRight = true;
		}
	}
	public void clearRightPane() {
		if (tabPaneRight!=null) {
			tabPaneRight.getTabs().clear();
		}
		if (tabPaneStatusRight) {
			hboxRight.getChildren().remove(tabPaneRight);
		}
		tabPaneStatusRight = false;
	}
	private void setProjectNull() {
		mainClass.projectManager.setActiveProject(null);
		calcMain.setDisable(true);
		runJob.setDisable(true);
		clearRightPane();
		comboCalculation.getItems().clear();
		radioGeometry.setSelected(true);
		calcLabel.setText("");
	}
	
	private void initializeLeftRightPane() {
		
		// right part, default off
		tabPaneStatusRight = false;
		vboxRight = new VBox();
		btnRight = new Button("R");
		vboxRight.getChildren().add(btnRight);
		hboxRight.getChildren().add(vboxRight);
		//right part, tab pane
		tabPaneRight = new TabPane();
		//Tab tab = new Tab("3e");
		//tabPaneRight.getTabs().add(tab);
		tabPaneRight.setPrefSize(375, 300);
		tabPaneRight.setMinSize(150, 150);
		tabPaneRight.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		//hboxRight.getChildren().add(0,tabPaneRight);
		
		//left part, default on
		scrollStatusLeft = true;
//		scrollLeft = new ScrollPane();
//		scrollLeft.setContent(projectTree);
		
		scrollLeft.setFitToHeight(true);
		vboxLeft = new VBox();
		btnLeft = new Button("L");
		vboxLeft.getChildren().add(btnLeft);
		hboxLeft.getChildren().addAll(vboxLeft,scrollLeft);
		
		//set button action
		btnRight.setOnAction((event) -> {
			if (tabPaneStatusRight) {
				hboxRight.getChildren().remove(tabPaneRight);
			}
			else {
				hboxRight.getChildren().add(0,tabPaneRight);
			}
			tabPaneStatusRight=!tabPaneStatusRight;
		});
		btnLeft.setOnAction((event) -> {
			if (scrollStatusLeft) hboxLeft.getChildren().remove(scrollLeft);
			else hboxLeft.getChildren().add(scrollLeft);
			scrollStatusLeft=!scrollStatusLeft;
		});
	}
	private void creatProject(String projName) {

		//add to ComboBox
		comboProject.getItems().add(projName);
		comboProject.setValue(projName);
		//add tab
		Tab tab = new Tab();
		final String pj = projName;
		tab.setText(pj);
		tab.setClosable(true);
		tab.setOnClosed((e) -> {
			closeProject(pj);
		});
				
		//add tab
		workSpaceTabPane.getTabs().add(tab);
		workSpaceTabPane.getSelectionModel().select(tab);
		projectTabDict.put(pj,tab);
		
		
		contTree.updateFullCalcTree();
		//allow more interactions
		calcMain.setDisable(false);
		runJob.setDisable(false);
		//enable add molecule button
		runJob.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Info");
	    	alert.setContentText("To be implemented!");
	    	alert.showAndWait();
	    	return;
		});
		toggleGeometry();
	}
	private void openCalc(String ecStr) {
		
		//load an existing calculation having name String ecStr
		if (ecStr==null || ecStr.isEmpty()) {
			clearRightPane();
			comboCalculation.getItems().clear();
			calcLabel.setText("");
			return;
		}

		mainClass.projectManager.setActiveCalculation(ecStr);
		 
		//check again whether successfully set the active calculation
		if(!ecStr.equals(mainClass.projectManager.getCurrentCalcName())) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setContentText("Cannot set active calculation string ecStr.");
	    	alert.showAndWait();
	    	return;
		}
		EnumCalc ec = mainClass.projectManager.getCurrentCalcType();//null safe
		openCalc(ec, false);
		
	}
	private void openCalc(EnumCalc ec, boolean boolCreate) {
		//if(boolCreate), open a new calculation of type EnumCalc ec
		//if(!boolCreate), just load one existing calculation. MUST be entered through openCalc(String ecStr) in this case!
		String calcName="";
		if (ec==null) {return;}
		
		//Boolean firstFlag=false;
//		if (mainClass.projectManager.existCurrentStep(EnumStep.SCF)) contScf.loadProjectParameters();
//		if (mainClass.projectManager.existCurrentStep(EnumStep.GEO)) contGeo.loadProjectParameters();
		
		switch(ec) {
		case SCF:
			if (!mainClass.projectManager.existCurrentProject()) return;//abnormal!
			
			radioCalculation.setSelected(true);
			mainClass.projectManager.setGeoActive(false);
			
			if(boolCreate) {
				//create new calculation even if SCF exists
				//need to update current calculation before loading parameters
				mainClass.projectManager.addCalcToActiveProj(EnumCalc.SCF); 
				calcName = mainClass.projectManager.getCurrentCalcName();
				//initialize controllers. This will be automatically done only once
				//***moved to the beginning of the program
				//add comboBox item
				comboCalculation.getItems().add(calcName);
				//update current status to trees
				contTree.updateCalcTree(calcName);
				
				
				
			}
			
			//load parameters for current project and calculation
			contGeo.loadProjectParameters();
			contGeo.setDisabled();
			contScf.loadProjectParameters();
			clearRightPane();
			addRightPane(scrollGeo,EnumStep.GEO);
			addRightPane(scrollScf,EnumStep.SCF);
			try {tabPaneRight.getSelectionModel().select(1);}catch (Exception e) {}//load second tab(not geo)
			
			calcLabel.setText(EnumCalc.SCF.getLong());
			
			calcName = mainClass.projectManager.getCurrentCalcName();
			if(calcName!=null) comboCalculation.getSelectionModel().select(calcName);
			
			break;
		case OPT:
			if (!mainClass.projectManager.existCurrentProject()) return;//abnormal!
			radioCalculation.setSelected(true);
			mainClass.projectManager.setGeoActive(false);
			
			if(boolCreate) {
				//need to update current calculation before loading parameters
				mainClass.projectManager.addCalcToActiveProj(EnumCalc.OPT); 
				calcName = mainClass.projectManager.getCurrentCalcName();
				//initialize controllers. This will be automatically done only once
				//***moved to the beginning of the program
				//add comboBox item
				comboCalculation.getItems().add(calcName);
				//update current status to trees
				contTree.updateCalcTree(calcName);
				
			}
			
			//load parameters for current project and calculation
			contGeo.loadProjectParameters();
			contGeo.setDisabled();
			contScf.loadProjectParameters();
			//update GUI
			clearRightPane();
			addRightPane(scrollGeo,EnumStep.GEO);
			addRightPane(scrollScf,EnumStep.SCF);
			addRightPane(scrollOpt,EnumStep.OPT);
			try {tabPaneRight.getSelectionModel().select(1);}catch (Exception e) {}//load second tab(not geo)
			
			calcLabel.setText(EnumCalc.OPT.getLong());
			calcName = mainClass.projectManager.getCurrentCalcName();
			if(calcName!=null) comboCalculation.getSelectionModel().select(calcName);
			break;
		case DOS:
//			if (currentProject!=null && !calcAvailDict.get(currentProject).get(EnumCalc.DOS)) {
//				//project exists, but this calculation does not
//				comboCalculation.getItems().add(calcString2.get(EnumCalc.DOS));
//				comboCalculation.setValue(calcString2.get(EnumCalc.DOS));
//			}
//			addRightPane(scrollScf,EnumStep.GEO);
//			addRightPane(scrollScf,EnumStep.SCF);
//			addRightPane(scrollScf,EnumStep.NSCF);
//			addRightPane(scrollDos,EnumStep.DOS);
//			calcLabel.setText(calcString1.get(EnumCalc.DOS));
//			currentCalcDict.put(currentProject,EnumCalc.DOS);
//			calcAvailDict.get(currentProject).put(EnumCalc.DOS, true);
//			updateCalcTree(EnumCalc.DOS);
			break;
		case BANDS:
//			if (currentProject!=null && !calcAvailDict.get(currentProject).get(EnumCalc.BANDS)) {
//				//project exists, but this calculation does not
//				comboCalculation.getItems().add(calcString2.get(EnumCalc.BANDS));
//				comboCalculation.setValue(calcString2.get(EnumCalc.BANDS));
//			}
//			addRightPane(scrollBands,EnumCalc.BANDS);
//			calcLabel.setText(calcString1.get(EnumCalc.BANDS));
//			currentCalcDict.put(currentProject,EnumCalc.BANDS);
//			calcAvailDict.get(currentProject).put(EnumCalc.BANDS, true);
//			updateCalcTree(EnumCalc.BANDS);
			break;
		case BOMD:
//			if (currentProject!=null && !calcAvailDict.get(currentProject).get(EnumCalc.BOMD)) {
//				//project exists, but this calculation does not
//				comboCalculation.getItems().add(calcString2.get(EnumCalc.BOMD));
//				comboCalculation.setValue(calcString2.get(EnumCalc.BOMD));
//			}
//			addRightPane(scrollMd,EnumCalc.BOMD);
//			calcLabel.setText(calcString1.get(EnumCalc.BOMD));
//			currentCalcDict.put(currentProject,EnumCalc.BOMD);
//			calcAvailDict.get(currentProject).put(EnumCalc.BOMD, true);
//			updateCalcTree(EnumCalc.BOMD);
			break;
		case TDDFT:
//			if (currentProject!=null && !calcAvailDict.get(currentProject).get(EnumCalc.TDDFT)) {
//				//project exists, but this calculation does not
//				comboCalculation.getItems().add(calcString2.get(EnumCalc.TDDFT));
//				comboCalculation.setValue(calcString2.get(EnumCalc.TDDFT));
//			}
//			addRightPane(scrollTddft,EnumCalc.TDDFT);
//			calcLabel.setText(calcString1.get(EnumCalc.TDDFT));
//			currentCalcDict.put(currentProject,EnumCalc.TDDFT);
//			calcAvailDict.get(currentProject).put(EnumCalc.TDDFT, true);
//			updateCalcTree(EnumCalc.TDDFT);
			break;
		default:
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setContentText("Wrong calculation type!");

	    	alert.showAndWait();
		}
	}
}
