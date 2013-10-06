
import groovyx.javafx.SceneGraphBuilder
import javafx.event.Event
import javafx.event.EventHandler
import javafx.stage.FileChooser
import RiskAssessment.ReasonCode

class FileOpenEventHandler implements EventHandler {
	SerializationUtil serializationUtil = new SerializationUtil()
	SceneGraphBuilder sgb
	PresentationModel pm

	/**
	 * Constructor
	 * @param sgb GroovyFX SceneGraphBuilder
	 * @param pm  the application presentation model
	 */
	FileOpenEventHandler(SceneGraphBuilder sgb, PresentationModel pm) {
		this.sgb = sgb
		this.pm = pm
	}

	/**
	 * Handle the File->Open menu event
	 *  by 1) launching the FileChooser to let the use select which file to open
	 *     2) populate the PresentationModel objects
	 * Handle deserializing a new file into the system,
	 * populate the treeview with new application data
	 */
	void handle(Event event) {
		def fileToOpen = selectFileToOpen()

		// open the file selected and update the presentation model
		if (fileToOpen)
		{
			// Update GUI: display the name of the selected file in the status bar
			//			   remove all of the old items from the path tree GUI control
			// 			   remove all of the old items from the split path tree GUI control
			sgb.selectedFile.text = fileToOpen
			sgb.finalTreeView.items.clear()
			sgb.originalTreeView.items.clear()
			
			// reset the presentation model
			pm.clear()

			// de-serialize the selected file 
			List<RiskAssessment> riskAssessments = serializationUtil.deserializeObjectFromFile(fileToOpen)
			
			// update our presentation model, filtering out zero qty paths that
			// were *not* adjusted to zero
			pm.assessments.addAll riskAssessments
			
			pm.finalAssessmentTreeViewModels.addAll buildFinalTreeViewModels(pm.assessments)
			sgb.finalTreeView.items.addAll pm.finalAssessmentTreeViewModels
					
			pm.originalAssessmentTreeViewModels.addAll buildOriginalTreeViewModels(pm.assessments)
			sgb.originalTreeView.items.addAll pm.originalAssessmentTreeViewModels

			// collapse the the trees			
			[
				sgb.finalTreeView,
				sgb.originalTreeView
			].each { treeView ->
				treeView.root.children.each { it.expanded = false }
			}
		}
	}

	/**
	 * In the presentation model, each AllocationPath is wrapped in an
	 * AllocationPathModel, which is used by the GUI. This method
	 * creates new model objects and stores the mapping between the
	 * AllocationPath and its wrapper in a map, which is part of the
	 * overall application presentation model.
	 * 
	 *  The closure recursively walk the parent allocation paths to map of gui tree item models.
	 *  The properties parent and children are confusing here; same analogy used in different contexts:
	 *  	-- the AllocationPath.parent is the previous state of the allocation path
	 *  	-- model.children refers to the GUI view of the data, children of a node in the tree control
	 * 
	 * note: The closure is *not* tail recursive.
	 * 
	 * @param the final list of allocationPaths that get passed into the GraphFlowDistribution Service
	 * @return the set of pathModels for these allocations paths
	 */
	Set<RiskAssessmentModel> buildFinalTreeViewModels(List<RiskAssessment> assessments) {
		
		// map keeps track of models that have been created so far
		def dataToModelMap = [:]
		
		assessments.collect { RiskAssessment assessment ->
			if (! dataToModelMap.containsKey(assessment)) {
				
				def model = new RiskAssessmentModel(riskAssessment: assessment)
				dataToModelMap[assessment] = model
	
				if (assessment.precedingAssessment) {
					
					// recursive call into this closure
					RiskAssessmentModel parentModel = call assessment.precedingAssessment
					  
					if (! model.children.contains(parentModel)) {
						model.children << parentModel
					}
				}
			}
			dataToModelMap[assessment]
		}
	}

	Set<RiskAssessmentModel> buildOriginalTreeViewModels(List<RiskAssessment> assessments) {
		// map keeps track of models that have been created so far
		def dataToModelMap = [:]
		
		assessments.collect { RiskAssessment assessment ->
			if (! dataToModelMap.containsKey(assessment)) {
				def model = new RiskAssessmentModel(riskAssessment: assessment)
				dataToModelMap[assessment] = model
	
				if (assessment.precedingAssessment) {
					
					// recursive call into this closure
					RiskAssessmentModel parentModel = call assessment.precedingAssessment  
					
					if (! parentModel.children.contains(model)) {
						parentModel.children << model
					}
				}
			}
			dataToModelMap[assessment]
		}
		dataToModelMap.values().findAll { it.riskAssessment.reason == ReasonCode.CREATE } as Set
	}

	/**
	 * Present the FileChooser to the user
	 * @return
	 */
	String selectFileToOpen() {
		new FileChooser().showOpenDialog()?.getAbsolutePath()
	}
}
