package com.czb.module_base.base;

public interface IBasePresenter<T> {

    /**
     * 注册ui通知接口
     * @param callback
     */
    void registerViewCallback(T callback);

    /**
     * 取消注册
     */
    void unregisterViewCallback(T callback);

}
