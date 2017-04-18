package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.mobileButler.MBackupRecord;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.Map;

/**
 * Created by yxm on 2016/9/9.
 */
public interface MobileButlerMapper extends SqlMapper
{
    UserVO selUserInfo(Map map) throws Exception;//查询用户信息

    void saveContacts(Map map) throws Exception;//批量保存通讯录

    void saveSms(Map map) throws Exception;//批量保存短信记录

    MBackupRecord selBackupRecord(Map map) throws Exception;//查询备份记录

    void delContacts(Map map) throws Exception;//删除通讯录备份

    void delSmss(Map map) throws Exception;//删除短信备份

    void updateBackupRecord(MBackupRecord mBackupRecord) throws Exception;//更新备份记录

    void saveBackupRecord(MBackupRecord mBackupRecord) throws Exception;//插入备份记录
}
