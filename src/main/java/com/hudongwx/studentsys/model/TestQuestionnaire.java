package com.hudongwx.studentsys.model;

import com.hudongwx.studentsys.common.BaseTestQuestionnaire;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class TestQuestionnaire extends BaseTestQuestionnaire<TestQuestionnaire> {
	public static final TestQuestionnaire dao = new TestQuestionnaire();
	public static final String SEARCH_FROM_TEST_QUESTIONNAIRE = "select * from stumanager_test_questionnaire ";
    public static final String SQL_FROM = " from stumanager_test_questionnaire ";
    private Long testQuestionnaireStartTime;
	private Long testQuestionnaireEndTime;
    private Integer testQuestionnaireClassId;
    private Long testQuestionnaireTempTime;
    private TestReply testReply;

    public TestReply getTestReply() {
        return testReply;
    }

    public void setTestReply(TestReply testReply) {
        this.testReply = testReply;
    }

    public Long getTestQuestionnaireEndTime() {
        return testQuestionnaireEndTime;
    }

    public void setTestQuestionnaireEndTime(Long testQuestionnaireEndTime) {
        this.testQuestionnaireEndTime = testQuestionnaireEndTime;
    }

    public Long getTestQuestionnaireStartTime() {
        return testQuestionnaireStartTime;
    }

    public void setTestQuestionnaireStartTime(Long testQuestionnaireStartTime) {
        this.testQuestionnaireStartTime = testQuestionnaireStartTime;
    }

    public Integer getTestQuestionnaireClassId() {
        return testQuestionnaireClassId;
    }

    public void setTestQuestionnaireClassId(Integer testQuestionnaireClassId) {
        this.testQuestionnaireClassId = testQuestionnaireClassId;
    }


    public Long getTestQuestionnaireTempTime() {
        return testQuestionnaireTempTime;
    }

    public void setTestQuestionnaireTempTime(Long testQuestionnaireTempTime) {
        this.testQuestionnaireTempTime = testQuestionnaireTempTime;
    }
}
