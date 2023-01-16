package com.smallbuilding.smallbuilding.model;

/**
 * CommonRoom inherits the fields and methods from Room.
 * Besides, CommonRoom has an additional field for its type, which is an enum.
 */
public class CommonRoom extends Room {
    private CommonRoomType commonRoomType;

    public CommonRoom() {
        super();
    }

    public CommonRoomType getCommonRoomType() {
        return commonRoomType;
    }

    public void setCommonRoomType(CommonRoomType commonRoomType) {
        this.commonRoomType = commonRoomType;
    }
}
