package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;
import com.bjpowernode.crm.workbench.bean.Tran;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ClueService {
    List<Clue> list(int page, int pageSize);

    void addClue(Clue clue,HttpSession session);

    List<Activity> clueActivityList(String id);

    List<Activity> queryClueActivityList(String id, String name);

    ResultVo bind(String ids, String id);

    void unbind(ClueActivityRelation clueActivityRelation);

    Clue clueList(String id);

    void convert(String id, User user, String isCreateTransaction, Tran tran);

    List<Activity> queryActivity(String id, String name);
}
