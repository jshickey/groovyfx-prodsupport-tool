import static groovyx.javafx.GroovyFX.start
import javafx.event.EventHandler
import javafx.util.Callback
import RiskAssessment.ReasonCode
import de.thomasbolz.javafx.NumberTextField
import extfx.util.TreeViewWithItems
import groovyx.javafx.SceneGraphBuilder


// The is the main container for the all of the JavaFX components
SceneGraphBuilder sceneGraphBuilder
thePrimaryStage = null
def selectedAssessment = new SelectedRiskAssessment()
def searchFormModel = new SearchFormModel()

// Display the name of data file loaded
fileOpenedStatusBar =  {
	hbox(spacing:10) {
		label("File: ", style: "-fx-font-size: 18px;", textFill: white, halignment: "center", valignment: 'baseline')
		label(id: 'selectedFile', hgrow: 'never',  textFill: white, valignment:'baseline', style: "-fx-font-size: 18px;")
	}
}

appMenu = {
	menuBar {
		menu("File") {
			menuItem("Open", id: 'fileOpen', onAction: { println "Open" }) {
				rectangle(width: 16, height: 16, fill: red)
			}
			saveAs = menuItem("SaveAs..", onAction: {  println "Save As"  })
			separatorMenuItem()
			menuItem("Exit", id: 'fileExit')
		}
	}
}

searchPanel = {
	vbox(style: '-fx-border-color: white;-fx-padding: 10; -fx-spacing: 10') {
		label("Search: ", style: "-fx-font-size: 18px;",
		textFill: white, halignment: "center", valignment: 'baseline',
		margin: [0, 0, 10])

		label(text: 'Last Name:', hgrow: 'never',  textFill: white)
		textField(id: 'lastNameSearch', text: bind(searchFormModel.lastName()))

		label(text: 'Score:', hgrow: 'never',  textFill: white)
		numberTextField(id: 'scoreSearch', number: bind(searchFormModel.score()))
		
		label(text: 'Reason:', hgrow: 'never',  textFill: white)
		choiceBox(id: 'reasonSearch', items: ReasonCode.enumConstants as List, prefWidth: 250, hgrow: 'always',
			value: bind(searchFormModel.reason()))
		
		button(text: 'Search', id: 'searchButton')
	}
}

pathDetailPane = {
	gridPane(hgap: 5, vgap: 5, padding: 5, alignment: "top_left") {
		columnConstraints(minWidth: 75 )
		label("Selected Assessment Detail", style: "-fx-font-size: 18px;",
			textFill: white, row: 0, columnSpan: 4, halignment: "left",
			margin: [0, 0, 10])
		int row = 1
		int col = 0
		
		label(text: 'First Name:', hgrow: 'never', row:  ++row, column: col, textFill: white)
		label(text: bind(selectedAssessment.firstName()),
			hgrow: 'never', row: row, column: col +1, textFill: white)
		label(text: 'Last Name:', hgrow: 'never', row:  ++row, column: col, textFill: white)
		label(text: bind(selectedAssessment.lastName()),
			hgrow: 'never', row: row, column: col +1, textFill: white)
		label(text: 'Score:', hgrow: 'never', row:  ++row, column: col, textFill: white)
		label(text: bind(selectedAssessment.score()).using{it.toString()},
			hgrow: 'never', row: row, column: col +1, textFill: white)
	}
}

// The main layout of the GUI
def layoutFrame(SceneGraphBuilder sgb) {
	sgb.stage(title: "Assessment Support Tool", x: 20, y: 20, visible: true, style: "decorated") {
		scene(width: 1200, height: 700, fill: GROOVYBLUE) {
			borderPane {
				bottom { delegate.with fileOpenedStatusBar }
				top    { delegate.with appMenu }
				left(align: CENTER) { delegate.with searchPanel }
				center(align: CENTER) {
					vbox (style: '-fx-border-color: white;-fx-padding: 10;') {
						delegate.with pathDetailPane
						tabPane(vgrow:'always') {
							tab('Final Status w. History') {
								stackPane (style: '-fx-border-color: white;-fx-padding: 10;')
								{
									treeViewWithItems(id: 'finalTreeView', showRoot: false, visible: true,
											 cellFactory: {new AssessmentTreeCell()} as Callback) { treeItem(expanded: false) }
								}
							}
							tab('Original List') {
								stackPane (style: '-fx-border-color: white;-fx-padding: 10;')
								{
									treeViewWithItems(id: 'originalTreeView', showRoot: false, visible: true,
											 cellFactory: {new AssessmentTreeCell()} as Callback) { treeItem(expanded: false) }
								}
							}
						}
					}
				}
			}
		}
	}
}

def attachHandlers(SceneGraphBuilder sgb, PresentationModel pm) {
	def assessmentSelectedHandler=  new AssessmentSelectedHandler(sgb,pm)
	sgb.finalTreeView.selectionModel.selectedItemProperty().addListener(assessmentSelectedHandler)
	sgb.originalTreeView.selectionModel.selectedItemProperty().addListener(assessmentSelectedHandler)
	
	sgb.searchButton.onAction = new SearchButtonHandler(sgb, pm)
	sgb.fileOpen.onAction = new FileOpenEventHandler(sgb, pm)

	sgb.fileExit.onAction = {
		println "file exit"
		thePrimaryStage.close()
	} as EventHandler
}
	
// The main entry point for a GroovyFX program.
start {
	// let GroovyFX know about a third-party GUI control, which let's us use it in the GroovyFX builders
	registerBeanFactory 'treeViewWithItems', TreeViewWithItems
	registerBeanFactory 'numberTextField', NumberTextField
	
	// we want a pointer to the screen graph builder, which is this closure's delegate so we can pass it around and access the
	// GUI widgets by their ID
	sceneGraphBuilder = delegate
	layoutFrame sceneGraphBuilder
	attachHandlers(sceneGraphBuilder, new PresentationModel(selectedAssessment: selectedAssessment, searchFormModel: searchFormModel))
	thePrimaryStage = primaryStage
	primaryStage.show()
}
