package com.chen.module_base.common.service.home.wrap;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.module_base.common.constant.RoutePath;
import com.chen.module_base.common.service.home.IHomeService;


public class HomeServiceWrapJava {

   @Autowired(name = RoutePath.Home.SERVICE_HOME)
    public IHomeService mService;

   private static final HomeServiceWrapJava instance;

   static {
       instance = Singletion.INSTANCE.getHolder();
       Log.d("HomeServiceWrap","HomeServiceWrap static");
   }

   public Fragment getFragment(){
       return mService.getFragment();
   }





   private HomeServiceWrapJava(){
       ARouter.getInstance().inject(this);
       Object navigation = ARouter.getInstance().build(RoutePath.Home.SERVICE_HOME).navigation();
       mService = (IHomeService) navigation;
//       mService = (IHomeService) ARouter.getInstance().build(RoutePath.Home.SERVICE_HOME).navigation();
   }

   public static final class Singletion{
       private static final HomeServiceWrapJava holder;
       public static final HomeServiceWrapJava.Singletion INSTANCE;

       public final HomeServiceWrapJava getHolder(){
           return holder;
       }
       private Singletion(){

       }

       static {
        Log.d("HomeServiceWrap","HomeServiceWrap Singletion static");
        Singletion singletion = new Singletion();
        INSTANCE = singletion;
        holder = new HomeServiceWrapJava();
       }
   }

}
