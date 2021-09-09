package com.xe.core.job;


import com.xe.core.controller.BaseController;
import com.xe.core.entity.DbUser;
import com.xe.core.service.DbUserService;
import com.xe.core.util.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
     仅用于本地测试
* */
@Component
@ResponseBody
@RequestMapping("/xxjob")
public class LocalTest extends BaseController {

    private Logger logger = LoggerFactory.getLogger(com.xe.core.job.LocalTest.class);

    @Autowired
    DbUserService dbUserService;

    @RequestMapping("/test")
    public ResultInfo test() {
        logger.info("kaishi");

        String[] tables = {"db_user"};
        String sql = "";
        for (int i = 0; i < tables.length; i++) {
            String tnm = tables[i];
            sql = "SELECT id,username,password FROM " + tnm + "";
            List<DbUser> mainList = dbUserService.selectSqls(sql);
            List<DbUser> thlist = new ArrayList<>();
            ExecutorService es = Executors.newFixedThreadPool(15);
            for (DbUser dbUser : mainList) {
                thlist.add(dbUser);
                if (thlist.size() == 200) {
                    Esupdate(es, tnm, thlist);
                    thlist = new ArrayList<>();
                }

            }
            if (thlist.size() > 0) {
                Esupdate(es, tnm, thlist);
            }
            es.shutdown();//此时线程池不会接受新任务，但是不会立刻退出，等待任务执行完毕
            //接下来 判断
            while (true) {
                if (es.isTerminated()) {
                    System.out.println("一个线程池执行完毕");
                    break;
                }
            }
        }
        try {


        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return renderSuccess("ok~");
    }

    private void Esupdate(ExecutorService es, final String tbname, final List<DbUser> mainList) {
        es.execute(new Runnable() {
                       @Override
                       public void run() {
                           System.out.println(mainList.size());
                           for (int i = 0; i < mainList.size(); i++) {
                                System.out.println(mainList.get(i).getUsername());
                           }
                       }
                   }
        );
    }


}
