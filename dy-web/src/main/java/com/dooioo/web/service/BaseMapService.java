package com.dooioo.web.service;

import com.dooioo.commons.Strings;
import com.dooioo.web.common.Constants;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.dao.QueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Service 通用方法集合
 * 使用该类里面的方法，首先要确保ibatis已有对应的sqlMap
 * 如果没有，则在使用时会报错，留心使用
 *
 * 该类不使用javabean对象，只需要map就搞定
 *
 * User: kuang
 * Date: 12-8-6
 * Time: 下午5:17
 */
public abstract class BaseMapService {

    @Autowired
    protected QueryDao queryDao;

    /**
     * 通过主键id查总量
     * 对应sqlId为：count
     * @param id
     * @return
     */
    public int count(Serializable id) {
        return queryDao.count(sqlId(Constants.DAO_COUNT), id);
    }

    /**
     * 通过主键id查总量
     * 对应sqlId为：count
     * @param sqlId
     * @param id
     * @return
     */
    public int count(String sqlId, Serializable id) {
        return queryDao.count(sqlId, id);
    }

    /**
     * 通过主键id查总量
     * 对应sqlId为：count
     * @param sqlId
     * @param params
     * @return
     */
    public int count(String sqlId, Map<String, Object> params) {
        return queryDao.count(sqlId, params);
    }

    /**
     * 通过主键id查总量
     * 对应sqlId为：count
     * @param params
     * @return
     */
    public int count(Map<String, Object> params) {
        return queryDao.count(sqlId(Constants.DAO_COUNT), params);
    }

    public int count2(Map<String, Object> params) {
        return queryDao.count(sqlId(Constants.DAO_COUNT_2), params);
    }

    /**
     * 通过主键id查询，返回需要查询的对象
     * 调用对应sqlId, Statement.findById
     * @param id 主键id
     * @return model
     */
    public Map<String, Object> findById(Serializable id) {
        return findById(sqlId(Constants.DAO_FINDBYID), id);
    }

    /**
     * 通过主键id查询，返回需要查询的对象
     * 其中sqlId，由客户端传入
     * @param sqlId
     * @param id
     * @return
     */
    public Map<String, Object> findById(String sqlId, Serializable id) {
        return queryDao.findById(sqlId, id);
    }

    /**
     * 通过自由组装字段及值进行查询，
     * 在内部会给对应model相应的字段赋值
     * 调用DAO时，传入的DB对应的model
     * 多个字段时，以,分隔
     * 调用对应sqlId, Statement.findByBean
     * @param params
     * @param fieldName 字段名，e.g: name,passwor; name_and_password
     * @param filedValue
     * @return model
     */
//    public Object findByBean(Map<String, Object> params, String fieldName, String... filedValue) {
//        entityObject = setPorperty(entityObject, fieldName, filedValue);
//        return findByBean(sqlId(Constants.DAO_QUERY_FOR_BEAN), entityObject);
//    }
//
//    public Object findByBean(Serializable id) {
//        return findByBean(sqlId(Constants.DAO_QUERY_FOR_BEAN), id);
//    }
//
//    public Object findByBean(String sqlId, Map<String, Object> bindParams) {
//        return (Object)queryDao.queryForBean(sqlId, bindParams);
//    }
//
//    public Object findByBean(String sqlId, Map<String, Object> params) {
//        return (Object)queryDao.queryForBean(sqlId, entityObject);
//    }
//
//    public Object findByBean(String sqlId, Serializable id) {
//        return (Object)queryDao.queryForBean(sqlId, id);
//    }


