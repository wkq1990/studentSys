package com.hudongwx.surveys.model;

import com.hudongwx.surveys.common.BaseQuestionnaire;
import com.hudongwx.surveys.service.QuestionnaireNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Questionnaire extends BaseQuestionnaire<Questionnaire> {
	public static final Questionnaire dao = new Questionnaire();
	private List<QuestionnaireNode> questionnaireNodeList = new ArrayList<>();

	public List<QuestionnaireNode> getQuestionnaireNodeList() {
		return questionnaireNodeList;
	}

	public void setQuestionnaireNodeList(List<QuestionnaireNode> questionnaireNodeList) {
		this.questionnaireNodeList = questionnaireNodeList;
	}
}
