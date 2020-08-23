package com.arc;

import lombok.extern.slf4j.Slf4j;
import com.arc.model.app .TAppContact ;
import com.arc.model.app.request.TAppContact.request.Request;
import com.arc.mapper.app.TAppContactMapper;
import com.arc.service.app.TAppContactService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
* APP联系人表服务
*
* @author lamy
* @since 
*/
@Slf4j
public class TAppContactServiceImpl implements TAppContactService {

    @Resource
    private TAppContactMapper tAppContactMapper;

    @Override
    public Long save(TAppContact tAppContact) {
        return tAppContactMapper.save(tAppContact) == 1 ? tAppContact.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return tAppContactMapper.delete(id);
    }

    @Override
    public int update(TAppContact tAppContact) {
        return tAppContactMapper.update(tAppContact);
    }

    @Override
    public TAppContact get(Long id) {
        return tAppContactMapper.get(id);
    }

    @Override
    public List<TAppContact> list() {
        return tAppContactMapper.list();
    }

    @Override
    public List<TAppContact> list(TAppContactRequest tAppContactRequest) {
        return tAppContactMapper.list();
    }

    @Override
    public List<TAppContact> listPage(TAppContactRequest tAppContactRequest) {
    return listPage(tAppContactRequest);
    }

    @Override
    public Integer saveBatch(List<TAppContact> tAppContacts) {
        return tAppContactMapper.saveBatch(tAppContacts);
    }

    @Override
    public Integer deleteIdIn(List<Long> ids) {
        return tAppContactMapper.deleteIdIn(ids);
    }

    @Override
    public Integer updateBatch(List<TAppContact> tAppContacts) {
        return tAppContactMapper.updateBatch(tAppContacts);
    }
}
