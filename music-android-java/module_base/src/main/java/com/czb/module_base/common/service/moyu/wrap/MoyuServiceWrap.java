package com.czb.module_base.common.service.moyu.wrap;

import android.os.Parcelable;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.czb.module_base.RoutePath;
import com.czb.module_base.common.Constants;
import com.czb.module_base.common.service.moyu.IMoyuService;



public class MoyuServiceWrap {

    @Autowired(name = RoutePath.Moyu.SERVICE_MOYU)
    public IMoyuService mMoyuService;

    private static final MoyuServiceWrap instance;

    static {
        instance = MoyuServiceWrap.Singletion.INSTANCE.getHolder();

        Log.d("MoyuServiceWrap","mMoyuService static");
    }

    public Fragment getFragment(){
        Log.d("HomeServiceWrap","tess");

        return mMoyuService.getFragment();
    }


    private MoyuServiceWrap(){
        ARouter.getInstance().inject(this);
        Object navigation = ARouter.getInstance().build(RoutePath.Moyu.SERVICE_MOYU).navigation();
        mMoyuService = (IMoyuService) navigation;
    }

    public void launchComment(String songId) {
        ARouter.getInstance()
                .build(RoutePath.Moyu.DETAIL_MUSIC_COMMENT)
                .withString(RoutePath.Moyu.MUSIC_ID,songId)
                .navigation();
    }

    public void launchSubComment(Parcelable commentParent) {
        ARouter.getInstance()
                .build(RoutePath.Moyu.DETAIL_MUSIC_SUB_COMMENT)
                .withParcelable(RoutePath.Moyu.COMMENT,commentParent)
                .navigation();
    }

    public static final class Singletion{
        private static final MoyuServiceWrap holder;
        public static final Singletion INSTANCE;
        public final MoyuServiceWrap getHolder(){
            return holder;
        }
        private Singletion(){

        }
        static {
            Log.d("HomeServiceWrap","HomeServiceWrap Singletion static");
            Singletion singletion =new Singletion();
            INSTANCE = singletion;
            holder = new MoyuServiceWrap();
        }
    }






}
