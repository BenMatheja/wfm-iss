package org.camunda.bpm.iss.entity.util;

public class GlobalDefinitions {

	public static final boolean USE_MOCK_SOLUTION_ISS = false;
	public static final boolean USE_MOCK_SOLUTION_PB = false;
	
	public static final String URL_PINKBLOB_BASE = "http://10.66.33.171:8080/pinkblob-wfms/api";
	public static final String URL_PINKBLOB_BASE_MOCK = "http://localhost:8080/iss-project/api/pb";
	public static final String URL_ISS_BASE = "http://localhost:8080/iss-project/api/iss";
	public static final String URL_ISS_BASE_MOCK = "http://localhost:8080/iss-project/api/iss";
	public static final String URL_API_PB_RECEIVE_DESIGN_REQUEST = "/design/new";
	public static final String URL_API_ISS_RECEIVE_ADDITIONAL_INFORMATION_REQEUST = "/additionalInformation/request";
	public static final String URL_API_PB_RECEIVE_ADDITIONAL_INFORMATION_RESPONSE = "/additionalInformation/respond";
	public static final String URL_API_ISS_RECEIVE_STATUS_UPDATE_DRAFT = "/status/draft/update";
	public static final String URL_API_ISS_RECEIVE_STATUS_UPDATE_DESIGN = "/status/design/update";
	public static final String URL_API_ISS_RECEIVE_DESIGN = "/design/receive";
	public static final String URL_API_PB_RECEIVE_DESIGN_FEEDBACK = "/design/feedback";
	public static final String URL_API_ISS_RECEIVE_BILL = "/bill/receive";
	
	public static final String EXECUTION_VARIABLES_DESIGN_JOB_ID = "designJobId";
	public static final String EXECUTION_VARIABLES_ADDITIONAL_INFO_ID = "addInfoId";
	
	public static final String PROCESS_NAME_PINK_BLOB = "the_pink_blob_process";
	
	public static final String ACTIVITY_ADDITIONAL_INFO_RECEIVED = "ICME_AddInfoReceived";
	public static final String ACTIVITY_DESIGN_DECISION = "ICME_Decision";
		
	public static final String TRANSITION_ADDITIONAL_INFO_RECEIVED_AFTER = "F_ICME_AddInfoReceived_To_XJ";

	public static final String TRANSITION_DESIGN_DECISION_AFTER = "F_ICME_Decision_To_ServT_CheckDecisionReceived";
	
	public static long JOB_ID;


	public static String getPbBaseURL(){
		return (USE_MOCK_SOLUTION_PB) ? URL_PINKBLOB_BASE_MOCK : URL_PINKBLOB_BASE;
	}
	
	public static String getIssBaseURL(){
		return (USE_MOCK_SOLUTION_ISS) ? URL_ISS_BASE_MOCK : URL_ISS_BASE;
	}

	public static long getJOB_ID() {
		return JOB_ID;
	}

	public static void setJOB_ID(long jOB_ID) {
		JOB_ID = jOB_ID;
	}
	
	

	
}
