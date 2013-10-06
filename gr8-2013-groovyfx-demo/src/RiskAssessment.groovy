
class RiskAssessment implements Serializable{
	enum ReasonCode  { CREATE, STEP1, STEP2, STEP3, STEP4 }

	Integer score = 0
	ReasonCode reason = ReasonCode.CREATE
	Person person
	RiskAssessment precedingAssessment
}
