import groovyx.javafx.SceneGraphBuilder
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.TreeView


/**
 * This is the handler (MVC Controller) for the Search Button.
 * 
 * This class will read what's been entered in the search panel 
 * and filter the backing collection of items for the
 * tree controls.
 * 
 *
 */
class SearchButtonHandler implements EventHandler
{
	PresentationModel pm
	SceneGraphBuilder sgb
	
	SearchButtonHandler(SceneGraphBuilder sgb, PresentationModel pm) {
		this.pm = pm
		this.sgb = sgb
	}
	
	void handle(Event event)
	{
		println pm.searchFormModel.dump()
		println sgb.scoreSearch.dump()
		// mark the paths in the model that match the search criteria
		[pm.finalAssessmentTreeViewModels, pm.originalAssessmentTreeViewModels].each { Set<RiskAssessmentModel> models ->
			models.each { RiskAssessmentModel model ->
				model.isVisible = matchesSearchCriteria(model.riskAssessment , pm.searchFormModel)
			}
		}

		// filter the treeView.items for those models marked visible
		[(sgb.finalTreeView) : pm.finalAssessmentTreeViewModels, 
		 (sgb.originalTreeView) : pm.originalAssessmentTreeViewModels].each { TreeView treeView, models ->
		 	treeView.items.addAll( models.findAll { it.isVisible }.minus( treeView.items ))
			treeView.items.removeAll( models.findAll { !it.isVisible })
		}
	}

	boolean matchesSearchCriteria(RiskAssessment riskAssessment, SearchFormModel searchFormModel)
	{
		(   (!searchFormModel.lastName|| searchFormModel.lastName == riskAssessment.person.lastName)
		 && (!searchFormModel.score   || searchFormModel.score == riskAssessment.score)
		 && (!searchFormModel.reason  || searchFormModel.reason == riskAssessment.reason))
	}

}
