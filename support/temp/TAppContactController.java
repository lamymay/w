package com.arc;

import lombok.extern.slf4j.Slf4j;
import com.arc.model.app .TAppContact ;
import com.arc.test.model.request.TAppContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
    import java.util.Date;

/**
* APP联系人表接口
*
* @author lamy
* @since 
*/
@Slf4j
public class TAppContactController {

    @Autowired
    private TAppContactService tAppContactService;

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param tAppContact 实体
    * @return 主键 PK
    */
    ResponseVo<Long> save(TAppContact tAppContact){
        return ResponseVo.success(tAppContactService.save(tAppContact));
    }

    /**
    * 通过主键删除一条数据
    *
    * @param id 主键
    * @return 影响数据条数
    */
    ResponseVo<int> delete(Long id){
        return ResponseVo.success(tAppContactService.delete(id));
    }

    /**
    * 更新一条数据
    *
    * @param tAppContact
    * @return 影响数据条数
    */
     ResponseVo<int> update(TAppContact tAppContact) {
        return ResponseVo.success(tAppContactService.update(tAppContact));
    }

    /**
    * 通过主键查询一条数据
    *
    * @param id 主键
    * @return 返回一条数据
    */
     ResponseVo<TAppContact> get(Long id) {
        return ResponseVo.success(tAppContactService.get(id));
    }

    /**
    * 无条件查询全部数据
    *
    * @return 全部数据
    */
    ResponseVo<List<TAppContact>> list() {
        return ResponseVo.success(tAppContactService.list());
    }

    /**
    * 条件查询数据列表
    *
    * @return 数据集合
    */
     ResponseVo<List<TAppContact>> list(TAppContactRequest request) {
        return ResponseVo.success(tAppContactService.list(request));
    }

    /**
    * 分页条件查询数据列表
    *
    * @param request
    * @return 数据集合
    */
    ResponseVo<List<TAppContact>> listPage(TAppContactRequest request) {
        return ResponseVo.success(tAppContactService.listPage(request));
    }

    /**
    * 批量插入
    *
    * @param tAppContacts 数据集合
    * @return 影响条数
    */
    ResponseVo<Integer> saveBatch(List<TAppContact> tAppContacts) {
        return ResponseVo.success(tAppContactService.saveBatch(tAppContacts));
    }

    /**
    * 批量删除
    *
    * @param ids 主键集合
    * @return 影响条数
    */
    ResponseVo<Integer> deleteIdIn(List<Long> ids) {
        return ResponseVo.success(tAppContactService.deleteIdIn(ids));
    }

    /**
    * 批量更新
    *
    * @param tAppContacts 数据集合
    * @return 影响条数
    */
    ResponseVo<Integer> updateBatch(List<TAppContact> tAppContacts) {
        return ResponseVo.success(tAppContactService.updateBatch(tAppContacts));
    }

}
