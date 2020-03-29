package com.unibz.cockpit.model;

import java.io.Serializable;
import java.util.Date;
public class Project implements Serializable{
        private Integer projectId;
        String projectName;
        String projectDesc;
        String projectAcronym;
        Date startDate;
        Date endDate;
        Integer budget;
        Date isUpdated;


        public Project() {
            super();
        }
        public Integer getProjectId() {
            return projectId;
        }
        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }
        public String getProjectName() {
            return projectName;
        }
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
        public String getProjectDesc() {
            return projectDesc;
        }
        public void setProjectDesc(String projectDesc) {
            this.projectDesc = projectDesc;
        }
        public String getProjectAcronym() {
            return projectAcronym;
        }
        public void setProjectAcronym(String projectAcronym) {
            this.projectAcronym = projectAcronym;
        }
        public Date getStartDate() {
            return startDate;
        }
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }
        public Date getEndDate() {
            return endDate;
        }
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
        public Integer getBudget() {
            return budget;
        }
        public void setBudget(Integer budget) {
            this.budget = budget;
        }
        public Date getIsUpdated() {
            return isUpdated;
        }
        public void setIsUpdated(Date isUpdated) {
            this.isUpdated = isUpdated;
        }

}
