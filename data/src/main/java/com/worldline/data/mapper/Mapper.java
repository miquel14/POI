package com.worldline.data.mapper;

import java.util.List;

/**
 * Interface that specifies the method to map data to model and vice versa
 */
public interface Mapper <M, D> {

    D modelToData(M model);

    M dataToModel(D data);

    List<M> dataListToModelList(List<D> data);

    List<D> modelLisToDataList(List<M> model);
}

