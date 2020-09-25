package com.hdax.ssm.entity;

import lombok.Data;

@Data
public class Permission {

  private Integer permissionId;
  private String pName;
  private String type;
  private String url;
  private Integer parentid;



    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", pName='" + pName + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", parentid=" + parentid +
                '}';
    }
}
