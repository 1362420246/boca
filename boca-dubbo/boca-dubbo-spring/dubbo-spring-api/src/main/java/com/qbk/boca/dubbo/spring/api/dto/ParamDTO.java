package com.qbk.boca.dubbo.spring.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ParamDTO implements Serializable {

    private static final long serialVersionUID = 3524248999450427852L;

    @NotNull(message = "名称不能为空")
    private String name;
}
