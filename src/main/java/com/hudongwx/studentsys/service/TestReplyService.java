package com.hudongwx.studentsys.service;

import com.hudongwx.studentsys.common.Service;
import com.hudongwx.studentsys.model.TestQuestionnaireClass;
import com.hudongwx.studentsys.model.TestReply;
import com.hudongwx.studentsys.util.Common;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wuhongxu on 2016/10/19 0019.
 */
public class TestReplyService extends Service {
    public TestQuestionnaireClassService tqcs;

    public boolean _saveTestReply(TestReply testReply) {
        return testReply.save();
    }

    public boolean _updateTestReply(TestReply testReply) {
        if (testReply.getId() == null)
            return false;
        return testReply.update();
    }

    public TestReply getByCache(Integer qcId, Integer studentId) {
        return getReply(qcId, studentId, true);
    }

    public TestReply getReply(Integer qcId, Integer studentId, boolean useCache) {
        TestReply reply = null;

        if (useCache)
            reply = TestReply.dao.findFirstByCache(Common.CACHE_LONG_TIME_LABEL, qcId + "-" + studentId,
                    TestReply.SEARCH_FROM_TEST_REPLY + "where testQuestionnaireClassId = ? and studentId = ?", qcId, studentId);
        else
            reply = TestReply.dao.findFirst(TestReply.SEARCH_FROM_TEST_REPLY + "where testQuestionnaireClassId = ? and studentId = ?", qcId, studentId);
        if (reply == null) {
            reply = new TestReply();
            reply.setTestQuestionnaireClassId(qcId);
            reply.setStudentId(studentId);
            reply.setAnswers("{}");
        }
        return reply;
    }

    public TestReply putByCache(TestReply testReply, boolean isSave) {
        if (isSave) {
            if (testReply.getId() == null)
                _saveTestReply(testReply);
            else
                _updateTestReply(testReply);
        }
        CacheKit.put(Common.CACHE_LONG_TIME_LABEL, testReply.getTestQuestionnaireClassId() + "-" + testReply.getStudentId(), testReply);
        return testReply;
    }

    public TestReply putByCache(TestReply testReply) {
        return putByCache(testReply, false);
    }

    public List<TestReply> getReplies(Integer qcId) {
        return TestReply.dao.find(TestReply.SEARCH_FROM_TEST_REPLY + "where testQuestionnaireClassId = ? order by id desc", qcId);
    }

    public TestReply getReplyById(Integer replyId) {
        return TestReply.dao.findById(replyId);
    }

    public List<TestReply> getReplyByStudentId(Integer stuId) {
        if (stuId == null)
            return null;
        List<TestReply> replyList = TestReply.dao.find(TestReply.SEARCH_FROM_TEST_REPLY + " where studentId = ?", stuId);
        if (replyList.isEmpty())
            return null;
        return replyList.stream().map(testReply -> {
            TestQuestionnaireClass questionnaireClass = tqcs.getById(testReply.getTestQuestionnaireClassId());
            testReply.setTime(questionnaireClass.getTestQuestionnaireEndTime());
            _updateTestReply(testReply);
            return testReply;
        }).collect(Collectors.toList());
    }

}
