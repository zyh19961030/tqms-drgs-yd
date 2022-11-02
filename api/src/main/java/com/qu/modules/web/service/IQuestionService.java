package com.qu.modules.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.entity.TbDep;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.Data;
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

    QuestionVo singleDiseaseQueryById(QuestionQueryByIdParam param);

    List<ViewNameVo> queryByViewName(QuestionCheckedDepParam viewName);

    Question updateQuestionById(QuestionEditParam questionEditParam, TbUser tbUser);

    boolean removeQuestionById(Integer id, TbUser tbUser);

    QuestionAndCategoryPageVo queryPageList(QuestionParam questionParam, Integer pageNo, Integer pageSize);

    QuestionPageVo questionFillInList(QuestionParam questionParam, Integer pageNo, Integer pageSize);

    IPage<QuestionCheckVo> checkQuestionList(QuestionCheckParam questionParam, Integer pageNo, Integer pageSize,Data data);

    List<QuestionStatisticsCheckVo> statisticsCheckList(QuestionCheckParam questionParam);

    List<CheckQuestionHistoryStatisticVo> checkQuestionHistoryStatisticList(Data data);

    /**
     * 检查管理_参数设置列表
     * @param deptId
     * @return
     */
    List<CheckQuestionParameterSetListVo> checkQuestionParameterSetList(String deptId);

    /**
     * 检查管理_历史统计列表_上级督查_填报记录和检查明细_被检查科室筛选条件
     * @param deptListParam
     * @param data
     * @return
     */
    List<CheckQuestionHistoryStatisticDeptListDeptVo> checkQuestionHistoryStatisticInspectedDeptList(CheckQuestionHistoryStatisticDeptListParam deptListParam, Data data);

    /**
     * 检查管理_历史统计列表_上级督查_填报记录和检查明细_检查科室筛选条件
     * @param deptListParam
     * @param data
     * @return
     */
    List<CheckQuestionHistoryStatisticDeptListDeptVo> checkQuestionHistoryStatisticDeptList(CheckQuestionHistoryStatisticDeptListParam deptListParam, Data data);

    /**
     * 检查管理_历史统计列表_科室自查_填报记录和检查明细_自查科室筛选条件
     * @param deptListParam
     * @param data
     * @return
     */
    List<CheckQuestionHistoryStatisticDeptListDeptVo> checkQuestionHistoryStatisticSelfDeptList(CheckQuestionHistoryStatisticDeptListParam deptListParam, Data data);

    void updateDeptIdsParam(UpdateDeptIdsParam updateDeptIdsParam);

    void updateSeeDeptIdsParam(UpdateDeptIdsParam updateDeptIdsParam);

    void updateCheckedDeptIdsParam(UpdateCheckedDeptIdsParam updateCheckedDeptIdsParam);

    List<String> selectCheckedDeptIdsParam(SelectCheckedDeptIdsParam selectCheckedDeptIdsParam);

    void updateResponsibilityUserIdsParam(UpdateResponsibilityUserIdsParam param);

    List<String> selectResponsibilityUserIdsParam(SelectResponsibilityUserIdsParam param);

    QuestionVo queryPersonById(Integer id);

    void updateCategoryIdParam(UpdateCategoryIdParam updateCategoryIdParam);

    void updateTemplateIdIdParam(UpdateTemplateIdParam param);

    Boolean againRelease(QuestionAgainReleaseParam questionAgainreleaseParam);

    Boolean generateTraceability(String id);

    List<Question> queryQuestionByInput(String name);

    void updateWriteFrequencyIdsParam(UpdateWriteFrequencyIdsParam updateWriteFrequencyIdsParam);

    void updateQuestionIcon(UpdateQuestionIconParam updateQuestionIconParam);

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
