
package com.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 病例
 * 后端接口
 *
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/bingli")
public class CaseController {
    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;

    @Autowired
    private PatientService patientService;//病人
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private DoctorService doctorService;//医生
    @Autowired
    private YonghuService yonghuService;//用户

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("page方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永不会进入");
        else if ("用户".equals(role))
            params.put("yonghuId", request.getSession().getAttribute("userId"));
        else if ("医生".equals(role))
            params.put("yishengId", request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = caseService.queryPage(params);

        //字典表数据转换
        List<CaseView> list = (List<CaseView>) page.getList();
        for (CaseView c : list) {
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("info方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        CaseEntity bingli = caseService.selectById(id);
        if (bingli != null) {
            //entity转view
            CaseView view = new CaseView();
            BeanUtils.copyProperties(bingli, view);//把实体数据重构到view中
            //级联表 病人
            //级联表
            PatientEntity bingren = patientService.selectById(bingli.getBingrenId());
            if (bingren != null) {
                BeanUtils.copyProperties(bingren, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yishengId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
                view.setBingrenId(bingren.getId());
            }
            //级联表 医生
            //级联表
            DoctorEntity yisheng = doctorService.selectById(bingli.getYishengId());
            if (yisheng != null) {
                BeanUtils.copyProperties(yisheng, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yishengId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
                view.setYishengId(yisheng.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        } else {
            return R.error(511, "查不到数据");
        }

    }

    /**
     * 后端详情
     */
    @RequestMapping("/jiaofei")
    public R jiaofei(Long id, HttpServletRequest request) {
        logger.debug("jiaofei方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        CaseEntity bingli = caseService.selectById(id);
        if (bingli == null) {
            return R.error("查不到病例缴费单");
        }
        if (bingli.getBingliTypes() != 1)
            return R.error("已经缴费过了");
        PatientEntity bingrenEntity = patientService.selectById(bingli.getBingrenId());
        if (bingrenEntity == null)
            return R.error("查不到病人");
        YonghuEntity yonghuEntity = yonghuService.selectById(bingrenEntity.getYonghuId());
        if (yonghuEntity == null)
            return R.error("查不到病人");

        double balance = yonghuEntity.getNewMoney() - bingli.getHuafeijine();
        if (balance < 0)
            return R.error("账户余额不够缴费,请充值后再缴费");

        yonghuEntity.setNewMoney(balance);
        yonghuService.updateById(yonghuEntity);

        bingli.setBingliTypes(2);
        caseService.updateById(bingli);


        return R.ok();

    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CaseEntity bingli, HttpServletRequest request) {
        logger.debug("save方法:,,Controller:{},,bingli:{}", this.getClass().getName(), bingli.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永远不会进入");
        else if ("医生".equals(role)) {
            bingli.setYishengId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
            bingli.setBingliTypes(1);
        }

        bingli.setInsertTime(new Date());
        bingli.setCreateTime(new Date());
        caseService.insert(bingli);
        return R.ok();
    }

    /**
     * 后端修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CaseEntity bingli, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,bingli:{}", this.getClass().getName(), bingli.toString());
        CaseEntity oldBingliEntity = caseService.selectById(bingli.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("医生".equals(role))
//            bingli.setYishengId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        caseService.updateById(bingli);//根据id更新
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        List<CaseEntity> oldBingliList = caseService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        caseService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save(String fileName, HttpServletRequest request) {
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}", this.getClass().getName(), fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<CaseEntity> bingliList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields = new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if (lastIndexOf == -1) {
                return R.error(511, "该文件没有后缀");
            } else {
                String suffix = fileName.substring(lastIndexOf);
                if (!".xls".equals(suffix)) {
                    return R.error(511, "只支持后缀为xls的excel文件");
                } else {
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if (!file.exists()) {
                        return R.error(511, "找不到上传文件，请联系管理员");
                    } else {
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for (List<String> data : dataList) {
                            //循环
                            CaseEntity bingliEntity = new CaseEntity();
//                            bingliEntity.setBingliUuidNumber(data.get(0));                    //病例编号 要改的
//                            bingliEntity.setBingrenId(Integer.valueOf(data.get(0)));   //病人 要改的
//                            bingliEntity.setYishengId(Integer.valueOf(data.get(0)));   //医生 要改的
//                            bingliEntity.setJianchaTime(sdf.parse(data.get(0)));          //检查时间 要改的
//                            bingliEntity.setBingrenzishuContent("");//详情和图片
//                            bingliEntity.setJianchajieguoContent("");//详情和图片
//                            bingliEntity.setYishengkaifangContent("");//详情和图片
//                            bingliEntity.setYishengjianyiContent("");//详情和图片
//                            bingliEntity.setHuafeijine(data.get(0));                    //花费金额 要改的
//                            bingliEntity.setXiacijiuzhenTime(sdf.parse(data.get(0)));          //下次就诊时间 要改的
//                            bingliEntity.setBingliTypes(Integer.valueOf(data.get(0)));   //状态 要改的
//                            bingliEntity.setInsertTime(date);//时间
//                            bingliEntity.setCreateTime(date);//时间
                            bingliList.add(bingliEntity);


                            //把要查询是否重复的字段放入map中
                            //病例编号
                            if (seachFields.containsKey("bingliUuidNumber")) {
                                List<String> bingliUuidNumber = seachFields.get("bingliUuidNumber");
                                bingliUuidNumber.add(data.get(0));//要改的
                            } else {
                                List<String> bingliUuidNumber = new ArrayList<>();
                                bingliUuidNumber.add(data.get(0));//要改的
                                seachFields.put("bingliUuidNumber", bingliUuidNumber);
                            }
                        }

                        //查询是否重复
                        //病例编号
                        List<CaseEntity> bingliEntities_bingliUuidNumber = caseService.selectList(new EntityWrapper<CaseEntity>().in("bingli_uuid_number", seachFields.get("bingliUuidNumber")));
                        if (bingliEntities_bingliUuidNumber.size() > 0) {
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for (CaseEntity s : bingliEntities_bingliUuidNumber) {
                                repeatFields.add(s.getBingliUuidNumber());
                            }
                            return R.error(511, "数据库的该表中的 [病例编号] 字段已经存在 存在数据为:" + repeatFields.toString());
                        }
                        caseService.insertBatch(bingliList);
                        return R.ok();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(511, "批量插入数据异常，请联系管理员");
        }
    }


    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("list方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = caseService.queryPage(params);

        //字典表数据转换
        List<CaseView> list = (List<CaseView>) page.getList();
        for (CaseView c : list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        CaseEntity bingli = caseService.selectById(id);
        if (bingli != null) {


            //entity转view
            CaseView view = new CaseView();
            BeanUtils.copyProperties(bingli, view);//把实体数据重构到view中

            //级联表
            PatientEntity bingren = patientService.selectById(bingli.getBingrenId());
            if (bingren != null) {
                BeanUtils.copyProperties(bingren, view, new String[]{"id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setBingrenId(bingren.getId());
            }
            //级联表
            DoctorEntity yisheng = doctorService.selectById(bingli.getYishengId());
            if (yisheng != null) {
                BeanUtils.copyProperties(yisheng, view, new String[]{"id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setYishengId(yisheng.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        } else {
            return R.error(511, "查不到数据");
        }
    }


    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CaseEntity bingli, HttpServletRequest request) {
        logger.debug("add方法:,,Controller:{},,bingli:{}", this.getClass().getName(), bingli.toString());
        Wrapper<CaseEntity> queryWrapper = new EntityWrapper<CaseEntity>()
                .eq("bingli_uuid_number", bingli.getBingliUuidNumber())
                .eq("bingren_id", bingli.getBingrenId())
                .eq("yisheng_id", bingli.getYishengId())
                .eq("bingli_types", bingli.getBingliTypes())
//            .notIn("bingli_types", new Integer[]{102})
                ;
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        CaseEntity bingliEntity = caseService.selectOne(queryWrapper);
        if (bingliEntity == null) {
            bingli.setInsertTime(new Date());
            bingli.setCreateTime(new Date());
            caseService.insert(bingli);

            return R.ok();
        } else {
            return R.error(511, "表中有相同数据");
        }
    }

}

