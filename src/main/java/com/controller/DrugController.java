
package com.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;

import com.utils.*;

import com.service.DictionaryService;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 药品
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/yaopin")
public class DrugController {
    private static final Logger logger = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    private DrugService drugService;

    @Autowired
    private DictionaryService dictionaryService;//字典

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("医生".equals(role))
            params.put("yishengId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = drugService.queryPage(params);

        //字典表数据转换
        List<DrugView> list =(List<DrugView>)page.getList();
        for(DrugView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        DrugEntity yaopin = drugService.selectById(id);
        if(yaopin !=null){
            //entity转view
            DrugView view = new DrugView();
            BeanUtils.copyProperties( yaopin , view );//把实体数据重构到view中
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody DrugEntity yaopin, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,yaopin:{}",this.getClass().getName(),yaopin.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<DrugEntity> queryWrapper = new EntityWrapper<DrugEntity>()
            .eq("yaopin_name", yaopin.getYaopinName())
            .eq("shengchanchangjia", yaopin.getShengchanchangjia())
            .eq("yaopin_types", yaopin.getYaopinTypes())
            .eq("yaopin_kucun_number", yaopin.getYaopinKucunNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DrugEntity yaopinEntity = drugService.selectOne(queryWrapper);
        if(yaopinEntity==null){
            yaopin.setInsertTime(new Date());
            yaopin.setCreateTime(new Date());
            drugService.insert(yaopin);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody DrugEntity yaopin, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,yaopin:{}",this.getClass().getName(),yaopin.toString());
        DrugEntity oldYaopinEntity = drugService.selectById(yaopin.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        if("".equals(yaopin.getYaopinPhoto()) || "null".equals(yaopin.getYaopinPhoto())){
                yaopin.setYaopinPhoto(null);
        }

            drugService.updateById(yaopin);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<DrugEntity> oldYaopinList = drugService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        drugService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<DrugEntity> yaopinList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            DrugEntity yaopinEntity = new DrugEntity();
//                            yaopinEntity.setYaopinName(data.get(0));                    //药品名称 要改的
//                            yaopinEntity.setYaopinUuidNumber(data.get(0));                    //药品编号 要改的
//                            yaopinEntity.setYaopinPhoto("");//详情和图片
//                            yaopinEntity.setShengchanchangjia(data.get(0));                    //生产厂家 要改的
//                            yaopinEntity.setYaopinTypes(Integer.valueOf(data.get(0)));   //药品类型 要改的
//                            yaopinEntity.setYaopinKucunNumber(Integer.valueOf(data.get(0)));   //药品库存 要改的
//                            yaopinEntity.setYaopinJinjiaMoney(data.get(0));                    //药品进价 要改的
//                            yaopinEntity.setYaopinNewMoney(data.get(0));                    //售价 要改的
//                            yaopinEntity.setYaopinGongxiaoContent("");//详情和图片
//                            yaopinEntity.setYaopinJinjiContent("");//详情和图片
//                            yaopinEntity.setYaopinZhuyiContent("");//详情和图片
//                            yaopinEntity.setYaopinContent("");//详情和图片
//                            yaopinEntity.setInsertTime(date);//时间
//                            yaopinEntity.setCreateTime(date);//时间
                            yaopinList.add(yaopinEntity);


                            //把要查询是否重复的字段放入map中
                                //药品编号
                                if(seachFields.containsKey("yaopinUuidNumber")){
                                    List<String> yaopinUuidNumber = seachFields.get("yaopinUuidNumber");
                                    yaopinUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> yaopinUuidNumber = new ArrayList<>();
                                    yaopinUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("yaopinUuidNumber",yaopinUuidNumber);
                                }
                        }

                        //查询是否重复
                         //药品编号
                        List<DrugEntity> yaopinEntities_yaopinUuidNumber = drugService.selectList(new EntityWrapper<DrugEntity>().in("yaopin_uuid_number", seachFields.get("yaopinUuidNumber")));
                        if(yaopinEntities_yaopinUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(DrugEntity s:yaopinEntities_yaopinUuidNumber){
                                repeatFields.add(s.getYaopinUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [药品编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        drugService.insertBatch(yaopinList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = drugService.queryPage(params);

        //字典表数据转换
        List<DrugView> list =(List<DrugView>)page.getList();
        for(DrugView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        DrugEntity yaopin = drugService.selectById(id);
            if(yaopin !=null){


                //entity转view
                DrugView view = new DrugView();
                BeanUtils.copyProperties( yaopin , view );//把实体数据重构到view中

                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody DrugEntity yaopin, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,yaopin:{}",this.getClass().getName(),yaopin.toString());
        Wrapper<DrugEntity> queryWrapper = new EntityWrapper<DrugEntity>()
            .eq("yaopin_name", yaopin.getYaopinName())
            .eq("yaopin_uuid_number", yaopin.getYaopinUuidNumber())
            .eq("shengchanchangjia", yaopin.getShengchanchangjia())
            .eq("yaopin_types", yaopin.getYaopinTypes())
            .eq("yaopin_kucun_number", yaopin.getYaopinKucunNumber())
//            .notIn("yaopin_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DrugEntity yaopinEntity = drugService.selectOne(queryWrapper);
        if(yaopinEntity==null){
            yaopin.setInsertTime(new Date());
            yaopin.setCreateTime(new Date());
        drugService.insert(yaopin);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

