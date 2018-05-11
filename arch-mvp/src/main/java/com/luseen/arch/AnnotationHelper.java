package com.luseen.arch;

/**
 * Created by Chatikyan on 29.06.2017.
 */

class AnnotationHelper {

    static BaseContract.Presenter createPresenter(Class<?> annotatedClass) {
        try {
            return annotatedClass.getAnnotation(Viewable.class).presenter().newInstance();
        } catch (InstantiationException e) {
            throw new ArchMvpException("Cannot create an instance of " + annotatedClass, e);
        } catch (IllegalAccessException e) {
            throw new ArchMvpException("Cannot create an instance of " + annotatedClass, e);
        }
    }
}