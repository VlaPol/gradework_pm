package by.tms.gradework_pm.entity;

import lombok.Getter;

@Getter
public enum Stage {
    NOTSTARTED("Project not started"),
    COMPLETED("Project complit"),
    INPROGRESS("Project in progress");

    private final String displayStage;

    Stage(String displayStage) {
        this.displayStage = displayStage;
    }

}