    /**
     * 查询一条记录，返回一个对象
     * 对应sqlId为：queryFirst
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryFirst(Map<String, Object> params) {
        return queryFirst(sqlId(Constants.DAO_QUERY_First), params);
    }

    public Map<String, Object> queryFirst(String sqlId, Map<String, Object> params) {
        return (Map<String, Object>) queryDao.queryForObject(sqlId, params);
    }

    public List<Map<String, Object>> queryForList() {
        return queryDao.queryForList(sqlId(Constants.DAO_QUERY));
    }

    public List<Map<String, Object>> queryForList(Serializable id) {
        return queryDao.queryForList(sqlId(Constants.DAO_QUERY), id);
    }

    public List<Map<String, Object>> queryForList(String sqlId, Serializable id) {
        return queryDao.queryForList(sqlId, id);
    }

    public List<Map<String, Object>> queryForList(String sqlId, Map<String, Object> bindParams) {
        return queryDao.queryForList(sqlId, bindParams);
    }

    public List<Map<String, Object>> queryForListBySqlId(String sqlId) {
        return queryDao.queryForList(sqlId);
    }

    public List<Map<String, Object>> queryForList(Map<String, Object> bindParams) {
        return queryForList(sqlId(Constants.DAO_QUERY), bindParams);
    }


    public List<String> queryForListStr(String sqlId, Map<String, Object> bindParams) {
        return queryDao.queryForListStr(sqlId, bindParams);
    }

    public List<Object> queryForListObj(String sqlId) {
        return queryDao.queryForListObj(sqlId);
    }

    public List<Object> queryForListObj(String sqlId, Map<String, Object> params) {
        return queryDao.queryForListObj(sqlId, params);
    }

    public Paginate queryForPaginate(Map<String, Object> params) {
        return queryForPaginate(Constants.DAO_QUERY_FOR_PAGINATE, params);
    }

    public Paginate queryForPaginate(String sqlId, Map<String, Object> params) {
        //分页组装从1开始计算，存储过程的分页从0开始
        Paginate paginate = new Paginate((Integer)params.get("pageNo") + 1, (Integer)params.get("pageSize"));
        paginate.setPageList(queryDao.queryForList(sqlId(sqlId), params));
        paginate.setTotalCount(this.count(params));
        return paginate;
    }

    public boolean update(String sqlId, Serializable id) {
        return queryDao.update(sqlId, id) > 0;
    }

    public boolean update(Map<String, Object> params) {
        return queryDao.update(sqlId(Constants.DAO_UPDATE), params) > 0;
    }

    public boolean update(String sqlId, Map<String, Object> bindParams) {
        return queryDao.update(sqlId, bindParams) > 0;
    }

    public boolean delete(Serializable id) {
        return queryDao.delete(sqlId(Constants.DAO_DELETE), id) > 0;
    }

    public boolean delete(Map<String, Object> bindParams) {
        return queryDao.delete(sqlId(Constants.DAO_DELETE), bindParams) > 0;
    }

    public boolean delete(String sqlId, Map<String, Object> bindParams) {
        return queryDao.delete(sqlId, bindParams) > 0;
    }

    public boolean delete(String sqlId, Serializable id) {
        return queryDao.delete(sqlId, id) > 0;
    }

    public boolean insert(Map<String, Object> bindParams) {
        return queryDao.insert(sqlId(Constants.DAO_INSERT), bindParams) > 0;
    }

    public boolean insert(String sqlId, Map<String, Object> bindParams) {
        return queryDao.insert(sqlId, bindParams) > 0;
    }

    /**
     * 插入数据并返回identity
     * @param entityObject 实体对象
     * @return integer
     */
    public Integer insertAndReturnId(Object entityObject) {
        return queryDao.insertAndReturnId(sqlId(Constants.DAO_INSERTANDRETURNID), entityObject);
    }

    public Integer insertAndReturnId(String sqlId, Object entityObject) {
        return queryDao.insertAndReturnId(sqlId, entityObject);
    }

    public Integer insertAndReturnId(String sqlId, Map<String, Object> params) {
        return queryDao.insertAndReturnId(sqlId, params);
    }

    public boolean contains(Map<String, Object> params) {
        return queryFirst(params) != null;
    }


    public abstract String getNamespace();

    /**
     * @see #sqlId(String)
     * @param prefix
     * @return
     */
    @Deprecated
    protected String getStatementId(String prefix) {
        return sqlId(prefix);
    }

    /**
     * 生成ibatis的sqlId
     * namespace.sqlId
     *
     * @param sqlId
     * @return
     */
    protected String sqlId(String sqlId) {
        return new StringBuffer(getNamespace())
                .append(".").append(sqlId)
                .toString();
    }


    protected boolean batchInsert(String sqlId ,List<? extends Object> params){
        return queryDao.batchInsert(sqlId, params);
    }


    public Paginate queryForPaginate2(Map<String, Object> params) {
        return queryForPaginate2(Constants.DAO_QUERY_FOR_PAGINATE_2, params);
    }

    public Paginate queryForPaginate2(String sqlId, Map<String, Object> params) {
        //分页组装从1开始计算，存储过程的分页从0开始
        int pageNo = Strings.isEmpty(params.get("pageNo")) ? 0 : (Integer)params.get("pageNo");
        int pageSize = Strings.isEmpty(params.get("pageSize")) ? Constants.WEB_PAGE_SIZE : (Integer)params.get("pageSize");

        Paginate paginate = new Paginate(pageNo + 1, pageSize);
        paginate.setPageList(queryDao.queryForList(sqlId(sqlId), params));
        paginate.setTotalCount(this.count2(params));
        return paginate;
    }

    /**
     * 通用Map查询分页
     * @param params
     * @return
     */
    @Deprecated
    public Paginate paginateByMap(Map<String,? extends Object> params){
        Object page  = params.get("page");
        Object pageSize = params.get("pageSize");
        Assert.notNull(page, "page must not be null !");
        Assert.notNull(pageSize, "pageSize must not be null !");

        Integer ipage = 0;
        Integer ipageSize = 20;
        if(page instanceof Number){
            ipage = (Integer) page;
        }else{
            ipage = Integer.valueOf((String) page) ;
        }
        if(pageSize instanceof Number){
            ipageSize = (Integer) pageSize;
        }else{
            ipageSize = Integer.valueOf((String) pageSize) ;
        }
        Paginate paginate =  new Paginate(ipage+1,ipageSize);
        paginate.setPageList(queryDao.queryForList(sqlId("paginateByMapList"), params));
        paginate.setTotalCount(queryDao.count(sqlId("paginateByMapCount"), params));
        return paginate;
    }

    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return queryDao.getSqlMapClientTemplate();
    }
}
