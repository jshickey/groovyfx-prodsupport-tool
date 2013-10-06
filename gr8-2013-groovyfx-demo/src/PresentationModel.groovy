

class PresentationModel {
	// holds the data from the production app
	List<RiskAssessment> assessments = []
	
	SelectedRiskAssessment selectedAssessment
	SearchFormModel searchFormModel
	
	// Contains a list of presentation models that wrap the final assessments,
	// navigation through the tree lets you step back up the history
	// It answers: "How did I get here (backward looking)?"
	Set<RiskAssessment> finalAssessmentTreeViewModels = []

	// Contains a list of presentation models for the original assessments,
	// clicking the through the tree lets you see the progression of the assessment.
	// It answers: "What happened to this person's assessment (forward looking)?"
	Set<RiskAssessment> originalAssessmentTreeViewModels = []
	
	// clear the model, used when loading a new file
	def clear() {
		assessments.clear()
		finalAssessmentTreeViewModels.clear()
		originalAssessmentTreeViewModels.clear()
	}

}
