package com.mxkj.econtrol.net;

import java.util.Objects;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class APIException extends RuntimeException {
    public APIException(Object object,String msg) {
        super(msg);
    }
}
