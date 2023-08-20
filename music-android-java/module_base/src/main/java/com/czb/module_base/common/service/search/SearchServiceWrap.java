package com.czb.module_base.common.service.search;

import com.alibaba.android.arouter.launcher.ARouter;
import com.czb.module_base.RoutePath;


public class SearchServiceWrap {

    private static final SearchServiceWrap instance;

    static  {
        instance = SearchServiceWrap.Singletion.INSTANCE.getHolder();
    }


    public void launchSearch(){
        ARouter.getInstance()
                .build(RoutePath.Search.PAGE_SEARCH)
                .navigation();
    }



    public static final class Singletion{
        private static final SearchServiceWrap holder;
        public static final SearchServiceWrap.Singletion INSTANCE;
        public final SearchServiceWrap getHolder(){
            return holder;
        }

        private Singletion(){

        }

        static {
            SearchServiceWrap.Singletion singletion = new SearchServiceWrap.Singletion();
            INSTANCE = singletion;
            holder = new SearchServiceWrap();
        }

    }

}
