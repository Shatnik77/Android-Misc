package com.artshell.mvp.storage.server;

import com.artshell.mvp.storage.prefer.PreferManager;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;

import javax.annotation.Nonnull;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * @author artshell on 17/03/2018
 */

public class CacheManager {

    // GET 请求
    public static Store<String, Mixture> store() {
        return MixtureHolder.MIXTURE;
    }

    // GET 请求 + 查询参数
    public static Store<String, Mixture2> storeWithParameter() {
        return MixtureParameterHolder.MIXTURE;
    }

    // POST 请求 + 字段
    public static Store<String, Mixture2> storeWithField() {
        return MixtureFieldHolder.MIXTURE;
    }

    private static final class MixtureHolder {
        private static final Store<String, Mixture> MIXTURE = createStore(
                mixture -> HttpManager.get(String.class, mixture.getPath()).singleOrError()
        );
    }

    private static final class MixtureParameterHolder {
        private static final Store<String, Mixture2> MIXTURE = createStore(
                mixture -> HttpManager.get(String.class, mixture.getPath(), mixture.getPairs()).singleOrError()
        );
    }

    private static final class MixtureFieldHolder {
        private static final Store<String, Mixture2> MIXTURE = createStore(
                mixture -> HttpManager.post(String.class, mixture.getPath(), mixture.getPairs()).singleOrError()
        );
    }

    public static <I extends Key> Store<String, I> createStore(Fetcher<String, I> fetcher) {
        return StoreBuilder.<I, String, String>parsedWithKey()
                .fetcher(fetcher)
                .persister(new Persister<String, I>() {
                    @Nonnull
                    @Override
                    public Maybe<String> read(@Nonnull I tuple) {
                        // 从SharedPreference读取
                        String cache = PreferManager.apiData().get(tuple.getKey(), String.class, "");
                        if (cache.length() == 0) {
                            return Maybe.empty();
                        }
                        return Maybe.just(cache);
                    }

                    @Nonnull
                    @Override
                    public Single<Boolean> write(@Nonnull I tuple, @Nonnull String source) {
                        // 缓存存放至SharedPreference中, 也可以是FileSystems, Database, server, clouds
                        PreferManager.apiData().put(tuple.getKey(), source);
                        return Single.just(true);
                    }
                })
                .parser(cache -> cache)
                .networkBeforeStale()
                .open();
    }
}
