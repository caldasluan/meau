package com.dap.meau.Model;

import java.io.Serializable;

public class PetUserInterestModel implements Serializable {
    private String ptid, uid;

    public PetUserInterestModel() {
    }

    public PetUserInterestModel(String ptid, String uid) {
        this.ptid = ptid;
        this.uid = uid;
    }

    public String getPtid() {
        return ptid;
    }

    public void setPtid(String ptid) {
        this.ptid = ptid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
