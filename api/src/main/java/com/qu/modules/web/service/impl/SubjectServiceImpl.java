package com.qu.modules.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.QsubjectConstant;
import com.qu.modules.web.entity.Qoption;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Qsubjectlib;
import com.qu.modules.web.mapper.OptionMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.param.InsertSubjectParam;
import com.qu.modules.web.param.LogicParam;
import com.qu.modules.web.param.QoptionParam;
import com.qu.modules.web.param.SubjectEditParam;
import com.qu.modules.web.param.SubjectLogicParam;
import com.qu.modules.web.param.SubjectParam;
import com.qu.modules.web.param.UpdateOrderNumParam;
import com.qu.modules.web.service.ISubjectService;
import com.qu.modules.web.vo.SubjectVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;

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

        String columnType = subjectParam.getColumnType();
        if (StringUtils.isNotBlank(columnType)) {
            subject.setColumnTypeDatabase(conversionColumnType(subjectParam.getColumnType()));
        }

        BeanUtils.copyProperties(subjectParam, subject);
        Map<String, Object> param = new HashMap<>();
        param.put("quId", subjectParam.getQuId());
        param.put("columnName", subjectParam.getColumnName());
        param.put("del", QsubjectConstant.DEL_NORMAL);
        int colCount = qsubjectMapper.selectColumnNameCount(param);
        if (colCount > 0) {//字段重复
            return null;
        }
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

        return subjectVo;
    }

    private String conversionColumnType(String columnType) {
        switch (columnType) {
            case "字符串":
            case "数组":
                return "varchar";
            case "数值":
                return "int";
            default:
                break;
        }
        return null;
    }

    @Override
    public SubjectVo insertSubject(InsertSubjectParam insertSubjectParam) {
        SubjectVo subjectVo = new SubjectVo();
        Qsubject subject = new Qsubject();

        String columnType = insertSubjectParam.getColumnType();
        if (StringUtils.isNotBlank(columnType)) {
            subject.setColumnTypeDatabase(conversionColumnType(insertSubjectParam.getColumnType()));
        }
        BeanUtils.copyProperties(insertSubjectParam, subject);
        Map<String, Object> param = new HashMap<>();
        param.put("quId", insertSubjectParam.getQuId());
        param.put("columnName", insertSubjectParam.getColumnName());
        param.put("del", QsubjectConstant.DEL_NORMAL);
        int colCount = qsubjectMapper.selectColumnNameCount(param);
        if (colCount > 0) {//字段重复
            return null;
        }

        //计算题号
        Integer nextOrderNum = qsubjectMapper.selectNextOrderNum(insertSubjectParam.getUpSubId());
        subject.setOrderNum(nextOrderNum + 1);

        //如果是分组题，计算分组题号字段
        if (insertSubjectParam.getSubType().equals("8")) {
            String[] gids = insertSubjectParam.getGroupIds();
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

        //把这道题以后的所有题的序号都加1
        Map<String, Object> insParam = new HashMap<>();
        insParam.put("quId", insertSubjectParam.getQuId());
        insParam.put("nextOrderNum", nextOrderNum + 1);
        insParam.put("subId", subject.getId());
        qsubjectMapper.updateNextOrderNum(insParam);

        //如果upSubId属于分组，把此题也插入分组中
        Map<String, Object> selectParam = new HashMap<>();
        selectParam.put("quId", insertSubjectParam.getQuId());
        selectParam.put("subId", insertSubjectParam.getUpSubId());
        Qsubject groupQsubject = qsubjectMapper.selectGroupQsubject(selectParam);
        if (groupQsubject != null) {
            String groupIds = groupQsubject.getGroupIds();
            String[] gids = groupIds.split(",");
            StringBuffer groupIdsNew = new StringBuffer();
            for (String gid : gids) {
                groupIdsNew.append(gid);
                groupIdsNew.append(",");
                if (Integer.parseInt(gid) == insertSubjectParam.getUpSubId()) {
                    groupIdsNew.append(subject.getId());
                    groupIdsNew.append(",");
                }
            }
            //更新数据库
            Qsubject updateQsubject = new Qsubject();
            updateQsubject.setId(groupQsubject.getId());
            updateQsubject.setGroupIds(groupIdsNew.toString());
            qsubjectMapper.updateById(updateQsubject);
        }

        //拷贝到Vo对象
        BeanUtils.copyProperties(subject, subjectVo);
        //选项
        List<Qoption> optionList = new ArrayList<>();
        List<QoptionParam> optionParamList = insertSubjectParam.getOptionParamList();
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

        return subjectVo;
    }

    @Override
    public SubjectVo updateQsubjectById(SubjectEditParam subjectEditParam) {
        SubjectVo subjectVo = new SubjectVo();
        Qsubject subject = new Qsubject();

        String columnType = subjectEditParam.getColumnType();
        if (StringUtils.isNotBlank(columnType)) {
            subject.setColumnTypeDatabase(conversionColumnType(subjectEditParam.getColumnType()));
        }
        BeanUtils.copyProperties(subjectEditParam, subject);
        //如果是分组题，计算分组题号字段
        if (subjectEditParam.getSubType() != null) {
            Map<String, String> existCache = new HashMap<>();
            if (subjectEditParam.getSubType().equals("8")) {
                String[] gids = subjectEditParam.getGroupIds();
                StringBuffer groupIds = new StringBuffer();
                if (null != gids) {
                    for (String gid : gids) {
                        groupIds.append(gid);
                        groupIds.append(",");
                        existCache.put(gid, "1");
                    }
                }
                subject.setGroupIds(groupIds.toString());
            }
            //如果其他分组题中groupIds字段包含此题中的groupIds,删除
            //查询此问卷下所有的分组题
            Map<String, Object> groupParam = new HashMap();
            groupParam.put("quId", subjectEditParam.getQuId());
            groupParam.put("subId", subjectEditParam.getId());
            List<Qsubject> qsubjectList = qsubjectMapper.selectGroupQsubjectByQuId(groupParam);
            for (Qsubject qsubject : qsubjectList) {
                StringBuffer groupIdsUpdate = new StringBuffer();
                String groupIdsDB = qsubject.getGroupIds();
                if (groupIdsDB != null && groupIdsDB.length() > 0) {
                    String[] gidsdb = groupIdsDB.split(",");
                    for (String giddb : gidsdb) {
                        if (existCache.get(giddb) == null) {
                            groupIdsUpdate.append(giddb);
                            groupIdsUpdate.append(",");
                        }
                    }
                    Qsubject qsubjectUpdate = new Qsubject();
                    qsubjectUpdate.setId(qsubject.getId());
                    qsubjectUpdate.setGroupIds(groupIdsUpdate.toString());
                    qsubjectMapper.updateById(qsubjectUpdate);
                }
            }
        }
        subject.setUpdater(1);
        subject.setUpdateTime(new Date());
        qsubjectMapper.updateById(subject);
        //拷贝到Vo对象
        BeanUtils.copyProperties(subject, subjectVo);
        //删除以前的所有选项
        //int delCount = optionMapper.deleteOptionBySubId(subject.getId());
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
                if (option.getId() != null && option.getId() != 0) {
                    optionMapper.updateById(option);
                } else {
                    optionMapper.insert(option);
                }
                i++;
                optionList.add(option);
            }
            //删除应该删除的选项
            List<Integer> optionDeleteList = subjectEditParam.getOptionDeleteList();
            if(optionDeleteList!=null && !optionDeleteList.isEmpty()){
                optionMapper.deleteBatchIds(optionDeleteList);
            }
            subjectVo.setOptionList(optionList);
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
            //如果此题在分组中，删除分组题中的groupIds
            Qsubject groupQsubject = qsubjectMapper.selectIdByGroupIdsLike(id);
            if (groupQsubject != null) {
                Integer groupSubId = groupQsubject.getId();
                String groupIds = groupQsubject.getGroupIds();
                String[] gids = groupIds.split(",");
                StringBuffer groupIdsNew = new StringBuffer();
                for (String gid : gids) {
                    if (id != Integer.parseInt(gid)) {
                        groupIdsNew.append(gid);
                        groupIdsNew.append(",");
                    }
                }
                Qsubject qsubjectUpdate = new Qsubject();
                qsubjectUpdate.setId(groupSubId);
                qsubjectUpdate.setGroupIds(groupIdsNew.toString());
                qsubjectMapper.updateById(qsubjectUpdate);
            }
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

    @Override
    public void editLogic(SubjectLogicParam subjectLogicParam) {
        List<LogicParam> subjectLogicList = subjectLogicParam.getSubjectLogicList();
        subjectLogicList.forEach(s->{
            Integer id = s.getId();
            String jumpLogic = s.getJumpLogic();
            Qsubject byId = this.getById(id);
            if(byId!=null){
                byId.setJumpLogic(jumpLogic);
                this.updateById(byId);
            }
        });

        List<LogicParam> optionLogicList = subjectLogicParam.getOptionLogicList();
        optionLogicList.forEach(o->{
            Integer id = o.getId();
            String jumpLogic = o.getJumpLogic();
            Qoption qoption = optionMapper.selectById(id);
            if(qoption!=null){
                qoption.setJumpLogic(jumpLogic);
                optionMapper.updateById(qoption);
            }
        });
    }

    @Override
    public String querySubjectNmae(Integer subjectId) {
        String name = qsubjectMapper.querySubjectNmae(subjectId);
        return name;
    }

    @Override
    public List<Qsubject> querySubjectByInput(String name) {
        List<Qsubject> qsubjects = qsubjectMapper.querySubjectByInput(name);
        return qsubjects;
    }

    @Override
    public List<Qsubject> querySubjectByQuId(Integer id) {
        LambdaQueryWrapper<Qsubject> lambda = new QueryWrapper<Qsubject>().lambda();
        lambda.eq(Qsubject::getQuId, id);
        lambda.eq(Qsubject::getDel, 0);
        List<Qsubject> list = this.list(lambda);
        return list;
    }
}
