package com.frame.myrxjava_retrofit_okhttps.netWork;

import com.frame.myrxjava_retrofit_okhttps.bean.BaseReply;
import com.frame.myrxjava_retrofit_okhttps.bean.InfoBean;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Field注解:
 * 作用于方法的参数
 * 用于发送一个表单请求
 * 用String.valueOf()把参数值转换为String,然后进行URL编码,当参数值为null值时,会自动忽略,
 * 如果传入的是一个List或array,则为每一个非空的item拼接一个键值对,每一个键值对中的键是相同的,值就是非空item的值
 * 另外,如果item的值有空格,在拼接时会自动忽略
 * ---------------------
 * FieldMap注解:
 * 作用于方法的参数
 * 用于发送一个表单请求
 * map中每一项的键和值都不能为空,否则抛出IllegalArgumentException异常
 * ---------------------
 * Query注解:
 * 作用于方法的参数
 * 用于添加查询参数,即请求参数
 * 参数值通过String.valueOf()转换为String并进行URL编码
 * 使用该注解定义的参数,参数值可以为空,为空时,忽略该值
 * 当传入一个List或array时,为每个非空item拼接请求键值对,所有的键是统一的
 * ---------------------
 * QueryMap注解:
 * 作用于方法的参数
 * 以map的形式添加查询参数,即请求参数
 * 参数的键和值都通过String.valueOf()转换为String格式
 * map的键和值默认进行URL编码
 * map中每一项的键和值都不能为空,否则抛出IllegalArgumentException异常
 * ---------------------
 * FormUrlEncoded注解:
 * 用于修饰Field注解和FieldMap注解
 * 使用该注解,表示请求正文将使用表单网址编码。字段应该声明为参数，并用@Field注释或FieldMap注释。
 * 使用FormUrlEncoded注解的请求将具”application / x-www-form-urlencoded” MIME类型。
 * 字段名称和值将先进行UTF-8进行编码,再根据RFC-3986进行URI编码.
 * ---------------------
 * Headers注解:
 * 作用于方法,用于添加一个或多个请求头
 * 具有相同名称的请求头不会相互覆盖,而是会照样添加到请求头中
 * ---------------------
 */

public class NetWorks extends RetrofitUtils {

    protected static final NetService service = getRetrofit().create(NetService.class);
    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private interface NetService{

        //get示例:获取详情
        @GET("/case/info")
        Observable<InfoBean> getInfo(@Query("caseId") int caseId);

        //get示例:path
        @GET("/case/{caseId}")
        Observable<InfoBean> getInfo2(@Path("caseId") int caseId);

        //delete参数用法同get
        @DELETE("/delete")
        Observable<BaseReply> delete(@Query("caseId") int caseId);

        //post示例:登录
        @FormUrlEncoded
        @POST("/app/login")
        Observable<BaseReply> login(
                //@Header("Authorization") String Authorization, //在拦截器统一设置header后不需要加
                @Field("Account") String account,
                @Field("Password") String password
        );

        //post示例:以form-data上传文件
        @Multipart
        @POST("/app/add")
        Observable<BaseReply> addScore(@PartMap() Map<String, RequestBody> AttachmentFiles);

        //post示例:以form-data上传文件 另一种写法
        @Multipart
        @POST("/app/add")
        Observable<BaseReply> addScore2(@Part("caseId") RequestBody caseId,
                                        @PartMap() Map<String, RequestBody> AttachmentFiles);

        @FormUrlEncoded
        @POST("/app/addPerson")
        Observable<BaseReply> addPerson(@Field("caseId") int caseId,
                                        @FieldMap Map<String, String> personList);

        //put用法同post
        @FormUrlEncoded
        @PUT("/app/update")
        Observable<BaseReply> update(@Field("Account") String Account,
                                     @Field("Password") String Password);
    }

    public static void getInfo(int caseId , Observer<InfoBean> observer) {
        setSubscribe(service.getInfo(caseId), observer);
    }

    public static void getInfo2(int caseId , Observer<InfoBean> observer) {
        setSubscribe(service.getInfo2(caseId), observer);
    }

    public static void delete(int caseId , Observer<BaseReply> observer) {
        setSubscribe(service.delete(caseId), observer);
    }

    public static void login(String Account ,String Password, Observer<BaseReply> observer) {
        setSubscribe(service.login(Account, Password), observer);
    }

    public static void addScore(Map<String, RequestBody> AttachmentFiles, Observer<BaseReply> observer) {
        setSubscribe(service.addScore(AttachmentFiles), observer);
    }

    public static void addScore2(RequestBody caseId ,Map<String, RequestBody> AttachmentFiles, Observer<BaseReply> observer) {
        setSubscribe(service.addScore2(caseId, AttachmentFiles), observer);
    }

    public static void addPerson(int caseId ,Map<String, String> personList, Observer<BaseReply> observer) {
        setSubscribe(service.addPerson(caseId, personList), observer);
    }

    public static void update(String Account ,String Password, Observer<BaseReply> observer) {
        setSubscribe(service.update(Account, Password), observer);
    }

    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
