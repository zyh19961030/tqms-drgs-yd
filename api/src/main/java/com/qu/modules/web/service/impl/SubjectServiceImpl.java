package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.Qoption;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.mapper.OptionMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.param.QoptionParam;
import com.qu.modules.web.param.SubjectEditParam;
import com.qu.modules.web.param.SubjectParam;
import com.qu.modules.web.param.UpdateOrderNumParam;
import com.qu.modules.web.service.ISubjectService;
import com.qu.modules.web.vo.SubjectVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
@Slf4j
@Service
public class SubjectServiceImpl extends ServiceImpl<QsubjectMapper, Qsubject> implements ISubjectService {

    @Autowired
    private QsubjectMapper qsubjectMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Override
    public SubjectVo saveSubject(SubjectParam subjectParam) {
        SubjectVo subjectVo = new SubjectVo();
        Qsubject subject = new Qsubject();
        try {
            BeanUtils.copyProperties(subjectParam, subject);
            //计算题号
            Integer subSumCount = qsubjectMapper.selectSumCount(subjectParam.getQuId());
            subject.setOrderNum(subSumCount + 1);
            //如果是分组题，计算分组题号字段
            if (subjectParam.getSubType().equals("8")) {
                String[] gids = subjectParam.getGroupIds();
                StringBuffer groupIds = new StringBuffer();
                if (null != gids) {
                    for (String gid : gids) {
                        groupIds.append(gid);
                        groupIds.append(",");
                    }
                }
                subject.setGroupIds(groupIds.toString());
            }
            subject.setDel(0);
            subject.setCreater(1);
            subject.setCreateTime(new Date());
            subject.setUpdater(1);
            subject.setUpdateTime(new Date());
            qsubjectMapper.insert(subject);
            //拷贝到Vo对象
            BeanUtils.copyProperties(subject, subjectVo);
            //选项
            List<Qoption> optionList = new ArrayList<>();
            List<QoptionParam> optionParamList = subjectParam.getOptionParamList();
            if (null != optionParamList) {
                int i = 1;
                for (QoptionParam optionParam : optionParamList) {
                    Qoption option = new Qoption();
                    BeanUtils.copyProperties(optionParam, option);
                    option.setSubId(subject.getId());
                    option.setOpOrder(i);
                    option.setDel(0);
                    option.setCreater(1);
                    option.setCreateTime(new Date());
                    option.setUpdater(1);
                    option.setUpdateTime(new Date());
                    optionMapper.insert(option);
                    i++;
                    optionList.add(option);
                }
                subjectVo.setOptionList(optionList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return subjectVo;
    }

    @Override
    public SubjectVo updateQsubjectById(SubjectEditParam subjectEditParam) {
        SubjectVo subjectVo = new SubjectVo();
        Qsubject subject = new Qsubject();
        try {
            BeanUtils.copyProperties(subjectEditParam, subject);
            //如果是分组题，计算分组题号字段
            if (subjectEditParam.getSubType().equals("8")) {
                String[] gids = subjectEditParam.getGroupIds();
                StringBuffer groupIds = new StringBuffer();
                if (null != gids) {
                    for (String gid : gids) {
                        groupIds.append(gid);
                        groupIds.append(",");
                    }
                }
                subject.setGroupIds(groupIds.toString());
            }
            subject.setUpdater(1);
            subject.setUpdateTime(new Date());
            qsubjectMapper.updateById(subject);
            //拷贝到Vo对象
            BeanUtils.copyProperties(subject, subjectVo);
            //删除以前的所有选项
            int delCount = optionMapper.deleteOptionBySubId(subject.getId());
            //选项
            List<Qoption> optionList = new ArrayList<>();
            List<QoptionParam> optionParamList = subjectEditParam.getOptionParamList();
            if (null != optionParamList) {
                int i = 1;
                for (QoptionParam optionParam : optionParamList) {
                    Qoption option = new Qoption();
                    BeanUtils.copyProperties(optionParam, option);
                    option.setSubId(subject.getId());
                    option.setOpOrder(i);
                    option.setDel(0);
                    option.setCreater(1);
                    option.setCreateTime(new Date());
                    option.setUpdater(1);
                    option.setUpdateTime(new Date());
                    optionMapper.insert(option);
                    i++;
                    optionList.add(option);
                }
                subjectVo.setOptionList(optionList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return subjectVo;
    }

    @Override
    public Boolean removeSubjectById(Integer id) {
        Boolean delFlag = true;
        try {
            Qsubject subject = new Qsubject();
            subject.setId(id);
            subject.setDel(1);
            subject.setUpdater(1);
            subject.setUpdateTime(new Date());
            qsubjectMapper.updateById(subject);
        } catch (Exception e) {
            delFlag = false;
            log.error(e.getMessage(), e);
        }
        return delFlag;
    }

    @Override
    public Boolean updateOrderNum(UpdateOrderNumParam updateOrderNumParam) {
        Boolean flag = true;
        try {
            Qsubject qsubjecta = qsubjectMapper.selectById(updateOrderNumParam.getIda());
            Qsubject qsubjectb = qsubjectMapper.selectById(updateOrderNumParam.getIdb());
            int orderNuma = qsubjecta.getOrderNum();
            int orderNumb = qsubjectb.getOrderNum();
            qsubjecta.setOrderNum(orderNumb);
            qsubjectb.setOrderNum(orderNuma);
            qsubjectMapper.updateById(qsubjecta);
            qsubjectMapper.updateById(qsubjectb);
            //把分组的groupIds字段调换顺序
            int ida = qsubjecta.getId();
            int idb = qsubjectb.getId();
            Qsubject groupQsubject = qsubjectMapper.selectIdByGroupIdsLike(ida);
            if (groupQsubject != null) {
                Integer groupSubId = groupQsubject.getId();
                String groupIds = groupQsubject.getGroupIds();
                String[] gids = groupIds.split(",");
                int ida_index = 0;
                int idb_index = 0;
                for (int i = 0; i < gids.length; i++) {
                    String val = gids[i];
                    if (ida == Integer.parseInt(val)) {
                        ida_index = i;
                    }
                    if (idb == Integer.parseInt(val)) {
                        idb_index = i;
                    }
                }
                gids[ida_index] = String.valueOf(idb);
                gids[idb_index] = String.valueOf(ida);
                StringBuffer groupIdsNew = new StringBuffer();
                for (String id : gids) {
                    groupIdsNew.append(id);
                    groupIdsNew.append(",");
                }
                Qsubject qsubjectUpdate = new Qsubject();
                qsubjectUpdate.setId(groupSubId);
                qsubjectUpdate.setGroupIds(groupIdsNew.toString());
                qsubjectMapper.updateById(qsubjectUpdate);
            }
        } catch (Exception e) {
            flag = false;
            log.error(e.getMessage(), e);
        }
        return flag;
    }


}
