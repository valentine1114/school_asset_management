package cn.sspku.basics.baseClass;

import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 基类控制器
 * @author
 */
public abstract class sspkuBaseController<E, ID extends Serializable> {

    @Autowired
    public abstract sspkuBaseService<E,ID> getsspkuService();

    @RequestMapping(value = "/getOne", name = "查询单个数据", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询单个数据")
    public Result<E> getOne(@RequestParam ID id){
        return new ResultUtil<E>().setData(getsspkuService().get(id));
    }

    @RequestMapping(value = "/getAll", name = "查询全部数据",  method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询全部数据")
    public Result<List<E>> getAll(){
        return new ResultUtil<List<E>>().setData(getsspkuService().getAll());
    }

    @RequestMapping(value = "/getByPage", name = "查询数据",  method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询数据")
    public Result<Page<E>> getByPage(PageVo page){
        return new ResultUtil<Page<E>>().setData(getsspkuService().findAll(PageUtil.initPage(page)));
    }

    @RequestMapping(value = "/save", name = "新增数据",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增数据")
    public Result<E> save(E entity){
        return new ResultUtil<E>().setData(getsspkuService().save(entity));
    }

    @RequestMapping(value = "/update", name = "编辑数据",  method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "编辑数据")
    public Result<E> update(E entity){
        return new ResultUtil<E>().setData(getsspkuService().update(entity));
    }

    @RequestMapping(value = "/count", name = "查询数据条数",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询数据条数")
    public Result<Long> count(){
        return new ResultUtil<Long>().setData(getsspkuService().count());
    }
    @RequestMapping(value = "/delOne", name = "删除数据",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public Result<Object> delByIds(@RequestParam ID id){
        getsspkuService().delete(id);
        return new ResultUtil<Object>().setSuccessMsg("OK");
    }

    @RequestMapping(value = "/delByIds", name = "删除数据",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public Result<Object> delByIds(@RequestParam ID[] ids){
        for(ID id:ids){
            getsspkuService().delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("OK");
    }
}
