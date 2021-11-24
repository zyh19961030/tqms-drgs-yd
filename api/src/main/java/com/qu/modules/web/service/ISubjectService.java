package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.param.*;
import com.qu.modules.web.vo.SubjectVo;

import java.util.List;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
public interface ISubjectService extends IService<Qsubject> {

    SubjectVo saveSubject(SubjectParam subjectParam);

    SubjectVo insertSubject(InsertSubjectParam insertSubjectParam);

    SubjectVo updateQsubjectById(SubjectEditParam subjectEditParam);

    Boolean removeSubjectById(Integer id);

    Boolean updateOrderNum(UpdateOrderNumParam updateOrderNumParam);

    void editLogic(SubjectLogicParam subjectLogicParams);

    void editSpecialLogic(SubjectSpecialLogicParam subjectSpecialLogicParam);

    String querySubjectNmae(Integer subjectId);

    List<Qsubject> querySubjectByInput(String name);

    List<Qsubject> querySubjectByQuId(Integer id);

    Qsubject querySubjectById(Integer id);

}
