package com.qu.modules.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.entity.TbDep;
import com.qu.modules.web.param.QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam;
import com.qu.modules.web.param.QuestionAgainReleaseParam;
import com.qu.modules.web.param.QuestionEditParam;
import com.qu.modules.web.param.QuestionParam;
import com.qu.modules.web.param.UpdateCategoryIdParam;
import com.qu.modules.web.param.UpdateDeptIdsParam;
import com.qu.modules.web.param.UpdateWriteFrequencyIdsParam;
import com.qu.modules.web.pojo.TbUser;
import com.qu.modules.web.vo.QuestionAndCategoryPageVo;
import com.qu.modules.web.vo.QuestionMiniAppPageVo;
import com.qu.modules.web.vo.QuestionMonthQuarterYearCreateListVo;
import com.qu.modules.web.vo.QuestionPageVo;
import com.qu.modules.web.vo.QuestionPatientCreateListVo;
import com.qu.modules.web.vo.QuestionVo;
import com.qu.modules.web.vo.ViewNameVo;

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

}
