package com.dooioo.web.service;

import com.dooioo.commons.Lang;
import com.dooioo.commons.Strings;
import com.dooioo.web.common.Constants;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.dao.QueryErpDao;
import com.dooioo.web.model.BaseEntity;
import com.dooioo.web.model.BasePageEntity;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: kuang
 * Date: 12-7-5
 * Time: 下午5:21
 */
public abstract class BaseErpService<T> {

    protected Class<T> entityClass;

    @Autowired
    protected QueryErpDao queryDao;

    public BaseErpService() {
        entityClass = Lang.getSuperClassGenricType(getClass());
    }

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
     * @param entityObject
     * @return
     */
    public int count(String sqlId, BaseEntity entityObject) {
        return queryDao.count(sqlId, entityObject);
    }

    /**
     * 通过主键id查总量
     * 对应sqlId为：count
     * @param entityObject
     * @return
     */
    public int count(BaseEntity entityObject) {
        return queryDao.count(sqlId(Constants.DAO_COUNT), entityObject);
    }

    public int count2(BasePageEntity entityObject) {
        return queryDao.count(sqlId(Constants.DAO_COUNT_2), entityObject);
    }

    /**
     * 通过主键id查询，返回需要查询的对象
     * 调用对应sqlId, Statement.findById
     * @param id 主键id
     * @return model
     */
    public T findById(Serializable id) {
        return findById(sqlId(Constants.DAO_FINDBYID), id);
    }

    /**
     * 通过主键id查询，返回需要查询的对象
     * 其中sqlId，由客户端传入
     * @param sqlId
     * @param id
     * @return
     */
    public T findById(String sqlId, Serializable id) {
        return (T)queryDao.findById(sqlId, id);
    }

    /**
     * 通过自由组装字段及值进行查询，
     * 在内部会给对应model相应的字段赋值
     * 调用DAO时，传入的DB对应的model
     * 多个字段时，以,分隔
     * 调用对应sqlId, Statement.findByBean
     * @param entityObject
     * @param fieldName 字段名，e.g: name,passwor; name_and_password
     * @param filedValue
     * @return model
     */
    public T findByBean(BaseEntity entityObject, String fieldName, String... filedValue) {
        entityObject = setPorperty(entityObject, fieldName, filedValue);
        return findByBean(sqlId(Constants.DAO_QUERY_FOR_BEAN), entityObject);
    }

    public T findByBean(Serializable id) {
        return findByBean(sqlId(Constants.DAO_QUERY_FOR_BEAN), id);
    }

    public T findByBean(String sqlId, Map<String, Object> bindParams) {
        return (T)queryDao.queryForBean(sqlId, bindParams);
    }

    public T findByBean(String sqlId, BaseEntity entityObject) {
        return (T)queryDao.queryForBean(sqlId, entityObject);
    }

    public T findByBean(String sqlId, Serializable id) {
        return (T)queryDao.queryForBean(sqlId, id);
    }

    /**
     * 查询一条记录
     * TODO 该方法还可以优化，sqlMap无需匹配的queryTopOne
     * @param entityObject
     * @return
     */
    @Deprecated
    public T queryTopOne(BaseEntity entityObject) {
        return (T)queryDao.queryForBean(sqlId(Constants.DAO_QUERY_TOP_ONE), entityObject);
    }

    /**
     * 查询一条记录，返回一个对象
     * 对应sqlId为：queryFirst
     *
     * @param entityObject
     * @return
     */
    public T queryFirst(BaseEntity entityObject) {
        return (T)queryDao.queryForBean(sqlId(Constants.DAO_QUERY_First), entityObject);
    }

    public T queryFirst(String sqlId, BaseEntity entityObject) {
        return (T)queryDao.queryForBean(sqlId, entityObject);
    }

    public List<T> queryForList() {
        return queryDao.queryForList(sqlId(Constants.DAO_QUERY));
    }

    public List<T> queryForList(String sqlId, BaseEntity entityObject, String fieldName, Serializable... filedValue) {
        entityObject = setPorperty(entityObject, fieldName, filedValue);
        return (List<T>) queryDao.queryForList(sqlId, entityObject);
    }

    public List<T> queryForList(BaseEntity entityObject, String fieldName, Serializable... filedValue) {
        return queryForList(sqlId(Constants.DAO_QUERY), entityObject, fieldName, filedValue);
    }

    public List<T> queryForList(String sqlId, Serializable id) {
        return queryDao.queryForList(sqlId, id);
    }

    public List<T> queryForList(String sqlId, T entityObject) {
        return queryDao.queryForList(sqlId, entityObject);
    }

    public List<T> queryForList(String sqlId, Map<String, Object> bindParams) {
        return queryDao.queryForList(sqlId, bindParams);
    }

    public List<T> queryForList(Serializable id) {
        return queryDao.queryForList(sqlId(Constants.DAO_QUERY), id);
    }

    public List<T> queryForListBySqlId(String sqlId) {
        return queryDao.queryForList(sqlId);
    }

    public List<T> queryForList(T entityObject) {
        return queryForList(sqlId(Constants.DAO_QUERY), entityObject);
    }

    public List<T> queryForList(Map<String, Object> bindParams) {
        return queryForList(sqlId(Constants.DAO_QUERY), bindParams);
    }

    public Object queryForObject(String sqlId, Serializable id) {
        return queryDao.queryForObject(sqlId, id);
    }

    public Object queryForObject(String sqlId, BaseEntity entityObject) {
        return queryDao.queryForObject(sqlId, entityObject);
    }

