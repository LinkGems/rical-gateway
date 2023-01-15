package com.wtrue.rical.gateway.domain.adapter;

import com.wtrue.rical.gateway.domain.dto.UserBaseDTO;
import com.wtrue.rical.gateway.domain.po.user.UserBase;
import com.wtrue.rical.gateway.export.pojo.UserBaseModel;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/11/28 7:11 PM
 */
public class UserAdapter {

    public static UserBaseDTO po2dto(UserBase userBase) {
        if (userBase == null) {
            return null;
        }
        UserBaseDTO userBaseDTO = new UserBaseDTO();
        userBaseDTO.setUserId(userBase.getUserId());
        userBaseDTO.setUserName(userBase.getUserName());
        userBaseDTO.setSex(userBase.getSex());
        userBaseDTO.setIdType(userBase.getIdType());
        userBaseDTO.setIdNum(userBase.getIdNum());
        return userBaseDTO;
    }

    public static UserBaseDTO mo2dto(UserBaseModel userBaseModel) {
        if (userBaseModel == null) {
            return null;
        }
        UserBaseDTO userBaseDTO = new UserBaseDTO();
        userBaseDTO.setUserId(userBaseModel.getUserId());
        userBaseDTO.setUserName(userBaseModel.getUserName());
        userBaseDTO.setSex(userBaseModel.getSex());
        userBaseDTO.setIdType(userBaseModel.getIdType());
        userBaseDTO.setIdNum(userBaseModel.getIdNum());
        return userBaseDTO;
    }

    public static UserBaseModel dto2mo(UserBaseDTO userBaseDTO) {
        if (userBaseDTO == null) {
            return null;
        }
        UserBaseModel userBaseModel = new UserBaseModel();
        userBaseModel.setUserId(userBaseDTO.getUserId());
        userBaseModel.setUserName(userBaseDTO.getUserName());
        userBaseModel.setSex(userBaseDTO.getSex());
        userBaseModel.setIdType(userBaseDTO.getIdType());
        userBaseModel.setIdNum(userBaseDTO.getIdNum());
        return userBaseModel;
    }
}
