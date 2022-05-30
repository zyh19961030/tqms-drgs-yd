package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.TbUser;
import com.qu.modules.web.vo.SubjectVo;

import java.util.List;

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

    List<Qsubject> querySubjectByQuId(Integer id);

    Qsubject querySubjectById(Integer id);

}
