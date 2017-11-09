package com.worldline.template.model.mapper;


import com.worldline.data.mapper.Mapper;
import com.worldline.domain.model.HomeItems;
import com.worldline.template.model.HomeItemModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeItemModelMapper implements Mapper<HomeItemModel, HomeItems> {

    @Inject
    public HomeItemModelMapper() {
    }

    @Override
    public HomeItems modelToData(HomeItemModel model) {
        return null;
    }

    @Override
    public HomeItemModel dataToModel(HomeItems data) {
        HomeItemModel homeItemModel = new HomeItemModel();
        if (data != null){
            homeItemModel.setId(data.getId());
            homeItemModel.setTitle(data.getTitle());
        }
        return homeItemModel;
    }

    @Override
    public List<HomeItemModel> dataListToModelList(List<HomeItems> data) {
        List<HomeItemModel> model = new ArrayList<>();
        if (data != null) {
            for (HomeItems homeitems : data) {
                model.add(dataToModel(homeitems));
            }
        }
        return model;
    }

    @Override
    public List<HomeItems> modelLisToDataList(List<HomeItemModel> model) {
        return null;
    }
}
