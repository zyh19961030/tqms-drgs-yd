package com.qu.modules.web.vo;

import com.qu.modules.web.entity.DrugRulesQuestion;
import com.qu.modules.web.entity.DrugRulesSubject;

public class inputVo {
    private DrugRulesQuestion drugRulesQuestion;

    public DrugRulesQuestion getDrugRulesQuestion() {
        return drugRulesQuestion;
    }

    public void setDrugRulesQuestion(DrugRulesQuestion drugRulesQuestion) {
        this.drugRulesQuestion = drugRulesQuestion;
    }

    private DrugRulesSubject drugRulesSubject;

    public DrugRulesSubject getDrugRulesSubject() {
        return drugRulesSubject;
    }

    public void setDrugRulesSubject(DrugRulesSubject drugRulesSubject) {
        this.drugRulesSubject = drugRulesSubject;
    }
}
