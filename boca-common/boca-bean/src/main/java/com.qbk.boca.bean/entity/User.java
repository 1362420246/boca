package com.qbk.boca.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 6160110037574922696L;

    /**
     *   * 用户ID
     */
    private Integer id;

    /**
     *   * 登录名
     */
    private String username;

    /**
     *   * 密码
     */
    private String password;

    /**
     *   * shiro加密盐
     */
    private String salt;

    /**
     *   * 是否锁定
     */
    private Boolean isLock;

    /**
     *   * 是否删除
     */
    private Boolean isDel;

}