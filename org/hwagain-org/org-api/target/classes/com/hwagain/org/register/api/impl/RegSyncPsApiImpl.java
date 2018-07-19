package com.hwagain.org.register.api.impl;

import com.hwagain.org.register.api.IRegSyncPsApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-07-02
 */
@Service("regSyncPsApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegSyncPsApiImpl implements IRegSyncPsApi {
	
}
