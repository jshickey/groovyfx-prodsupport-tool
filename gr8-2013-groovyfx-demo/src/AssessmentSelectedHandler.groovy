
import groovyx.javafx.SceneGraphBuilder
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.TreeItem


class AssessmentSelectedHandler implements ChangeListener {
	
	SceneGraphBuilder sgb
	PresentationModel pm
	
	AssessmentSelectedHandler(SceneGraphBuilder sgb, PresentationModel pm) {
		this.sgb = sgb
		this.pm = pm
	}

	void changed(ObservableValue<? extends TreeItem<RiskAssessmentModel>> ov, 
		         TreeItem<RiskAssessmentModel> old_val, 
				 TreeItem<RiskAssessmentModel> new_val) {
		
		if (new_val) {
			RiskAssessment model = new_val.value.riskAssessment
			pm.selectedAssessment.firstName = model.person.firstName
			pm.selectedAssessment.lastName = model.person.lastName
			pm.selectedAssessment.score = model.score
		}
	}
//			println "pm.selectedPathModel: ${pm.selectedPathModel.dump()}"
			
			
			
//			SupportToolAllocationPathData allocationPath = new_val.value.allocationPath
//			sgb.with {
//				selectedContractNumber.text = allocationPath.capacityVolume.schd.contractNumber
//				selectedUpK.text            = allocationPath.capacityVolume.schd.upstreamContractNumber
//				selectedNonSysUpK.text      = allocationPath.capacityVolume.schd.upstreamNonSystemContractNumber
//				selectUpKDuns.text          = allocationPath.capacityVolume.schd.upstreamDunsNumber
//				selectedRcptPt.text         = allocationPath.capacityVolume.schd.receiptPointNumber
//				selectedRcptPtPriority.text = allocationPath.capacityVolume.schd.upstreamPriority
//				selectedRcptVol.text        = allocationPath.capacityVolume.schd.receiptVolume
//				protectedReceiptVol.text    = allocationPath.capacityVolume.schd.protectedReceiptVolume
//				selectedDelVol.text         = allocationPath.capacityVolume.schd.deliveryVolume
//				protectedDeliveryVol.text   = allocationPath.capacityVolume.schd.protectedDeliveryVolume
//				selectedFuelVol.text        = allocationPath.capacityVolume.schd.fuelVolume
//	
//				selectedContractDuns.text   = allocationPath.capacityVolume.schd.nominatorDunsNumber
//				selectedDnK.text            = allocationPath.capacityVolume.schd.downstreamContractNumber
//				selectedNonSysDnK.text      = allocationPath.capacityVolume.schd.downstreamNonSystemContractNumber
//				selectedDnKDuns.text        = allocationPath.capacityVolume.schd.downstreamDunsNumber
//				selectedDelPt.text          = allocationPath.capacityVolume.schd.deliveryPointNumber
//				selectedDelPtPriority.text  = allocationPath.capacityVolume.schd.downstreamPriority
//				selectedTranType.text       = allocationPath.capacityVolume.schd.transactionTypeCode
//				selectedServiceType.text    = allocationPath.capacityVolume.schd.serviceTypeCode
//				selectedPkgId.text          = allocationPath.capacityVolume.schd.packageId ?: "n/a"
//				selectedFuelRate.text       = allocationPath.capacityVolume.schd.fuelRate
//			}
//		} else {
//			sgb.with {
//				selectedContractNumber.text = null
//				selectedUpK.text            = null
//				selectedNonSysUpK.text      = null
//				selectUpKDuns.text          = null
//				selectedRcptPt.text         = null
//				selectedRcptPtPriority.text = null
//				selectedRcptVol.text        = null
//				protectedReceiptVol.text    = null
//				selectedDelVol.text         = null
//				protectedDeliveryVol.text   = null
//				selectedFuelVol.text        = null
//	
//				selectedContractDuns.text   = null
//				selectedDnK.text            = null
//				selectedNonSysDnK.text      = null
//				selectedDnKDuns.text        = null
//				selectedDelPt.text          = null
//				selectedDelPtPriority.text  = null
//				selectedTranType.text       = null
//				selectedServiceType.text    = null
//				selectedPkgId.text          = null
//				selectedFuelRate.text       = null
//			}
//		}
//	} 

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		changed(arg0, arg1, arg2) 
	}
}
