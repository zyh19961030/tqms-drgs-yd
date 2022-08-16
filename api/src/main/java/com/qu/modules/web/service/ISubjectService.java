package com.qu.modules.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.param.InsertSubjectParam;
import com.qu.modules.web.param.StatisticsCheckTableParam;
import com.qu.modules.web.param.SubjectEditParam;
import com.qu.modules.web.param.SubjectLogicParam;
import com.qu.modules.web.param.SubjectParam;
import com.qu.modules.web.param.SubjectQuantityStatisticsParam;
import com.qu.modules.web.param.SubjectSpecialLogicParam;
import com.qu.modules.web.param.UpdateOrderNumParam;
import com.qu.modules.web.pojo.TbUser;
import com.qu.modules.web.vo.StatisticsCheckTableSubjectVo;
import com.qu.modules.web.vo.SubjectVo;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
public interface ISubjectService extends IService<Qsubject> {

    SubjectVo saveSubject(SubjectParam subjectParam, TbUser tbUser);

    SubjectVo insertSubject(InsertSubjectParam insertSubjectParam, TbUser tbUser);

    SubjectVo updateQsubjectById(SubjectEditParam subjectEditParam, TbUser tbUser);

    Boolean removeSubjectById(Integer id, TbUser tbUser);

    Boolean updateOrderNum(UpdateOrderNumParam updateOrderNumParam);

    void editLogic(SubjectLogicParam subjectLogicParams);

    void editSpecialLogic(SubjectSpecialLogicParam subjectSpecialLogicParam);

    String querySubjectNmae(Integer subjectId);

    List<Qsubject> querySubjectByInput(String name);

    List<Qsubject> querySubjectByQuantityStatistics(SubjectQuantityStatisticsParam subjectQuantityStatisticsParam);

    List<Qsubject> querySubjectByScoreCount(SubjectQuantityStatisticsParam subjectQuantityStatisticsParam);

    List<Qsubject> querySubjectByResultEvaluate(SubjectQuantityStatisticsParam subjectQuantityStatisticsParam);

    List<Qsubject> querySubjectByQuId(Integer id);

    Qsubject querySubjectById(Integer id);

    List<StatisticsCheckTableSubjectVo> statisticsCheckTable(StatisticsCheckTableParam statisticsCheckTableParam);

    /**
     * 查询题目和选项根据问卷id
     * @param questionId
     * @return
     */
    List<SubjectVo> selectSubjectAndOptionByQuId(Integer questionId);

}
