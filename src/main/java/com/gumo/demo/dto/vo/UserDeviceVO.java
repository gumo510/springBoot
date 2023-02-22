package com.gumo.demo.dto.vo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hy
 * @date 2022/3/10
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDeviceVO {

    private Integer id;

    private String userName;

    private String passWord;

    private String realName;

    private List<DeviceIdVO> deviceIds ;

}
