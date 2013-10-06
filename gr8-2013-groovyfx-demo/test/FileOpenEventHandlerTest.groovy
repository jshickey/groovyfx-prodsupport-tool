import static org.junit.Assert.*
import groovyx.javafx.SceneGraphBuilder;

import org.junit.Test

import RiskAssessment.ReasonCode

class FileOpenEventHandlerTest {
	SceneGraphBuilder sgb
	PresentationModel pm
	FileOpenEventHandler handler = new FileOpenEventHandler(sgb, pm)
	def serializationUtil = new SerializationUtil()
	
	//@Test
	void testBuildFinalTreeViewModels() {
		def fileToOpen = 'test-assessments.ser'
		List<RiskAssessment> riskAssessments = serializationUtil.deserializeObjectFromFile(fileToOpen)
		Set<RiskAssessmentModel> models = handler.buildFinalTreeViewModels(riskAssessments)
		
		assertEquals 3, models.size()
		assertEquals 23, models.first().riskAssessment.score
		assertEquals 1, models.first().children.size()
		assertEquals 15, models.first().children.first().riskAssessment.score
		assertEquals 10, models.first().children.first()
									   .children.first().riskAssessment.score
		assertEquals 0, models.first().children.first()
									   .children.first()
									   .children.first().riskAssessment.score
	}
	
	
	//@Test
	void testBuildOriginalTreeViewModels() {
		def fileToOpen = 'test-assessments.ser'
		List<RiskAssessment> riskAssessments = serializationUtil.deserializeObjectFromFile(fileToOpen)
		Set<RiskAssessmentModel> models = handler.buildOriginalTreeViewModels(riskAssessments)
		
		assertEquals 3, models.size()
		assertEquals 0, models.first().riskAssessment.score
		assertEquals 1, models.first().children.size()
		assertEquals 10, models.first().children.first().riskAssessment.score
		assertEquals 15, models.first().children.first()
									   .children.first().riskAssessment.score
		assertEquals 23, models.first().children.first()
									   .children.first()
									   .children.first().riskAssessment.score
	}


	@Test
	void createSerializedTestFile() {
		def p1 = new Person(firstName:'Scott', lastName:'Hickey', state:'NE')
		def a1 = new RiskAssessment(person: p1)
		def a2 = new RiskAssessment(person: p1, score: 10, reason: ReasonCode.STEP1, precedingAssessment: a1) 	
		def a3 = new RiskAssessment(person: p1, score: 15, reason: ReasonCode.STEP2, precedingAssessment: a2) 	
		def a4 = new RiskAssessment(person: p1, score: 23, reason: ReasonCode.STEP3, precedingAssessment: a3) 	


		def p2 = new Person(firstName:'TJ', lastName:'Smith', state:'NE')
		def a11 = new RiskAssessment(person: p2)
		def a12 = new RiskAssessment(person: p2, score: 12, reason: ReasonCode.STEP1, precedingAssessment: a11) 	
		def a13 = new RiskAssessment(person: p2, score: 14, reason: ReasonCode.STEP2, precedingAssessment: a12) 	
		def a14 = new RiskAssessment(person: p2, score: 28, reason: ReasonCode.STEP3, precedingAssessment: a13)

		def p3 = new Person(firstName:'Shiraz', lastName:'Jones', state:'IA')
		def a31 = new RiskAssessment(person: p3)
		def a32 = new RiskAssessment(person: p3, score: 11, reason: ReasonCode.STEP1, precedingAssessment: a31)
		def a33 = new RiskAssessment(person: p3, score: 12, reason: ReasonCode.STEP2, precedingAssessment: a32)
		def a34 = new RiskAssessment(person: p3, score: 21, reason: ReasonCode.STEP3, precedingAssessment: a33)

		serializationUtil.serializeObjectToFile([a4,a14,a34], 'test-assessments.ser')
	}
}
