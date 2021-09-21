package com.qu.modules.web.vo;

import io.swagger.annotations.ApiModelProperty;

public class SubjectIdAndMatchesVo {
    @ApiModelProperty(value = "答案id")
    private Integer subjectId;

    @ApiModelProperty(value = "匹配规则标识")
    private Integer matches;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }
}
