package com.qu.modules.web.vo;

import com.qu.modules.web.entity.DrugRulesQuestion;
import com.qu.modules.web.entity.DrugRulesSubject;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SearchResultVo {
    @ApiModelProperty(value = "问卷")
    private DrugRulesQuestion drugRulesQuestion;

    @ApiModelProperty(value = "搜索出所属该问卷的问题集合")
    private List<DrugRulesSubject> drugRulesSubjectList;

    public DrugRulesQuestion getDrugRulesQuestion() {
        return drugRulesQuestion;
    }

    public void setDrugRulesQuestion(DrugRulesQuestion drugRulesQuestion) {
        this.drugRulesQuestion = drugRulesQuestion;
    }

    public List<DrugRulesSubject> getDrugRulesSubjectList() {
        return drugRulesSubjectList;
    }

    public void setDrugRulesSubjectList(List<DrugRulesSubject> drugRulesSubjectList) {
        this.drugRulesSubjectList = drugRulesSubjectList;
    }
}
