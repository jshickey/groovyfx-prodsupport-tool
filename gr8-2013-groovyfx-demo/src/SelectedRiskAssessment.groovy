import groovyx.javafx.beans.FXBindable
import RiskAssessment.ReasonCode

/**
 * Model class used for binding to the selected item detail pane
 * @author t99709
 *
 */
@FXBindable
class SelectedRiskAssessment implements Serializable{
	Integer score = 0
	ReasonCode reason = ReasonCode.CREATE
	// Person
	String firstName
	String lastName
	String address
	String city
	String state
	String zip
}
