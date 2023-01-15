package com.wtrue.rical.gateway.provider;

import com.wtrue.rical.gateway.biz.IUserBiz;
import com.wtrue.rical.gateway.domain.adapter.UserAdapter;
import com.wtrue.rical.gateway.domain.dto.UserBaseDTO;
import com.wtrue.rical.gateway.export.pojo.UserBaseModel;
import com.wtrue.rical.gateway.export.provider.IUserProvider;
import com.wtrue.rical.common.domain.BaseResponse;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/7/18 4:51 PM
 */
@Service(version = "${provider.wtrue.rical.backend.ITestProvider}")
public class UserProvider implements IUserProvider {

    @Resource
    private IUserBiz userBiz;

    @Override
    public BaseResponse<UserBaseModel> queryUser(Long userId) {
        UserBaseDTO userBaseDTO = userBiz.queryUser(userId);
        UserBaseModel userBaseModel = UserAdapter.dto2mo(userBaseDTO);
        if(userBaseModel != null){
            return BaseResponse.success(userBaseModel);
        }else{
            return BaseResponse.failure();
        }
    }
}
