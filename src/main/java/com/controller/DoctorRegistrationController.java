
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
 * 医生挂号
 * 后端接口
 *
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/yishengGuahao")
public class DoctorRegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorRegistrationController.class);


    @Autowired
    private DoctorRegistrationService doctorRegistrationService;


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
        PageUtils page = doctorRegistrationService.queryPage(params);

        //字典表数据转换
        List<DoctorRegistrationView> list = (List<DoctorRegistrationView>) page.getList();
        for (DoctorRegistrationView c : list) {
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
        DoctorRegistrationEntity yishengGuahao = doctorRegistrationService.selectById(id);
        if (yishengGuahao != null) {
            //entity转view
            DoctorRegistrationView view = new DoctorRegistrationView();
            BeanUtils.copyProperties(yishengGuahao, view);//把实体数据重构到view中
            //级联表 病人
            //级联表
            PatientEntity bingren = patientService.selectById(yishengGuahao.getBingrenId());
            if (bingren != null) {
                BeanUtils.copyProperties(bingren, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yishengId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
                view.setBingrenId(bingren.getId());
            }
            //级联表 医生
            //级联表
            DoctorEntity yisheng = doctorService.selectById(yishengGuahao.getYishengId());
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
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DoctorRegistrationEntity yishengGuahao, HttpServletRequest request) {
        logger.debug("save方法:,,Controller:{},,yishengGuahao:{}", this.getClass().getName(), yishengGuahao.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永远不会进入");
        else if ("医生".equals(role))
            yishengGuahao.setYishengId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<DoctorRegistrationEntity> queryWrapper = new EntityWrapper<DoctorRegistrationEntity>()
                .eq("bingren_id", yishengGuahao.getBingrenId())
                .eq("yisheng_id", yishengGuahao.getYishengId())
                .eq("yisheng_guahao_time", new SimpleDateFormat("yyyy-MM-dd").format(yishengGuahao.getYishengGuahaoTime()))
                .eq("yisheng_guahao_types", yishengGuahao.getYishengGuahaoTypes());

        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        DoctorRegistrationEntity yishengGuahaoEntity = doctorRegistrationService.selectOne(queryWrapper);
        if (yishengGuahaoEntity == null) {
            yishengGuahao.setInsertTime(new Date());
            yishengGuahao.setCreateTime(new Date());
            doctorRegistrationService.insert(yishengGuahao);
            return R.ok();
        } else {
            return R.error(511, "表中有相同数据");
        }
    }

    /**
     * 后端修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DoctorRegistrationEntity yishengGuahao, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,yishengGuahao:{}", this.getClass().getName(), yishengGuahao.toString());
        DoctorRegistrationEntity oldYishengGuahaoEntity = doctorRegistrationService.selectById(yishengGuahao.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("医生".equals(role))
//            yishengGuahao.setYishengId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        doctorRegistrationService.updateById(yishengGuahao);//根据id更新
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        List<DoctorRegistrationEntity> oldYishengGuahaoList = doctorRegistrationService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        doctorRegistrationService.deleteBatchIds(Arrays.asList(ids));

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
            List<DoctorRegistrationEntity> yishengGuahaoList = new ArrayList<>();//上传的东西
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
                            DoctorRegistrationEntity yishengGuahaoEntity = new DoctorRegistrationEntity();
//                            yishengGuahaoEntity.setYishengGuahaoUuidNumber(data.get(0));                    //挂号编号 要改的
//                            yishengGuahaoEntity.setBingrenId(Integer.valueOf(data.get(0)));   //病人 要改的
//                            yishengGuahaoEntity.setYishengId(Integer.valueOf(data.get(0)));   //医生 要改的
//                            yishengGuahaoEntity.setHuafeijine(data.get(0));                    //花费金额 要改的
//                            yishengGuahaoEntity.setYishengGuahaoTime(sdf.parse(data.get(0)));          //挂号日期 要改的
//                            yishengGuahaoEntity.setYishengGuahaoTypes(Integer.valueOf(data.get(0)));   //挂号状态 要改的
//                            yishengGuahaoEntity.setInsertTime(date);//时间
//                            yishengGuahaoEntity.setCreateTime(date);//时间
                            yishengGuahaoList.add(yishengGuahaoEntity);


                            //把要查询是否重复的字段放入map中
                            //挂号编号
                            if (seachFields.containsKey("yishengGuahaoUuidNumber")) {
                                List<String> yishengGuahaoUuidNumber = seachFields.get("yishengGuahaoUuidNumber");
                                yishengGuahaoUuidNumber.add(data.get(0));//要改的
                            } else {
                                List<String> yishengGuahaoUuidNumber = new ArrayList<>();
                                yishengGuahaoUuidNumber.add(data.get(0));//要改的
                                seachFields.put("yishengGuahaoUuidNumber", yishengGuahaoUuidNumber);
                            }
                        }

                        //查询是否重复
                        //挂号编号
                        List<DoctorRegistrationEntity> yishengGuahaoEntities_yishengGuahaoUuidNumber = doctorRegistrationService.selectList(new EntityWrapper<DoctorRegistrationEntity>().in("yisheng_guahao_uuid_number", seachFields.get("yishengGuahaoUuidNumber")));
                        if (yishengGuahaoEntities_yishengGuahaoUuidNumber.size() > 0) {
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for (DoctorRegistrationEntity s : yishengGuahaoEntities_yishengGuahaoUuidNumber) {
                                repeatFields.add(s.getYishengGuahaoUuidNumber());
                            }
                            return R.error(511, "数据库的该表中的 [挂号编号] 字段已经存在 存在数据为:" + repeatFields.toString());
                        }
                        doctorRegistrationService.insertBatch(yishengGuahaoList);
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
        PageUtils page = doctorRegistrationService.queryPage(params);

        //字典表数据转换
        List<DoctorRegistrationView> list = (List<DoctorRegistrationView>) page.getList();
        for (DoctorRegistrationView c : list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        DoctorRegistrationEntity yishengGuahao = doctorRegistrationService.selectById(id);
        if (yishengGuahao != null) {


            //entity转view
            DoctorRegistrationView view = new DoctorRegistrationView();
            BeanUtils.copyProperties(yishengGuahao, view);//把实体数据重构到view中

            //级联表
            PatientEntity bingren = patientService.selectById(yishengGuahao.getBingrenId());
            if (bingren != null) {
                BeanUtils.copyProperties(bingren, view, new String[]{"id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setBingrenId(bingren.getId());
            }
            //级联表
            DoctorEntity yisheng = doctorService.selectById(yishengGuahao.getYishengId());
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
    public R add(@RequestBody DoctorRegistrationEntity yishengGuahao, HttpServletRequest request) {
        logger.debug("add方法:,,Controller:{},,yishengGuahao:{}", this.getClass().getName(), yishengGuahao.toString());

        List<DoctorRegistrationEntity> yishengGuahaoEntityList = doctorRegistrationService.selectList(new EntityWrapper<DoctorRegistrationEntity>()
                .eq("yisheng_id", yishengGuahao.getYishengId())
                .eq("yisheng_guahao_time", new SimpleDateFormat("yyyy-MM-dd").format(yishengGuahao.getYishengGuahaoTime()))
                .notIn("yisheng_guahao_types", new Integer[]{102})
        );

        DoctorEntity yishengEntity = doctorService.selectById(yishengGuahao.getYishengId());
        if (yishengEntity == null) {
            return R.error("查不到医生");
        }

        if (yishengEntity.getGuahaoshuliang() < (yishengGuahaoEntityList.size() + 1)) {
            return R.error("当天挂号人数已满,请选择其他天挂号");
        }
        PatientEntity bingrenEntity = patientService.selectById(yishengGuahao.getBingrenId());
        if (bingrenEntity == null)
            return R.error("查不到病人信息");

        YonghuEntity yonghuEntity = yonghuService.selectById(bingrenEntity.getYonghuId());
        if (yonghuEntity == null) {
            return R.error("查不到用户信息");
        }

        yishengGuahao.setHuafeijine(yishengEntity.getGuahaojine());

        double balance = yonghuEntity.getNewMoney() - yishengGuahao.getHuafeijine();
        if (balance < 0) {
            return R.error("当前账户余额不够支付,请充值后再支付");
        }
        yonghuEntity.setNewMoney(balance);
        yonghuService.updateById(yonghuEntity);


        yishengGuahao.setYishengGuahaoUuidNumber(String.valueOf(new Date().getTime()));
        yishengGuahao.setYishengGuahaoTypes(101);
        yishengGuahao.setInsertTime(new Date());
        yishengGuahao.setCreateTime(new Date());
        doctorRegistrationService.insert(yishengGuahao);

        return R.ok();

    }

    /**
     * 取消挂号
     */
    @RequestMapping("/refund")
    public R refund(Integer id, HttpServletRequest request) {
        logger.debug("refund方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        String role = String.valueOf(request.getSession().getAttribute("role"));

        DoctorRegistrationEntity yishengGuahao = doctorRegistrationService.selectById(id);//当前表service
        Integer yishengId = yishengGuahao.getYishengId();
        if (yishengId == null)
            return R.error(511, "查不到该医生");
        DoctorEntity yishengEntity = doctorService.selectById(yishengId);
        if (yishengEntity == null)
            return R.error(511, "查不到该医生");

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        YonghuEntity yonghuEntity = yonghuService.selectById(userId);
        if (yonghuEntity == null)
            return R.error(511, "用户不能为空");
        if (yonghuEntity.getNewMoney() == null)
            return R.error(511, "用户金额不能为空");
        Double zhekou = 1.0;

        //计算金额
        Double money = yishengEntity.getGuahaojine();
        yonghuEntity.setNewMoney(yonghuEntity.getNewMoney() + money); //设置金额


        yishengGuahao.setYishengGuahaoTypes(102);//设置订单状态为已取消挂号
        doctorRegistrationService.updateById(yishengGuahao);//根据id更新
        yonghuService.updateById(yonghuEntity);//更新用户信息
        doctorService.updateById(yishengEntity);//更新订单中医生的信息

        return R.ok();
    }

    /**
     * 使用
     */
    @RequestMapping("/deliver")
    public R deliver(Integer id, HttpServletRequest request) {
        logger.debug("refund:,,Controller:{},,ids:{}", this.getClass().getName(), id.toString());
        DoctorRegistrationEntity yishengGuahaoEntity = doctorRegistrationService.selectById(id);
        yishengGuahaoEntity.setYishengGuahaoTypes(103);//设置订单状态为已使用
        doctorRegistrationService.updateById(yishengGuahaoEntity);

        return R.ok();
    }


}

