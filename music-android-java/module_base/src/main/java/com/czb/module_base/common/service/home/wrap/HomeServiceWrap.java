package com.czb.module_base.common.service.home.wrap;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.czb.module_base.RoutePath;
import com.czb.module_base.common.service.home.IHomeService;
import com.czb.module_base.common.service.moyu.wrap.MoyuServiceWrap;


public class HomeServiceWrap {

   @Autowired(name = RoutePath.Home.SERVICE_HOME)
    public IHomeService mService;

   private static final HomeServiceWrap instance;

   static {
       instance = HomeServiceWrap.Singletion.INSTANCE.getHolder();
       Log.d("HomeServiceWrap","HomeServiceWrap static");
   }

   public Fragment getFragment(){
       Log.d("HomeServiceWrap","tess");
       return mService.getFragment();
   }



   private HomeServiceWrap(){
       ARouter.getInstance().inject(this);
       Object navigation = ARouter.getInstance().build(RoutePath.Home.SERVICE_HOME).navigation();
       mService = (IHomeService) navigation;
//       mService = (IHomeService) ARouter.getInstance().build(RoutePath.Home.SERVICE_HOME).navigation();
   }

   public static final class Singletion{
       private static final HomeServiceWrap holder;
       public static final HomeServiceWrap.Singletion INSTANCE;

       public final HomeServiceWrap getHolder(){
           return holder;
       }
       private Singletion(){

       }

       static {
        Log.d("HomeServiceWrap","HomeServiceWrap Singletion static");
        Singletion singletion = new Singletion();
        INSTANCE = singletion;
        holder = new HomeServiceWrap();
       }
   }

}
