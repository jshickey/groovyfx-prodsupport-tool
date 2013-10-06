import javafx.collections.FXCollections
import javafx.collections.ObservableList
import extfx.util.HierarchyData
import groovyx.javafx.beans.FXBindable

// GUI Model class that wraps the original RiskAssessment object
class RiskAssessmentModel implements HierarchyData <RiskAssessmentModel>{
	// Original Data
	RiskAssessment riskAssessment

	// GUI: display in tree view based on search form
	Boolean isVisible
	
	// GUI: highlight row with different color if selected
	Boolean isSelected	
	
	// GUI: the child that is displayed when the tree control is expanded
	//  note this data will either be the previous version or subsequent version
	//  of the assessment data, depending on which tree view control this model belongs to
	ObservableList<RiskAssessmentModel> children = FXCollections.observableArrayList(new ArrayList<RiskAssessmentModel>())
}
