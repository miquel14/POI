package com.worldline.data.entity.mapper;

import com.worldline.data.entity.vo.StringVo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmList;

/**
 * Common mapper entity
 */
@Singleton
public class BaseMapper {

    @Inject
    public BaseMapper() {
    }

    protected String getIdFromUrl(String url) {
        String id = "";
        if (!TextUtils.isEmpty(url)) {
            String[] splitUrl = url.split("/");
            id = splitUrl[splitUrl.length - 1];
        }
        return id;
    }

    public List<String> toList(RealmList<StringVo> listVo) {
        List<String> list = new ArrayList<>();
        if (listVo != null) {
            for (StringVo item : listVo) {
                list.add(item.getString());
            }
        }
        return list;
    }

    public RealmList<StringVo> toRealmList(List<String> list) {
        RealmList<StringVo> realmList = new RealmList<>();
        if (list != null) {
            for (String item : list) {
                StringVo stringVo = new StringVo();
                stringVo.setString(item);
                realmList.add(stringVo);
            }
        }
        return realmList;
    }

}