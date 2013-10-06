import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeCell
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

/**
 * This class drives how each item in the tree view is displayed.
 * It is also the place where we define a context-menu for each tree item. 
 *
 */
class AssessmentTreeCell  extends TreeCell<RiskAssessmentModel> {
	private contextMenu = new ContextMenu()

	AssessmentTreeCell() {
		super()

		// add a context menu for copying tree item data to the clipboard
		setContextMenu(contextMenu)
		MenuItem copyMenuItem = new MenuItem("Copy to Clipboard")
		contextMenu.getItems().add(copyMenuItem)
		
		copyMenuItem.setOnAction( { Event t ->
			with Clipboard.systemClipboard {
				content = new ClipboardContent()
				content.putString text
			}
//			def clipboard = Clipboard.getSystemClipboard()
//			clipboard.content = new ClipboardContent()
//			clipboard.content.putString text
		} as EventHandler)
	}
	
	
	/**
	 * This method is called whenever the model behind a tree item is changed. It
	 * is responsible for creating the text that is displayed for each tree view item.
	 */
	void updateItem(RiskAssessmentModel riskAssessmentModel, boolean empty)
	{
		super.updateItem(riskAssessmentModel, empty)
		if (!empty && riskAssessmentModel) {
			def riskAssessment = riskAssessmentModel.riskAssessment
			
			// build the string displayed in the tree
			setText "${riskAssessment.person.firstName} ${riskAssessment.person.lastName} risk score: ${riskAssessment.score} "
							
			// highlight the selected path in the GUI using CSS
			if (riskAssessmentModel?.isSelected)  {
				setStyle '-fx-background-color: black;-fx-text-fill: yellow;'
			}
		}
	}
}
