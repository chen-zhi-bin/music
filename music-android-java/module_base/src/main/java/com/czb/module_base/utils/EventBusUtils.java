package com.czb.module_base.utils;


import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    public static final EventBusUtils INSTANCE;
    static {
        INSTANCE = new EventBusUtils();
    }

    public boolean isRegistered(Object object){
        return EventBus.getDefault().isRegistered(object);
    }
    /**
     * 订阅
     * @param subscriber 订阅者
     */
    public void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除注册
     * @param subscriber 订阅者
     */
    public void unRegister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     * @param event 事件
     */
    public void postEvent(Object event){
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件
     * @param stickyEvent 粘性事件
     */
    public void postStickyEvent(Object stickyEvent){
        EventBus.getDefault().postSticky(stickyEvent);
    }

    /**
     * 手动获取粘性事件
     * @param stickyEventType 粘性事件
     * @return 返回给定事件类型的最近粘性事件
     */
    public Object getStickyEvent(Class stickyEventType){
        return EventBus.getDefault().getStickyEvent(stickyEventType);
    }

    /**
     * 手动删除粘性事件
     * @param stickyEventType 粘性事件
     * @return 返回给定事件类型的最近粘性事件
     */
    public final Object removeStickyEvent(Class stickyEventType) {
        return EventBus.getDefault().removeStickyEvent(stickyEventType);
    }
}
