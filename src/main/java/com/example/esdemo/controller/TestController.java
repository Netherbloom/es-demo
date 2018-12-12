package com.example.esdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.esdemo.util.ElasticsearchUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/28.
 */
@RequestMapping("/test/**")
@RestController
public class TestController {


    /**
     * 新增数据
     * @param index
     * @param type
     * @param data
     * @return
     */
    @RequestMapping("/saveData")
    public Map<String,Object> saveData(String index,String type,String data){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","ok");
        try {
            JSONObject jsonObject=JSONObject.parseObject(data);
            //判断索引是否存在
            if(!ElasticsearchUtils.isIndexExist(index)){
                //创建索引
                ElasticsearchUtils.createIndex(index);
            }
          String id=  ElasticsearchUtils.addData(jsonObject,index,type);
          map.put("id",id);
        }catch (Exception e){
            map.put("code","201");
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 通过id删除数据
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/delData")
    public Map<String,Object> delData(String index,String type,String id){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","ok");
        try {
            //判断索引是否存在
            if(ElasticsearchUtils.isIndexExist(index)){
                ElasticsearchUtils.deleteDataById(index,type,id);
            }
        }catch (Exception e){
            map.put("code","201");
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 删除索引下所有数据
     * @param index
     * @return
     */
    @RequestMapping("/delall")
    public Map<String,Object> delall(String index){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","ok");
        try {
            //判断索引是否存在
            if(ElasticsearchUtils.isIndexExist(index)){
                ElasticsearchUtils.deleteIndex(index);
            }
        }catch (Exception e){
            map.put("code","201");
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 更新数据
     * @param index
     * @param type
     * @param id
     * @param data
     * @return
     */
    @RequestMapping("/update")
    public Map<String,Object> update(String index,String type,String id,String data){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","ok");
        try {
            //判断索引是否存在
            if(ElasticsearchUtils.isIndexExist(index)){
                JSONObject jsonObject=JSONObject.parseObject(data);
                ElasticsearchUtils.updateDataById(jsonObject,index,type,id);
            }
        }catch (Exception e){
            map.put("code","201");
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    @RequestMapping("/findById")
    public Map<String,Object> findById(String index,String type,String id,String fields){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","ok");
        try {
            //判断索引是否存在
            if(ElasticsearchUtils.isIndexExist(index)){
                map.put("data",ElasticsearchUtils.searchDataById(index, type, id, fields));
            }
        }catch (Exception e){
            map.put("code","201");
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 使用分词查询
     *
     * @param index    索引名称
     * @param type     类型名称,可传入多个type逗号分隔
     * @param fields   需要显示的字段，逗号分隔（缺省为全部字段）
     * @param matchStr 过滤条件（xxx=111,aaa=222）
     * @return
     */
    @RequestMapping("/findByMatchStr")
    public Map<String,Object> findByMatchStr(String index,String type,String fields,String matchStr){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","ok");
        try {
            //判断索引是否存在
            if(ElasticsearchUtils.isIndexExist(index)){
                map.put("data",ElasticsearchUtils.searchListData(index, type, fields, matchStr));
            }
        }catch (Exception e){
            map.put("code","201");
            map.put("msg",e.getMessage());
        }
        return map;
    }
}
