package com.gumo.demo.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;


/**
 * @author gumo
 */
@Data
@AllArgsConstructor
public class DeviceIdVO {
    String id;
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceIdVO that = (DeviceIdVO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
