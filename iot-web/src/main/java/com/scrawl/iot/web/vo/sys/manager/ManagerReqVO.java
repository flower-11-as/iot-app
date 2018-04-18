package com.scrawl.iot.web.vo.sys.manager;

import com.scrawl.iot.web.dao.entity.Manager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/15.
 */
@Setter
@Getter
public class ManagerReqVO extends Manager {
    private List<Integer> roles;
}
