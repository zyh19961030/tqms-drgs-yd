package com.qu.modules.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.entity.TbDep;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.TbUser;
import com.qu.modules.web.vo.*;

import java.util.List;

/**
 * @Description: 问卷表
 * @Author: jeecg-boot
 * @Date:   2021-03-19
 * @Version: V1.0
 */
public interface IQuestionService extends IService<Question> {

    Question saveQuestion(QuestionParam questionParam, TbUser tbUser);

    QuestionVo queryById(Integer id);

    List<ViewNameVo> queryByViewName(String viewName);

    Question updateQuestionById(QuestionEditParam questionEditParam, TbUser tbUser);

    boolean removeQuestionById(Integer id, TbUser tbUser);

    QuestionAndCategoryPageVo queryPageList(QuestionParam questionParam, Integer pageNo, Integer pageSize);

    QuestionPageVo questionFillInList(QuestionParam questionParam, Integer pageNo, Integer pageSize);

    IPage<QuestionCheckVo> checkQuestionList(QuestionCheckParam questionParam, Integer pageNo, Integer pageSize);

    List<QuestionStatisticsCheckVo> statisticsCheckList(QuestionCheckParam questionParam);

    void updateDeptIdsParam(UpdateDeptIdsParam updateDeptIdsParam);

    void updateSeeDeptIdsParam(UpdateDeptIdsParam updateDeptIdsParam);

    QuestionVo queryPersonById(Integer id);

    void updateCategoryIdParam(UpdateCategoryIdParam updateCategoryIdParam);

    Boolean againRelease(QuestionAgainReleaseParam questionAgainreleaseParam);

    List<Question> queryQuestionByInput(String name);

    void updateWriteFrequencyIdsParam(UpdateWriteFrequencyIdsParam updateWriteFrequencyIdsParam);

    List<QuestionPatientCreateListVo> patientCreateList(String name, String deptId);

    List<QuestionMonthQuarterYearCreateListVo> monthQuarterYearCreateList(String type, String deptId);

    List<TbDep> singleDiseaseStatisticAnalysisByDeptCondition(QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam, String deptId, String type);

    List<String> selectSingleDiseaseDeptIdList(String categoryId);


    IPage<QuestionMiniAppPageVo> queryPageListByMiniApp(String deptId, Integer pageNo, Integer pageSize);

    /**
     * CheckDetailSet检查表设置列时使用
     * @param id
     * @return
     */
    List<SubjectVo> queryQuestionSubjectById(Integer id);

}
