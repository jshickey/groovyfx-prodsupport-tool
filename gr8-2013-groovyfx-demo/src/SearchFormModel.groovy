import groovyx.javafx.beans.FXBindable
import RiskAssessment.ReasonCode

/**
 * Model class used for binding to the selected item detail pane
 */
@FXBindable
class SearchFormModel {
	BigDecimal score = 0.0
	ReasonCode reason
	String lastName
}
