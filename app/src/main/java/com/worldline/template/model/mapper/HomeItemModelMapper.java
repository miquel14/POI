package com.worldline.template.model.mapper;


import com.worldline.data.mapper.Mapper;
import com.worldline.domain.model.HomeItem;
import com.worldline.template.model.HomeItemModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeItemModelMapper implements Mapper<HomeItemModel, HomeItem> {

    @Inject
    public HomeItemModelMapper() {
    }

    @Override
    public HomeItem modelToData(HomeItemModel model) {
        return null;
    }

    @Override //per l'itemdetail
    public HomeItemModel dataToModel(HomeItem data) {
        HomeItemModel homeItemModel = new HomeItemModel();
        if (data != null){
            homeItemModel.setId(data.getId());
            homeItemModel.setTitle(data.getTitle());
            homeItemModel.setAddress(data.getAddress());
            homeItemModel.setDescription(data.getDescription());
            homeItemModel.setEmail(data.getEmail());
            homeItemModel.setPhone(data.getPhone());
            homeItemModel.setGeoCoordinates(data.getGeocoord());
            homeItemModel.setTransport(data.getTransport());
        }
        return homeItemModel;
    }

    @Override
    public List<HomeItemModel> dataListToModelList(List<HomeItem> data) {
        List<HomeItemModel> model = new ArrayList<>();
        if (data != null) {
            for (HomeItem homeitems : data) {
                HomeItemModel homeItemModel = new HomeItemModel();
                homeItemModel.setId(homeitems.getId());
                homeItemModel.setTitle(homeitems.getTitle());
                homeItemModel.setGeoCoordinates(homeitems.getGeocoord());
                model.add(homeItemModel);
            }
        }
        return model;
    }

    @Override
    public List<HomeItem> modelLisToDataList(List<HomeItemModel> model) {
        return null;
    }
}
