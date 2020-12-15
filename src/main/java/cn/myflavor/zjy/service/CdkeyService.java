package cn.myflavor.zjy.service;

import cn.myflavor.zjy.entity.Cdk;
import cn.myflavor.zjy.mapper.CdkeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CdkeyService {
    @Autowired
    private CdkeyMapper cdkeyMapper;

    public boolean addCdk(Cdk cdk) {
        return cdkeyMapper.addCdk(cdk);
    }

    ;

    public Cdk findByCdkey(String cdkey) {
        return cdkeyMapper.findByCdkey(cdkey);
    }

    public void delByCdkey(String cdkey) {
        cdkeyMapper.delByCdkey(cdkey);
    }

    public boolean updataByCdkey(String cdkey, int month) {
        return cdkeyMapper.updataByCdkey(cdkey, month);
    }

    public List<Cdk> findAllCdkey() {
        return cdkeyMapper.findAllCdkey();
    }

    public List<Cdk> findCdkeysLimit(int page, int no) {
        return cdkeyMapper.findCdkeysLimit(page, no);
    }

    public Cdk findById(String id) {
        return cdkeyMapper.findById(id);
    }

    public boolean updataById(Cdk cdk) {
        return cdkeyMapper.updataById(cdk);
    }

    public boolean delById(String id) {
        return cdkeyMapper.delById(id);
    }

    public String cdkCount() {
        return cdkeyMapper.cdkCount();
    }
}
