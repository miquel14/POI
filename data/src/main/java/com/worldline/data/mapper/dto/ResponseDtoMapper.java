package com.worldline.data.mapper.dto;

import com.worldline.data.entity.mapper.HomeItemsDto;
import com.worldline.data.entity.mapper.HomeItemsDtoList;
import com.worldline.data.mapper.Mapper;
import com.worldline.domain.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * ResponseDtoMapper class that groups together all data transfer object to business object Mappers
 */
public class ResponseDtoMapper implements Mapper<HomeItem,HomeItemsDto> {

    @Inject
    public ResponseDtoMapper()  {
        super();
    }

    public List<HomeItem> dataListToModelList(HomeItemsDtoList homeItemsDtoList) {
        List<HomeItem> result = null;
        if (homeItemsDtoList != null) {
            result = dataListToModelList(homeItemsDtoList.getList());
        }
        return result;
    }

    @Override
    public HomeItemsDto modelToData(HomeItem model) {
        return null;
    }

    @Override
    public HomeItem dataToModel(HomeItemsDto data) {
        HomeItem homeItem = new HomeItem();
        if (data != null){
            homeItem.setId(data.getId());
            homeItem.setAddress(data.getAddress());
            homeItem.setTitle(data.getTitle());
            homeItem.setEmail(data.getEmail());
            homeItem.setDescription(data.getDescription());
            homeItem.setPhone(data.getPhone());
            homeItem.setTransport(data.getTransport());
            homeItem.setGeocoord(data.getGeocoordinates());
        }
        return homeItem;
    }

    @Override
    public List<HomeItem> dataListToModelList(List<HomeItemsDto> dataList) {
        List<HomeItem> modelList = new ArrayList<>();
        if (dataList != null){
            for(HomeItemsDto homeItemsDto:dataList){
                modelList.add(dataToModel(homeItemsDto));
            }
        }
        return modelList;
    }

    @Override
    public List<HomeItemsDto> modelLisToDataList(List<HomeItem> model) {
        return null;
    }
}