    public Object queryForObject(String sqlId, BaseEntity entityObject, String fieldName, Serializable... filedValue) {
        entityObject = setPorperty(entityObject, fieldName, filedValue);
        return queryDao.queryForObject(sqlId, entityObject);
    }

    public Object queryForObject(String sqlId, Map<String, Object> bindParams) {
        return queryDao.queryForObject(sqlId, bindParams);
    }

    public List<String> queryForListStr(String sqlId, Map<String, Object> bindParams) {
        return queryDao.queryForListStr(sqlId, bindParams);
    }

    public List<Object> queryForListObj(String sqlId) {
        return queryDao.queryForListObj(sqlId);
    }

    public List<Object> queryForListObj(String sqlId, BaseEntity entityObject) {
        return queryDao.queryForListObj(sqlId, entityObject);
    }

    public Paginate queryForPaginate(BaseEntity entityObject) {
        return queryForPaginate(Constants.DAO_QUERY_FOR_PAGINATE, entityObject);
    }

    public Paginate queryForPaginate(String sqlId, BaseEntity entityObject) {
        //分页组装从1开始计算，存储过程的分页从0开始
        Paginate paginate = new Paginate(entityObject.getPageNo() + 1, entityObject.getPageSize());
        paginate.setPageList(queryDao.queryForList(sqlId(sqlId), entityObject));
        paginate.setTotalCount(this.count(entityObject));
        return paginate;
    }

    public boolean update(String sqlId, Serializable id) {
        return queryDao.update(sqlId, id) > 0;
    }

    public boolean update(T entityObject) {
        return queryDao.update(sqlId(Constants.DAO_UPDATE), entityObject) > 0;
    }

    public boolean update(String sqlId, T entityObject) {
        return queryDao.update(sqlId, entityObject) > 0;
    }

    public boolean update(String sqlId, Map<String, Object> bindParams) {
        return queryDao.update(sqlId, bindParams) > 0;
    }

    public boolean delete(Serializable id) {
        return queryDao.delete(sqlId(Constants.DAO_DELETE), id) > 0;
    }

    public boolean delete(T entityObject) {
        return queryDao.delete(sqlId(Constants.DAO_DELETE), entityObject) > 0;
    }

    public boolean delete(String sqlId, T entityObject) {
        return queryDao.delete(sqlId, entityObject) > 0;
    }

    public boolean delete(String sqlId, Serializable id) {
        return queryDao.delete(sqlId, id) > 0;
    }

    public boolean delete(String sqlId, Map<String, Object> bindParams) {
        return queryDao.delete(sqlId, bindParams) > 0;
    }

    public boolean insert(T entityObject) {
        return queryDao.insert(sqlId(Constants.DAO_INSERT), entityObject) > 0;
    }

    public boolean insert(String sqlId, T entityObject) {
        return queryDao.insert(sqlId, entityObject) > 0;
    }

    /**
     * 插入数据并返回identity
     * @param entityObject 实体对象
     * @return integer
     */
    public Integer insertAndReturnId(T entityObject) {
        return queryDao.insertAndReturnId(sqlId(Constants.DAO_INSERTANDRETURNID), entityObject);
    }

    public Integer insertAndReturnId(String sqlId, T entityObject) {
        return queryDao.insertAndReturnId(sqlId, entityObject);
    }


    public boolean insert(String sqlId, Map<String, Object> bindParams) {
        return queryDao.insert(sqlId, bindParams) > 0;
    }

    public Integer insertAndReturnId(String sqlId, BaseEntity entityObject) {
        return queryDao.insertAndReturnId(sqlId, entityObject);
    }

    public boolean contains(BaseEntity entityObject) {
        return queryFirst(entityObject) != null;
    }

    public boolean contains(BaseEntity entityObject, String fieldName, String... fieldValue) {
        entityObject = setPorperty(entityObject, fieldName, fieldValue);
        return queryFirst(entityObject) != null;
    }

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
        return new StringBuffer(entityClass.getSimpleName())
                .append(".").append(sqlId)
                .toString();
    }

    protected BaseEntity setPorperty(BaseEntity entityObject, String fieldName, Serializable... filedValue) {
        try {
            if(Strings.isEmpty(fieldName))
                return null;

            String [] fields = fieldName.split(",");
            for(int i = 0; i < fields.length; i ++) {
                String fv = filedValue[i].toString();
                if(Strings.isEmpty(fv))
                    continue;
                PropertyUtils.setSimpleProperty(entityObject, fields[i], filedValue[i]);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return entityObject;
    }

    protected boolean batchInsert(String sqlId ,List<? extends Object> params){
        return queryDao.batchInsert(sqlId, params);
    }


    public Paginate queryForPaginate2(BasePageEntity entityObject) {
        return queryForPaginate2(Constants.DAO_QUERY_FOR_PAGINATE_2, entityObject);
    }

    public Paginate queryForPaginate2(String sqlId, BasePageEntity entityObject) {
        //分页组装从1开始计算，存储过程的分页从0开始
        Paginate paginate = new Paginate(entityObject.getPageNo() + 1, entityObject.getPageSize());
        paginate.setPageList(queryDao.queryForList(sqlId(sqlId), entityObject));
        paginate.setTotalCount(this.count2(entityObject));
        return paginate;
    }

    /**
     * 通用Map查询分页
     * @param params
     * @return
     */
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

    //各种工具方法
    public static <T> List<T> list(T... arrays) {
        return Lang.list(arrays);
    }

    public static Map map(Object... arrays) {
        return Lang.map(arrays);
    }

    public static <T> Set<T> set(T... arrays) {
        return Lang.set(arrays);
    }
}
