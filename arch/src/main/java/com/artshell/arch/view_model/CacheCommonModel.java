package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.MainLiveDataStreams;
import com.artshell.arch.storage.Mixture;
import com.artshell.arch.storage.Mixture2;
import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.server.CacheManager;

import java.util.Map;

import io.reactivex.schedulers.Schedulers;

/**
 * 常用请求(单接口数据, 从缓存中获取, 没有则自动从服务器端获取)
 * (不适用于多接口数据或一个接口又依赖其它接口的数据)根据需要可选择重写相应方法
 * Created by artshell on 2018/3/16.
 */

public class CacheCommonModel extends BaseContextViewModel {

    public CacheCommonModel(@NonNull Application application) {
        super(application);
    }

    // Get请求
    public <T> LiveData<Resource<T>> get(String cacheKey, Class<T> target, String url) {
        return MainLiveDataStreams.fromPublisher(
                CacheManager.store()
                        .get(new Mixture(cacheKey, url))
                        .map(raw -> singleton().get().fromJson(raw, target))
                        .toFlowable()
                        .subscribeOn(Schedulers.io()));
    }

    // Get请求带参数
    public <T> LiveData<Resource<T>> getWithParameter(String cacheKey, Class<T> target, String url, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                CacheManager.storeWithParameter()
                        .get(new Mixture2(cacheKey, url, pairs))
                        .map(raw -> singleton().get().fromJson(raw, target))
                        .toFlowable()
                        .subscribeOn(Schedulers.io()));
    }

    // Post请求带字段
    public <T> LiveData<Resource<T>> post(String cacheKey, Class<T> target, String url, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                CacheManager.storeWithField()
                        .get(new Mixture2(cacheKey, url, pairs))
                        .map(raw -> singleton().get().fromJson(raw, target))
                        .toFlowable()
                        .subscribeOn(Schedulers.io()));
    }
}
