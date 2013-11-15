package pl.siemionczyk.otwartezabytki.event;

/**
 * Created by michalsiemionczyk on 13/11/13.
 */
public interface EventBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);
}
