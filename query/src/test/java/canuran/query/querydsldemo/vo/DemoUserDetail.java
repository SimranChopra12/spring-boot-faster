package canuran.query.querydsldemo.vo;

import canuran.query.querydsldemo.entity.DemoUser;

/**
 * 用户详细信息。
 */
public class DemoUserDetail extends DemoUser {

    private String genderName;

    private String addressName;

    private String sameGender;

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getSameGender() {
        return sameGender;
    }

    public void setSameGender(String sameGender) {
        this.sameGender = sameGender;
    }
}

