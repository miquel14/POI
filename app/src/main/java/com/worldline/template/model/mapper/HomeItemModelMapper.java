package com.worldline.template.model.mapper;


import com.worldline.domain.model.HomeItems;

import java.util.ArrayList;
import java.util.List;

public class HomeItemModelMapper implements Mapper<HomeItemModel, HomeItems> {

    @Override
    public HomeItems modelToData(HomeItemModel model) {
        return null;
    }

    @Override
    public HomeItemModel dataToModel(HomeItems data) {
        return null;
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
