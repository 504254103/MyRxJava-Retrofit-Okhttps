package com.frame.myrxjava_retrofit_okhttps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.frame.myrxjava_retrofit_okhttps.bean.BaseReply;
import com.frame.myrxjava_retrofit_okhttps.bean.InfoBean;
import com.frame.myrxjava_retrofit_okhttps.bean.ImageBean;
import com.frame.myrxjava_retrofit_okhttps.netWork.NetWorks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetWorks.getInfo(1, new Observer<InfoBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull InfoBean infoBean) {
                //在此可以对infoBean 进行解析后操作
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        NetWorks.login("account", "password", new Observer<BaseReply>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseReply baseReply) {
                Toast.makeText(getApplication(), baseReply.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Map<String, String> PersonBeans = new HashMap<>();
        for (int i = 0; i < personBeans.size(); i++) { //personBeans 是外部输入数据
            PersonBeans.put("PersonBeans[" + String.valueOf(i) + "].age", "");
            PersonBeans.put("PersonBeans[" + String.valueOf(i) + "].sex", "");
            PersonBeans.put("PersonBeans[" + String.valueOf(i) + "].tel", "");
            PersonBeans.put("PersonBeans[" + String.valueOf(i) + "].address", "");
        }

        //将数组包装成Map<String, String> 进行数据传输
        NetWorks.addPerson(1, PersonBeans, new Observer<BaseReply>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseReply baseReply) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        ArrayList<ImageBean> imageList = new ArrayList<>(); //new一个存放图片素材的数组

        //将各个参数包装在一个Map<String, RequestBody>一起提交
        Map<String, RequestBody> map = new HashMap<>();
        map.put("caseId", convertToRequestBody(String.valueOf(1)));
        for (int i = 0; i < imageList.size(); i++) {
            File file = new File(imageList.get(i).getUrl());
            RequestBody requestBody1 = RequestBody.create(MediaType.parse(getMimeType(imageList.get(i).getUrl())), file);
            map.put("Attachments\"; filename=\"" + file.getName(), requestBody1);
        }
        NetWorks.addScore(map, new Observer<BaseReply>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseReply baseReply) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //将各个参数分别包装为RequestBody格式 分别提交
        RequestBody caseId = RequestBody.create(null, String.valueOf(1)); //只能转换String类型
        Map<String, RequestBody> AttachmentFiles = changeList(imageList, "AttachmentFiles");
        NetWorks.addScore2(caseId, AttachmentFiles, new Observer<BaseReply>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseReply baseReply) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    private Map<String, RequestBody> changeList(ArrayList<ImageBean> imagePaths, String str) {

        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            if (imagePaths.get(i).getPicType() != 0) {
                File file = new File(imagePaths.get(i).getUrl());
                RequestBody requestBody = RequestBody.create(MediaType.parse(getMimeType(imagePaths.get(i).getUrl())), file);
                map.put(str + "\"; filename=\"" + file.getName(), requestBody);
            }
        }
        return map;
    }

    private String getMimeType(String filePath) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
    }
}
