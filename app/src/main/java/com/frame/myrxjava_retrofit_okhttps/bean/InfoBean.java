package com.frame.myrxjava_retrofit_okhttps.bean;


public class InfoBean extends BaseReply{

    private DataBean Data;

    public DataBean getData() {
        return Data;
    }
 
    public void setData(DataBean data) {
        Data = data;
    }

    public static class DataBean {

        private int Id;
        private String Code;
        private int CaseSourceId;
        private String CaseSourceName;
        private int CaseTypeId;
        private String CaseTypeName;
        private int CaseMainTypeId;
        private String CaseMainTypeName;
        private int CaseSubTypeId;
        private String CaseSubTypeName;
        private int GridId;
        private String GridName;
        private int CommunityId;
        private String CommunityName;
        private String Address;
        private String Description;
        private double Longitude;
        private double Latitude;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public int getCaseSourceId() {
            return CaseSourceId;
        }

        public void setCaseSourceId(int caseSourceId) {
            CaseSourceId = caseSourceId;
        }

        public String getCaseSourceName() {
            return CaseSourceName;
        }

        public void setCaseSourceName(String caseSourceName) {
            CaseSourceName = caseSourceName;
        }

        public int getCaseTypeId() {
            return CaseTypeId;
        }

        public void setCaseTypeId(int caseTypeId) {
            CaseTypeId = caseTypeId;
        }

        public String getCaseTypeName() {
            return CaseTypeName;
        }

        public void setCaseTypeName(String caseTypeName) {
            CaseTypeName = caseTypeName;
        }

        public int getCaseMainTypeId() {
            return CaseMainTypeId;
        }

        public void setCaseMainTypeId(int caseMainTypeId) {
            CaseMainTypeId = caseMainTypeId;
        }

        public String getCaseMainTypeName() {
            return CaseMainTypeName;
        }

        public void setCaseMainTypeName(String caseMainTypeName) {
            CaseMainTypeName = caseMainTypeName;
        }

        public int getCaseSubTypeId() {
            return CaseSubTypeId;
        }

        public void setCaseSubTypeId(int caseSubTypeId) {
            CaseSubTypeId = caseSubTypeId;
        }

        public String getCaseSubTypeName() {
            return CaseSubTypeName;
        }

        public void setCaseSubTypeName(String caseSubTypeName) {
            CaseSubTypeName = caseSubTypeName;
        }

        public int getGridId() {
            return GridId;
        }

        public void setGridId(int gridId) {
            GridId = gridId;
        }

        public String getGridName() {
            return GridName;
        }

        public void setGridName(String gridName) {
            GridName = gridName;
        }

        public int getCommunityId() {
            return CommunityId;
        }

        public void setCommunityId(int communityId) {
            CommunityId = communityId;
        }

        public String getCommunityName() {
            return CommunityName;
        }

        public void setCommunityName(String communityName) {
            CommunityName = communityName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }
    }
}
