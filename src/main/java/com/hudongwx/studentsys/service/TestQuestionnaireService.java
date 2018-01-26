package com.hudongwx.studentsys.service;

import com.hudongwx.studentsys.common.Service;
import com.hudongwx.studentsys.model.*;
import com.hudongwx.studentsys.model.Class;
import com.hudongwx.studentsys.util.Common;
import com.hudongwx.studentsys.util.PageinateKit;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wuhongxu on 2016/10/10 0010.
 */
public class TestQuestionnaireService extends Service {
    private ClassService classService;
    private StudentService studentService;
    private TestQuestionnaireClassService testQuestionnaireClassService;
    private TestReplyService testReplyService;
    public List<TestQuestionnaire> getAllTestQuestionnaire() {
        return TestQuestionnaire.dao.find(TestQuestionnaire.SEARCH_FROM_TEST_QUESTIONNAIRE);
    }

    public Page<TestQuestionnaire> getAllTestQuestionnaire(Integer currentPage){
        return TestQuestionnaire.dao.paginate(currentPage, Common.MAX_PAGE_SIZE,Common.COMMON_SELECT,TestQuestionnaire.SQL_FROM);
    }

    public List<TestQuestionnaire> getQuestionnaireByClass(Class c) {
        if (c == null)
            return new ArrayList<>();
        return TestQuestionnaireClass.dao
                .find(TestQuestionnaireClass.SEARCH_FROM_TEST_QUESTIONNAIRE_CLASS + "where classId = ?", c.getId())
                .stream().map(this::packingQuestionnaire)
                .collect(Collectors.toList());
        /*return TestQuestionnaireClass.dao
                .find(TestQuestionnaireClass.SEARCH_FROM_TEST_QUESTIONNAIRE_CLASS + "where classId = ?", c.getId())
                .stream().map(tc -> TestQuestionnaire.dao.findById(tc.getTestQuestionnaireId()))
                .collect(Collectors.toList());*/
    }

    public Page<TestQuestionnaire> getQuestionnaireByClass(Integer currentPage, Class c,Integer stuId){
        if(c == null)
            return Common.EMPTY_PAGE;
        Page<TestQuestionnaireClass> paginate = TestQuestionnaireClass.dao
                .paginate(currentPage, Common.MAX_PAGE_SIZE, Common.COMMON_SELECT, TestQuestionnaireClass.SQL_FROM + Common.SQL_WHERE + " classId = ? ", c.getId());
        List<TestQuestionnaire> collect = paginate.getList()
                .stream().map(this::packingQuestionnaire)
                .collect(Collectors.toList());
        collect.stream().map(tq ->{
            tq.setTestReply(testReplyService.getReply(tq.getTestQuestionnaireClassId(),stuId,false));
            return tq;
        }).collect(Collectors.toList());
        return PageinateKit.ClonePage(paginate,collect);
    }

    public List<TestQuestionnaire> getQuestionnaireByStudent(Student student) {
        if (student == null)
            return new ArrayList<>();
        return getQuestionnaireByClass(classService.getClassByStudent(student));
    }
    public Page<TestQuestionnaire> getQuestionnaireByStudent(Integer currentPage,Student student) {
        if (student == null)
            return Common.EMPTY_PAGE;
        return getQuestionnaireByClass(currentPage,classService.getClassByStudent(student),student.getId());
    }
    public List<TestQuestionnaire> getQuestionnairesByUser(User user) {
        return getQuestionnaireByStudent(studentService.getStudentByUser(user));
    }

    public Page<TestQuestionnaire> getQuestionnairesByUser(Integer p,User user){
        return getQuestionnaireByStudent(p,studentService.getStudentByUser(user));
    }

    public TestQuestionnaire packingQuestionnaire(TestQuestionnaireClass tqc) {
        if(tqc == null)
            return null;
        TestQuestionnaire q = TestQuestionnaire.dao.findById(tqc.getTestQuestionnaireId());
        if (null == q)
            return null;
        q.setTestQuestionnaireStartTime(tqc.getTestQuestionnaireStartTime());
        q.setTestQuestionnaireEndTime(tqc.getTestQuestionnaireEndTime());
        q.setTestQuestionnaireClassId(tqc.getId());
        return q;
    }
    public TestQuestionnaire packingQuestionnaire(Integer testQuestionnaireClassId){
        return packingQuestionnaire(testQuestionnaireClassService.getById(testQuestionnaireClassId));
    }

