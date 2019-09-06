package com.youdu.imoocbusiness.module.recommend;


import com.youdu.imoocbusiness.module.BaseModel;

import java.util.ArrayList;

/**
 * @author: vision
 * @function:
 * @date: 16/9/2
 */
public class RecommendHeadValue extends BaseModel {

    public ArrayList<String> ads;
    public ArrayList<String> middle;
    public ArrayList<RecommendFooterValue> footer;

}
