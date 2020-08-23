package com.arc;

import lombok.extern.slf4j.Slf4j;
import com.arc.model.app .TAppContact ;
import com.arc.test.model.request.TAppContactRequest;
import java.util.List;
    import java.util.Date;
/**
* APP联系人表服务
*
* @author lamy
* @since 
*/
public interface TAppContactService {

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param tAppContact 实体
    * @return 主键 PK
    */
    Long save(TAppContact tAppContact);

    /**
    * 通过主键删除一条数据
    *
    * @param id 主键
    * @return 影响数据条数
    */
    int delete(Long id);

    /**
    * 更新一条数据
    *
    * @param tAppContact
    * @return 影响数据条数
    */
    int update(TAppContact tAppContact);

    /**
    * 通过主键查询一条数据
    *
    * @param id 主键
    * @return 返回一条数据
    */
    TAppContact get(Long id);

    /**
    * 无条件查询全部数据
    *
    * @return 全部数据
    */
    List<TAppContact> list();

    /**
    * 条件查询数据列表
    *
    * @return 数据集合
    */
    List<TAppContact> list(TAppContactRequest tAppContactRequest);

    /**
    * 分页条件查询数据列表
    *
    * @param tAppContactRequest
    * @return 数据集合
    */
    List<TAppContact> listPage(TAppContactRequest tAppContactRequest);

    /**
    * 批量插入
    *
    * @param tAppContacts 数据集合
    * @return 影响条数
    */
    Integer saveBatch(List<TAppContact> tAppContacts);

    /**
    * 批量删除
    *
    * @param ids 主键集合
    * @return 影响条数
    */

    Integer deleteIdIn(List<Long> ids);

    /**
    * 批量更新
    *
    * @param tAppContacts 数据集合
    * @return 影响条数
    */
    Integer updateBatch(List<TAppContact> tAppContacts);
}