    public Map<String, String> getMsgMapByQuestionnaire(TestQuestionnaire tq) {
        List<TestQuestionnaireClass> questionnaireClasses = TestQuestionnaireClass.dao.find(TestQuestionnaireClass.SEARCH_FROM_TEST_QUESTIONNAIRE_CLASS + "where testQuestionnaireId = ?", tq.getId());
        Map<String, String> msgMap = new HashMap<>();
        for (TestQuestionnaireClass tqc : questionnaireClasses) {
            msgMap.put(tq.getId() + "-" + tqc.getClassId() + "StartTime", tqc.getTestQuestionnaireStartTime() + "");
            msgMap.put(tq.getId() + "-" + tqc.getClassId() + "EndTime", tqc.getTestQuestionnaireEndTime() + "");
            Class aClass = classService.getClassById(tqc.getClassId());
            msgMap.put(tq.getId() + "-classId", aClass.getId() + "");
            msgMap.put(tq.getId() + "-" + tqc.getClassId() + "className", aClass.getClassName());
        }
        return msgMap;

    }

    public Page<TestQuestionnaire> getNowQuestionnaireByUser(Integer currentPage,User user){
        Class userClass = classService.getClassByStudent(studentService.getStudentByUser(user));
        long now = System.currentTimeMillis();
        Page<TestQuestionnaireClass> testQuestionnaireClasses = TestQuestionnaireClass.dao.paginate(currentPage,Common.MAX_PAGE_SIZE,Common.COMMON_SELECT,TestQuestionnaireClass.SQL_FROM+Common.SQL_WHERE
                + "testQuestionnaireStartTime < ? and testQuestionnaireEndTime > ? and classId = ?", now, now, userClass.getId());
        List<TestQuestionnaire> list = testQuestionnaireClasses.getList().stream().map(this::packingQuestionnaire).collect(Collectors.toList());
        return PageinateKit.ClonePage(testQuestionnaireClasses,list);
    }
    public List<TestQuestionnaire> getNowQuestionnaireByUser(User user) {
        Class userClass = classService.getClassByStudent(studentService.getStudentByUser(user));
        long now = System.currentTimeMillis();
        List<TestQuestionnaireClass> testQuestionnaireClasses = TestQuestionnaireClass.dao.find(TestQuestionnaireClass.SEARCH_FROM_TEST_QUESTIONNAIRE_CLASS
                + "where testQuestionnaireStartTime < ? and testQuestionnaireEndTime > ? and classId = ?", now, now, userClass.getId());
        return testQuestionnaireClasses.stream().map(this::packingQuestionnaire).collect(Collectors.toList());
    }

    public TestQuestionnaire getQuestionnaireById(Integer id) {
        return TestQuestionnaire.dao.findById(id);
    }

    public List<TestQuestionnaire> getQuestionnairesByClass(Class aClass){
        List<TestQuestionnaireClass> tqcs = testQuestionnaireClassService.getByClassId(aClass);
        List<TestQuestionnaire> questionnaires = new ArrayList<>();
        for(TestQuestionnaireClass tqc : tqcs){
            TestQuestionnaire questionnaire = packingQuestionnaire(tqc);
            questionnaires.add(questionnaire);
        }
        return questionnaires;
    }
    public Page<TestQuestionnaire> getQuestionnairesByClass(int page, Class aClass){
        Page<TestQuestionnaireClass> tqcs = testQuestionnaireClassService.getByClassId(page, aClass);
        List<TestQuestionnaire> questionnaires = new ArrayList<>();
        for(TestQuestionnaireClass tqc : tqcs.getList()){
            TestQuestionnaire questionnaire = packingQuestionnaire(tqc);
            questionnaires.add(questionnaire);
        }
        return PageinateKit.ClonePage(tqcs, questionnaires);
    }
}
