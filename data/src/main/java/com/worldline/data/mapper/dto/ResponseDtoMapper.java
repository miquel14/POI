package com.worldline.data.mapper.dto;

import com.worldline.data.entity.mapper.HomeItemsDto;
import com.worldline.data.entity.mapper.HomeItemsDtoList;
import com.worldline.data.mapper.Mapper;
import com.worldline.domain.model.HomeItems;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * ResponseDtoMapper class that groups together all data transfer object to business object Mappers
 */
public class ResponseDtoMapper implements Mapper<HomeItems,HomeItemsDto> {

    @Inject
    public ResponseDtoMapper()  {
        super();
    }

    public List<HomeItems> dataListToModelList(HomeItemsDtoList homeItemsDtoList) {
        List<HomeItems> result = null;
        if (homeItemsDtoList != null) {
            result = dataListToModelList(homeItemsDtoList.getList());
        }
        return result;
    }

    @Override
    public HomeItemsDto modelToData(HomeItems model) {
        return null;
    }

    @Override
    public HomeItems dataToModel(HomeItemsDto data) {
        HomeItems homeitems = new HomeItems();
        if (data != null){
            homeitems.setId(data.getId());
            homeitems.setTitle(data.getTitle());
            //afegir resta de camps
        }
        return homeitems;
    }

    @Override
    public List<HomeItems> dataListToModelList(List<HomeItemsDto> dataList) {
        List<HomeItems> modelList = new ArrayList<>();
        if (dataList != null){
            for(HomeItemsDto homeItemsDto:dataList){
                modelList.add(dataToModel(homeItemsDto));
            }
        }
        return modelList;
    }

    @Override
    public List<HomeItemsDto> modelLisToDataList(List<HomeItems> model) {
        return null;
    }
}
